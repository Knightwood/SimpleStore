package com.androidx.mediastorefile.parser.download

import android.database.Cursor
import com.androidx.mediastorefile.parser.utils.ColumnNameIndexMap
import com.androidx.mediastorefile.item.DownloadMediaFile
import com.androidx.mediastorefile.parser.base.newBasicMediaStoreMapper
import com.androidx.mediastorefile.parser.utils.AggregationCursorMapper
import com.androidx.mediastorefile.parser.utils.CursorSampleMapper
import com.androidx.mediastorefile.parser.utils.ICursorParser

class DownloadMediaCursorParserApi1 : ICursorParser<DownloadMediaFile>() {

    override fun parseColumnIndex(cursor: Cursor): ColumnNameIndexMap {
        return mapOf()
    }

    override fun setFieldValue(cursor: Cursor, data: DownloadMediaFile) {}

}

/**
 * 只映射独属于DownloadMediaFile的字段
 */
fun newDownloadMediaCursorBaseMapper(): CursorSampleMapper<DownloadMediaFile> =
    CursorSampleMapper<DownloadMediaFile>() +
            DownloadMediaCursorParserApi1() +
            DownloadMediaCursorParserApi29()

fun newDownloadMediaCursorMapper(): AggregationCursorMapper<DownloadMediaFile> {
    // 基础信息映射器
    val baseMapper = newBasicMediaStoreMapper()
    return AggregationCursorMapper<DownloadMediaFile>(
        mapper = newDownloadMediaCursorBaseMapper(),
        dataCreator = { cursor ->
            DownloadMediaFile(
                property = baseMapper.mapOneRowFrom(cursor)//这里使用的映射器要与下面1处添加的是同一个实例
            )
        }
    ) + baseMapper//1.
}
