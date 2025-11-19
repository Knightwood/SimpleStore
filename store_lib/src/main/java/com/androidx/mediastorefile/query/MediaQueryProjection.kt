package com.androidx.mediastorefile.query

data class MediaQueryProjection(
    val mediaStore: MediaStores,
    val projection: List<String>,
    val selection: String? = null,
    val selectionArgs: Array<String>? = null,
    val sortOrder: String? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MediaQueryProjection

        if (mediaStore != other.mediaStore) return false
        if (projection != other.projection) return false
        if (selection != other.selection) return false
        if (!selectionArgs.contentEquals(other.selectionArgs)) return false
        if (sortOrder != other.sortOrder) return false

        return true
    }

    override fun hashCode(): Int {
        var result = mediaStore.hashCode()
        result = 31 * result + projection.hashCode()
        result = 31 * result + (selection?.hashCode() ?: 0)
        result = 31 * result + (selectionArgs?.contentHashCode() ?: 0)
        result = 31 * result + (sortOrder?.hashCode() ?: 0)
        return result
    }
}

enum class MediaStores {
    Downloads,
    Audio,
    Video,
    Images,
    Documents,
}

enum class MediaQueryOrder {
    ASC,
    DESC
}

data class MediaQueryOrderBy(
    val column: String,
    val order: MediaQueryOrder = MediaQueryOrder.ASC,
)

enum class MediaQuerySelection {
    AND,
    OR
}
