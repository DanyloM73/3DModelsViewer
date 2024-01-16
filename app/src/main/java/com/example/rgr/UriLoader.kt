package com.example.rgr

import android.content.Context
import android.net.Uri
import java.io.InputStream
import java.nio.ByteBuffer

class UriLoader : Loader {
    override fun load(context: Context, path: String): ByteBuffer? {
        val uri = Uri.parse(path)
        var input: InputStream? = null
        try {
            input = context.contentResolver.openInputStream(uri)
            val bytes = ByteArray(input!!.available())
            input.read(bytes)
            return ByteBuffer.wrap(bytes)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            input?.close()
        }
        return null
    }
}
