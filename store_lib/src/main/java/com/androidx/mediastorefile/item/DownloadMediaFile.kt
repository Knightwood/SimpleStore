@file:Suppress(
    "unused",
    "SpellCheckingInspection",
    "PropertyName",
    "LocalVariableName",
    "UNUSED_ANONYMOUS_PARAMETER"
)

package com.androidx.mediastorefile.item

import android.provider.MediaStore

class DownloadMediaFile(
    /**
     * [MediaStore.DownloadColumns.DOWNLOAD_URI]
     */
    var download_uri: String? = null,
    /**
     * [MediaStore.DownloadColumns.REFERER_URI]
     */
    var referer_uri: String? = null,
    val property: FakeMediaFile
) : FakeMediaFile by property
