package com.kiylx.store_lib.kit

import android.net.Uri
import java.io.InputStream
import java.io.OutputStream

/**
 * 通知处理结果 用true或false表示结果
 */
typealias FileProcessResult = Function1<Boolean, Unit>

/**
 * 通知文件的路径uri
 */
typealias UriResult = Function1<Uri?, Unit>

typealias InputConsumer = Function1<InputStream?, Unit>

typealias OutputConsumer = Function1<OutputStream?, Unit>
