package com.kiylx.store_lib.saf

import android.content.ContentResolver
import android.net.Uri
import androidx.annotation.RequiresApi
import androidx.documentfile.provider.DocumentFile
import com.kiylx.store_lib.kit.*

interface FileMethod {
    /**
     * 申请一个文件夹的使用权限
     */
    fun requestOneFolder(pickerInitialUri: String? = null, block: noNullUriResult)

    /**
     * 保留对应目录的读写权限
     */
    fun savePerms(uri: Uri)
    /**
     * 取消某个目录的权限
     * @param uri 需要取消权限的目录uri
     */
    fun releaseFolderPerms(
        uri: Uri,
    )
    /**
     * 使用saf打开一个用户选择的文件
     */
    fun selectFile(
        pickerInitialUri: String? = null,
        fileType: String = "*/*",
        block: noNullUriResult,
    )

    /**
     * @param fileName 文件名
     * @param pickerInitialUri （可选）在您的应用创建文档之前，为应该在系统文件选择器中打开的目录指定一个 URI。
     * @param fileType （可选）文件的类型
     * @param block 文件创建后，将会回调此匿名函数
     *
     * @see com.kiylx.store_lib.kit.MimeTypeConsts
     */
    fun createFile(
        fileName: String,
        fileType: String = "*/*",
        pickerInitialUri: String? = null,
        block: noNullUriResult,
    )


    fun deleteFile(
        uri: Uri,
        block: fileProcessResult,
    )

    fun copyFile(sourceFileUri: Uri, targetFolderUri: Uri, block: uriResult)


    fun moveFile(
        sourceFileUri: Uri,
        sourceFileParentUri: Uri,
        targetFolderUri: Uri,
        block: uriResult,
    )


    fun rename(sourceFileUri: Uri, newName: String, block: uriResult)

    @RequiresApi(24)
    fun removeFile(sourceFileUri: Uri, sourceFileParentUri: Uri, block: fileProcessResult)



    /**
     * @param parentUri 要创建文件的父文件夹uri
     * @param displayName 要创建的文件的名称
     * @param mimeType 文件类型[com.kiylx.store_lib.kit.MimeTypeConsts]
     * @param block(documentFile) 创建文件后给与documentFile，或者创建失败给与null
     */
    fun createDocumentFile(
        parentUri: Uri,
        displayName: String,
        mimeType: String,
        block: (file: DocumentFile?) -> Unit,
    )

    /**
     * @param parentUri 要创建文件文件夹的父文件夹uri
     * @param displayName 要创建的文件夹的名称
     * @param block(documentFile) 创建文件夹后给与documentFile，或者创建失败给与null
     */
    fun createDir(
        parentUri: Uri,
        displayName: String,
        block: (file: DocumentFile?) -> Unit,
    )
    fun readFile(
        uri: Uri,
        input: input,
    )

    fun writeFile(
        uri: Uri,
        output: output,
    )
    fun getContentResolver(): ContentResolver

    /**
     * Create a {@link DocumentFile} representing the document tree rooted at
     * the given {@link Uri}. This is only useful on devices running
     * {@link android.os.Build.VERSION_CODES#LOLLIPOP} or later, and will return
     * {@code null} when called on earlier platform versions.
     *
     * @param treeUri the {@link Intent#getData()} from a successful
     *            {@link Intent#ACTION_OPEN_DOCUMENT_TREE} request.
     */
    fun getDocumentTreeFile(treeUri: Uri): DocumentFile?

    /**
     * Create a {@link DocumentFile} representing the single document at the
     * given {@link Uri}. This is only useful on devices running
     * {@link android.os.Build.VERSION_CODES#KITKAT} or later, and will return
     * {@code null} when called on earlier platform versions.
     *
     * @param singleUri the {@link Intent#getData()} from a successful
     *            {@link Intent#ACTION_OPEN_DOCUMENT} or
     *            {@link Intent#ACTION_CREATE_DOCUMENT} request.
     */
    fun getSingleDocumentFile(singleUri: Uri): DocumentFile?

}