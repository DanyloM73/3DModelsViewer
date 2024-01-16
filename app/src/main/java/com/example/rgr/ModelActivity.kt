package com.example.rgr

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.SurfaceView
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class ModelActivity : AppCompatActivity() {

    private var surfaceView: SurfaceView? = null
    private var customViewer: CustomViewer = CustomViewer()
    private var uiManager: UIManager? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_model)

        val modelPath = intent.getStringExtra("model_path")
        val modelType = intent.getStringExtra("model_type")

        uiManager = UIManager(this, customViewer)
        uiManager?.setupUI()

        surfaceView = findViewById<View>(R.id.surface_view) as SurfaceView
        customViewer.run {
            loadEntity()
            setSurfaceView(requireNotNull(surfaceView))

            loadGlb(this@ModelActivity, modelPath.toString(), modelType.toString())

            loadIndirectLight(this@ModelActivity)
        }
    }

    override fun onResume() {
        super.onResume()
        customViewer.onResume()
    }

    override fun onPause() {
        super.onPause()
        customViewer.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        customViewer.onDestroy()
    }
}