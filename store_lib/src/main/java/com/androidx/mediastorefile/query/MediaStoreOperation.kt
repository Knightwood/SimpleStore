package com.androidx.mediastorefile.query

import android.content.Context

interface IMediaStoreItemOperation {
    fun move()
    fun copy()
    fun delete()
    fun open()
    fun mkDirs()
    fun newFile()
    fun isExist()
}

class MediaStoreOperation(val context: Context) {
    fun scope() {

    }
}
