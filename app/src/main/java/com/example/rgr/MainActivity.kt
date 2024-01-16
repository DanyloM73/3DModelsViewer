package com.example.rgr

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    companion object {
        private const val READ_REQUEST_CODE: Int = 42
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cubeButton: Button = findViewById(R.id.cube_button)
        val astronautButton: Button = findViewById(R.id.astronaut_button)
        val horseButton: Button = findViewById(R.id.horse_button)
        val neilArmstrongButton: Button = findViewById(R.id.neilArm_button)
        val rocketButton: Button = findViewById(R.id.rocket_button)

        val openFileButton: Button = findViewById(R.id.upload_button)

        cubeButton.setOnClickListener {
            loadModel("models/Cube.glb", "asset")
        }
        astronautButton.setOnClickListener {
            loadModel("models/Astronaut.glb", "asset")
        }
        horseButton.setOnClickListener {
            loadModel("models/Horse.glb", "asset")
        }
        neilArmstrongButton.setOnClickListener {
            loadModel("models/NeilArmstrong.glb", "asset")
        }
        rocketButton.setOnClickListener {
            loadModel("models/RocketShip.glb", "asset")
        }

        openFileButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "*/*"
                val downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val downloadsUri = Uri.fromFile(downloadsDirectory)
                putExtra(DocumentsContract.EXTRA_INITIAL_URI, downloadsUri)
            }
            startActivityForResult(intent, READ_REQUEST_CODE)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            resultData?.data?.also { uri ->
                loadModel(uri.toString(), "uri")
            }
        }
    }

    private fun loadModel(modelPath: String, modelType: String) {
        val intent = Intent(this, ModelActivity::class.java)
        intent.putExtra("model_path", modelPath)
        intent.putExtra("model_type", modelType)
        startActivity(intent)
    }
}







