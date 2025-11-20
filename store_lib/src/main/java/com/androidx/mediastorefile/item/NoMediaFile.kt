package com.androidx.mediastorefile.item

/**
 * NoMediaFile
 *
 * @constructor Create empty NoMediaFile
 * @property property
 */
data class NoMediaFile(
    val parent: Int,
    val media_type: Int,
    private val property: FakeMediaFile
) : FakeMediaFile by property {

}
