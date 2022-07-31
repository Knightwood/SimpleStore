package com.kiylx.store_lib.saf

import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import androidx.annotation.RequiresApi
import androidx.documentfile.provider.DocumentFile
import androidx.documentfile.provider.DocumentFile.fromTreeUri
import androidx.fragment.app.Fragment
import com.kiylx.store_lib.kit.ext.readFileFromUri
import com.kiylx.store_lib.kit.ext.runSafelyNoNull
import com.kiylx.store_lib.kit.ext.startActivityForResult
import com.kiylx.store_lib.kit.ext.writeFileFromUri
import com.kiylx.store_lib.kit.fileProcessResult
import com.kiylx.store_lib.kit.input
import com.kiylx.store_lib.kit.noNullUriResult
import com.kiylx.store_lib.kit.output

/** 从 MediaStore接口或者SAF获取到文件Uri后，请利用Uri打开FD 或者输入输出流，而不要转换成文件路径去访问。 */
class SafImplFragment : Fragment(), FileMethod {

    /** 申请一个文件夹的使用权限 如果直接返回，则intent为null，不触发后续处理。如果intent.data为null,则报错 */
    override fun requestOneFolder(
        pickerInitialUri: String?,
        block: noNullUriResult, /* = (uri: android.net.Uri) -> kotlin.Unit */
    ) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
            //可选，添加一个初始路径
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && pickerInitialUri != null) {
                putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
            }
        }
        startActivityForResult(intent) {
            it?.data.runSafelyNoNull(block)
        }
    }

    /**
     * 取消某个目录的权限
     *
     * @param uri 需要取消权限的目录uri
     */
    override fun releaseFolderPerms(
        uri: Uri,
    ) {
        val takeFlags = (Intent.FLAG_GRANT_READ_URI_PERMISSION
                or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

        getContentResolver().releasePersistableUriPermission(uri, takeFlags)
    }

    /** “获取”系统提供的永久性 URI 访问权限 */
    override fun savePerms(uri: Uri) {
        val contentResolver = getContentResolver()
        val takeFlags: Int = (Intent.FLAG_GRANT_READ_URI_PERMISSION or
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        //检查
        contentResolver.takePersistableUriPermission(uri, takeFlags)
    }

    /**
     * @param fileName 文件名
     * @param fileType （可选）文件的类型[com.kiylx.store_lib.kit.MimeTypeConsts]
     * @param pickerInitialUri （可选）在您的应用创建文档之前，为应该在系统文件选择器中打开的目录指定一个 URI。
     * @param block 文件创建后，将会回调此匿名函数.如果intent或intent.getData()为null,则不会调用block块
     */
    override fun createFile(
        fileName: String,
        fileType: String,
        pickerInitialUri: String?,
        block: noNullUriResult /* (uri: Uri) -> Unit */
    ) {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = fileType
            putExtra(Intent.EXTRA_TITLE, fileName)
            //可选，添加一个初始路径
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && pickerInitialUri != null) {
                putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
            }
        }
        startActivityForResult(intent) {
            it?.data.runSafelyNoNull(block)
        }
    }

    /**
     * @param parentUri 要创建文件的父文件夹uri
     * @param displayName 要创建的文件的名称
     * @param mimeType 文件类型[com.kiylx.store_lib.kit.MimeTypeConsts]
     * @param block(documentFile) 创建文件后给与documentFile，或者创建失败将会抛出异常
     */
    override fun createDocumentFile(
        parentUri: Uri,
        displayName: String,
        mimeType: String,
        block: (file: DocumentFile?) -> Unit,
    ) {
        val df: DocumentFile? =
            fromTreeUri(requireActivity(), parentUri)
        val documentFile = df?.createFile(mimeType, displayName)
        documentFile.runSafelyNoNull(block)
    }

    /**
     * @param parentUri 要创建文件文件夹的父文件夹uri
     * @param displayName 要创建的文件夹的名称
     * @param block(documentFile) 创建文件夹后给与documentFile，或者创建失败给与null
     */
    override fun createDir(
        parentUri: Uri,
        displayName: String,
        block: (file: DocumentFile?) -> Unit,
    ) {
        val df = fromTreeUri(requireActivity(), parentUri)
        val document = df?.createDirectory(displayName)
        document.runSafelyNoNull(block)
    }

    /** 使用saf打开一个用户选择的文件 */
    override fun selectFile(
        pickerInitialUri: String?,
        fileType: String,
        block: noNullUriResult,/* = (uri: android.net.Uri) -> kotlin.Unit */
    ) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = fileType
            //可选，添加一个初始路径
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && pickerInitialUri != null) {
                putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
            }
        }
        startActivityForResult(intent) {
            it?.data.runSafelyNoNull(block)
        }
    }

    /**
     * Copies the given document.
     *
     * @param sourceFileUri document with
     *     [android.provider.DocumentsContract.Document.FLAG_SUPPORTS_COPY]
     * @param targetFolderUri document which will become a parent of the source
     *     document's copy.
     * @param block(uri) uri is the coped document, or {@code null} if failed.
     */
    @RequiresApi(24)
    override fun copyFile(
        sourceFileUri: Uri,
        targetFolderUri: Uri,
        block: noNullUriResult,/* = (uri: android.net.Uri) -> kotlin.Unit */
    ) {
        val uri: Uri? =
            DocumentsContract.copyDocument(
                getContentResolver(),
                sourceFileUri,
                targetFolderUri
            )
        uri.runSafelyNoNull(block)
    }

    /**
     * Moves the given document under a new parent.
     *
     * @param sourceFileUri document with
     *     [android.provider.DocumentsContract.Document.FLAG_SUPPORTS_MOVE]
     * @param sourceFileParentUri parent document of the document to move.
     * @param targetFolderUri document which will become a new parent of the
     *     source document.
     * @param block(uri) uri is the moved document, or {@code null} if failed.
     */
    @RequiresApi(24)
    override fun moveFile(
        sourceFileUri: Uri,
        sourceFileParentUri: Uri,
        targetFolderUri: Uri,
        block: noNullUriResult,/* = (uri: android.net.Uri) -> kotlin.Unit */
    ) {
        val uri: Uri? =
            DocumentsContract.moveDocument(
                getContentResolver(),
                sourceFileUri,
                sourceFileParentUri,
                targetFolderUri
            )
        uri.runSafelyNoNull(block)
    }

    override fun readFile(uri: Uri, input: input) {
        requireActivity().readFileFromUri(uri, input)
    }

    override fun writeFile(uri: Uri, output: output) {
        requireActivity().writeFileFromUri(uri, output)
    }

    /**
     * Change the display name of an existing document.
     *
     * If the underlying provider needs to create a new
     * [android.provider.DocumentsContract.Document.COLUMN_DOCUMENT_ID] to
     * represent the updated display name, that new document is returned and
     * the original document is no longer valid. Otherwise, the original
     * document is returned.
     *
     * @param sourceFileUri document with
     *     [android.provider.DocumentsContract.Document.FLAG_SUPPORTS_RENAME]
     * @param newName updated name for document
     * @param block(uri) the existing or new document after the rename, or
     *     {@code null} if failed.
     */
    override fun rename(
        sourceFileUri: Uri,
        newName: String,
        block: noNullUriResult,/* = (uri: android.net.Uri) -> kotlin.Unit */
    ) {
        DocumentsContract.renameDocument(
            getContentResolver(),
            sourceFileUri,
            newName
        ).runSafelyNoNull(block)
    }

    /**
     * Removes the given document from a parent directory.
     *
     * In contrast to [deleteFile] it requires specifying the parent. This
     * method is especially useful if the document can be in multiple parents.
     *
     * @param sourceFileUri document with
     *     [android.provider.DocumentsContract.Document.FLAG_SUPPORTS_REMOVE]
     * @param sourceFileParentUri parent document of the document to remove.
     * @param block(b) true if the document was removed successfully.
     */
    @RequiresApi(24)
    override fun removeFile(
        sourceFileUri: Uri,
        sourceFileParentUri: Uri,
        block: fileProcessResult, /* = (result: kotlin.Boolean) -> kotlin.Unit */
    ) {
        DocumentsContract.removeDocument(
            getContentResolver(),
            sourceFileUri, sourceFileParentUri
        ).run(block)
    }

    override fun deleteFile(uri: Uri, block: fileProcessResult) {
        DocumentsContract.deleteDocument(getContentResolver(), uri)
            .run(block)
    }

    override fun getContentResolver(): ContentResolver = requireActivity().contentResolver

    /**
     * Create a [DocumentFile] representing the document tree rooted
     * at the given [Uri]. This is only useful on devices running
     * [android.os.Build.VERSION_CODES.LOLLIPOP] or later, and will
     * return {@code null} when called on earlier platform versions.
     *
     * @param treeUri the [Intent.getData] from a successful
     *     [Intent.ACTION_OPEN_DOCUMENT_TREE] request.
     */
    override fun getDocumentTreeFile(treeUri: Uri): DocumentFile? {
        return fromTreeUri(requireActivity(), treeUri)
    }

    /**
     * Create a [DocumentFile] representing the single document
     * at the given [Uri]. This is only useful on devices running
     * [android.os.Build.VERSION_CODES.KITKAT] or later, and will
     * return {@code null} when called on earlier platform versions.
     *
     * @param singleUri the [Intent.getData] from a successful
     *     [Intent.ACTION_OPEN_DOCUMENT] or [Intent.ACTION_CREATE_DOCUMENT]
     *     request.
     */
    override fun getSingleDocumentFile(singleUri: Uri): DocumentFile? {
        return DocumentFile.fromSingleUri(requireActivity(), singleUri)
    }
}