package com.example.rgr

import android.content.Context
import java.nio.ByteBuffer

interface Loader {
    fun load(context: Context, path: String): ByteBuffer?
}