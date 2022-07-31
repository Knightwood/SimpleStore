package com.kiylx.store_lib.mediastore

import android.os.Build
import androidx.annotation.RequiresApi
import com.kiylx.store_lib.kit.noNullUriResult

interface MediaStoreMethod {
    /**
     * @param relativePath 相对图片文件夹的相对路径
     * 例如 传入test，会存入 storage/emulated/0/Pictures/test/ 文件夹
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    fun newPhoto(
        name: String,
        mime: String,
        relativePath: String = "",
        block: noNullUriResult,
    )

    @RequiresApi(Build.VERSION_CODES.Q)
    fun newPicture(
        name: String,
        relativePath: String = "",
        mime: String,
        block: noNullUriResult,
    )

    @RequiresApi(Build.VERSION_CODES.Q)
    fun newDownloadFile(
        name: String,
        path: String = "",
        mime: String,
        block: noNullUriResult,
    )

    @RequiresApi(Build.VERSION_CODES.Q)
    fun newMovieFile(
        name: String,
        path: String = "",
        mime: String,
        block: noNullUriResult,
    )

    @RequiresApi(Build.VERSION_CODES.Q)
    fun newMusicFile(
        name: String,
        path: String = "",
        mime: String,
        block: noNullUriResult,
    )
}