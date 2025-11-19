@file:Suppress(
    "unused",
    "SpellCheckingInspection",
    "PropertyName",
    "LocalVariableName",
    "UNUSED_ANONYMOUS_PARAMETER"
)

package com.androidx.mediastorefile.item

class MediaFile(
    //api 1
    override var _id: Long = 0,
    override var _count: Int = 0,
    override var _data: String? = null,
    override var _size: Long = 0,
    override var _display_name: String? = null,
    override var date_added: Long = 0,
    override var date_modified: Long = 0,
    override var mime_type: String? = null,
    override var width: Int = 0,
    override var height: Int = 0,
    override var title: String? = null,
    //api 29
    override var datetaken: Long = 0,
    override var is_pending: Int = 0,
    override var date_expires: Long = 0,
    override var owner_package_name: String? = null,
    override var relative_path: String? = null,
    override var bucket_id: Int = 0,
    override var bucket_display_name: String? = null,
    override var document_id: String? = null,
    override var instance_id: String? = null,
    override var original_document_id: String? = null,
    override var orientation: Int = 0,
    override var volume_name: String? = null,
    override var duration: Long = 0,
    //api 30
    override var is_drm: Int = 0,
    override var is_trashed: Int = 0,
    override var resolution: String? = null,
    @Deprecated("已移除")
    override var group_id: Int = 0,
    override var is_favorite: Int = 0,
    override var is_download: Int = 0,
    override var generation_added: Int = 0,
    override var generation_modified: Int = 0,
    override var xmp: ByteArray? = null,
    override var cd_track_number: String? = null,
    override var album: String? = null,
    override var artist: String? = null,
    override var author: String? = null,
    override var composer: String? = null,
    override var genre: String? = null,
    override var year: Int = 0,
    override var num_tracks: Int = 0,
    override var writer: String? = null,
    override var album_artist: String? = null,
    override var disc_number: String? = null,
    override var compilation: String? = null,
    override var bitrate: Int = 0,
    override var capture_framerate: Float = 0.0f,
) : FakeMediaFile

