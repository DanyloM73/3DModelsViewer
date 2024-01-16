package com.example.rgr

import android.content.Context
import java.nio.ByteBuffer

class AssetLoader : Loader {
    override fun load(context: Context, path: String): ByteBuffer {
        val input = context.assets.open(path)
        val bytes = ByteArray(input.available())
        input.read(bytes)
        return ByteBuffer.wrap(bytes)
    }
}