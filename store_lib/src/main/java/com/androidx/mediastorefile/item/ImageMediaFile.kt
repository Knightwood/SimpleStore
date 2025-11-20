package com.androidx.mediastorefile.item

data class ImageMediaFile(
    val picasaId: String? = null,              // 对应 PICASA_ID（已废弃）
    val isPrivate: Int? = null,                // 对应 IS_PRIVATE
    val latitude: Float? = null,               // 对应 LATITUDE（已废弃）
    val longitude: Float? = null,              // 对应 LONGITUDE（已废弃）
    val miniThumbMagic: Long? = null,          // 对应 MINI_THUMB_MAGIC（已废弃）
    val bucketDisplayName: String? = null,     // 对应 BUCKET_DISPLAY_NAME（继承自 MediaColumns）
    val groupId: Long? = null,                 // 对应 GROUP_ID（已废弃）
    val description: String? = null,           // 对应 DESCRIPTION
    val exposureTime: String? = null,          // 对应 EXPOSURE_TIME
    val fNumber: String? = null,               // 对应 F_NUMBER
    val iso: Int? = null,                      // 对应 ISO
    val sceneCaptureType: Int? = null,         // 对应 SCENE_CAPTURE_TYPE
    private val property: FakeMediaFile
) : FakeMediaFile by property {

}
