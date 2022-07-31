package com.kiylx.store_lib.kit.ext

/**
 * 传入参数不能为null
 *
 * @param block 使用try catch 块执行的匿名函数
 *
 * T: block的传入参数类型 R: block的返回参数类型
 */
inline fun <T : Any, R> T.runSafely(block: (T) -> R) = try {
    block(this)
} catch (e: Exception) {
    e.printStackTrace()
    null
}

/**
 * 传入参数可以为null
 *
 * @param block 使用try catch 块执行的匿名函数
 *
 * T: block的传入参数类型 R: block的返回参数类型
 */
inline fun <T : Any, R> T?.runSafelyNullable(block: (T?) -> R) = try {
    block(this)
} catch (e: Exception) {
    e.printStackTrace()
    null
}

/**
 * 传入参数为null将会抛出异常
 *
 * @param block 使用try catch 块执行的匿名函数
 *
 * T: block的传入参数类型 R: block的返回参数类型
 */
inline fun <T : Any, R> T?.runSafelyNoNull(block: (T) -> R) = try {
    block(this!!)
} catch (e: Exception) {
    e.printStackTrace()
    null
}