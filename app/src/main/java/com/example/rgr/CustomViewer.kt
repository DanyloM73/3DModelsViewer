package com.example.rgr

import android.annotation.SuppressLint
import android.content.Context
import android.view.Choreographer
import android.view.SurfaceView
import com.google.android.filament.Skybox
import com.google.android.filament.utils.KTX1Loader
import com.google.android.filament.utils.ModelViewer
import com.google.android.filament.utils.Utils

class CustomViewer {
    companion object {
        init {
            Utils.init()
        }
    }

    private lateinit var choreographer: Choreographer
    private lateinit var modelViewer: ModelViewer
    private val assetLoader = AssetLoader()
    private val uriLoader = UriLoader()

    fun loadEntity() {
        choreographer = Choreographer.getInstance()
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setSurfaceView(mSurfaceView: SurfaceView) {
        modelViewer = ModelViewer(mSurfaceView)
        mSurfaceView.setOnTouchListener(modelViewer)

        modelViewer.scene.skybox = Skybox.Builder().build(modelViewer.engine)
        modelViewer.scene.skybox?.setColor(0.0f, 0.0f, 0.0f, 1.0f)
    }

    fun changeSkyboxColor (red: Float, green:Float, blue: Float) {
        modelViewer.scene.skybox?.setColor(red, green, blue, 1.0f)
    }

    fun changeIntensity (progress: Float) {
        modelViewer.scene.indirectLight?.intensity = progress
    }

    fun loadGlb(context: Context, path: String, type: String) {
        val loader: Loader = if (type == "asset") assetLoader else uriLoader
        val buffer = loader.load(context, path)

        modelViewer.apply {
            if (buffer != null) {
                loadModelGlb(buffer)
            }
            transformToUnitCube()
        }
    }

    fun loadIndirectLight(context: Context) {
        val buffer = assetLoader.load(context, "environments/venetian_crossroads_2k_ibl.ktx")
        KTX1Loader.createIndirectLight(modelViewer.engine, buffer).apply {
            intensity = 50_000f
            modelViewer.scene.indirectLight = this
        }
    }

    private val frameCallback = object : Choreographer.FrameCallback {
        private val startTime = System.nanoTime()
        override fun doFrame(currentTime: Long) {
            val seconds = (currentTime - startTime).toDouble() / 1_000_000_000
            choreographer.postFrameCallback(this)
            modelViewer.animator?.apply {
                if (animationCount > 0) {
                    applyAnimation(0, seconds.toFloat())
                }
                updateBoneMatrices()
            }
            modelViewer.render(currentTime)
        }
    }

    fun onResume() {
        choreographer.postFrameCallback(frameCallback)
    }

    fun onPause() {
        choreographer.removeFrameCallback(frameCallback)
    }

    fun onDestroy() {
        choreographer.removeFrameCallback(frameCallback)
    }
}