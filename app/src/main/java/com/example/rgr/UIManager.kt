package com.example.rgr

import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SeekBar

class UIManager(private val activity: ModelActivity, private val customViewer: CustomViewer) {

    fun setupUI() {
        val controlPanel: LinearLayout = activity.findViewById(R.id.control_panel)
        val toolbar: LinearLayout = activity.findViewById(R.id.toolbar)

        val changeSettings: Button = activity.findViewById(R.id.change_settings)
        val closeToolbar: Button = activity.findViewById(R.id.close_toolbar)

        changeSettings.setOnClickListener {
            controlPanel.visibility = View.GONE
            toolbar.visibility = View.VISIBLE
        }

        closeToolbar.setOnClickListener {
            toolbar.visibility = View.GONE
            controlPanel.visibility = View.VISIBLE
        }

        setupColorSliders()
        setupLightSlider()
    }

    private fun setupColorSliders() {
        val redSlider: SeekBar = activity.findViewById(R.id.red_slider)
        val greenSlider: SeekBar = activity.findViewById(R.id.green_slider)
        val blueSlider: SeekBar = activity.findViewById(R.id.blue_slider)

        val colorSliders = listOf(redSlider, greenSlider, blueSlider)

        colorSliders.forEach { slider ->
            slider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    val red = redSlider.progress / 255.0f
                    val green = greenSlider.progress / 255.0f
                    val blue = blueSlider.progress / 255.0f
                    customViewer.changeSkyboxColor(red, green, blue)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
        }
    }

    private fun setupLightSlider() {
        val lightSlider: SeekBar = activity.findViewById(R.id.light_slider)

        lightSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                val lightIntensity = progress.toFloat() * 500f
                customViewer.changeIntensity(lightIntensity)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
}