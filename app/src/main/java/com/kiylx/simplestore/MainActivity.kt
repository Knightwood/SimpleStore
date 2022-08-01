package com.kiylx.simplestore

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.annotation.WorkerThread
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.documentfile.provider.DocumentFile
import com.google.android.material.button.MaterialButton
import com.kiylx.store_lib.StoreX
import com.kiylx.store_lib.kit.MimeTypeConsts
import com.kiylx.store_lib.kit.ext.filter
import com.kiylx.store_lib.kit.ext.makeToast
import com.kiylx.store_lib.kit.ext.writeFileFromUri
import com.kiylx.store_lib.mediastore.FileLocate
import com.kiylx.store_lib.mediastore.MediaStoreHelper
import com.kiylx.store_lib.saf.SAFHelper
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread


//AndroidManifest.xml中声明：android:hasFragileUserData="true"，卸载应用会有提示是否保留 APP数据。
//默认应用卸载时App-specific目录下的数据被删除，但用户可以选择保留。

// 注：
// 1、如果你想遍历出非本 app 创建的文件，则需要先申请 READ_EXTERNAL_STORAGE 权限
// 2、如果你的 app 卸载后再重装的话系统不会认为是同一个 app（也就是你卸载之前创建的文件，再次安装 app 后必须先申请 READ_EXTERNAL_STORAGE 权限后才能获取到）

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var helper: SAFHelper.Helper
    private lateinit var mediaStoreHelper: MediaStoreHelper.Helper
    private val hander = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        helper = StoreX.initSaf(this)
        mediaStoreHelper = StoreX.initMediaStore(this)
        requestPermission()
        findViewById<MaterialButton>(R.id.request_prem).setOnClickListener(this)
        findViewById<MaterialButton>(R.id.save_prem).setOnClickListener(this)
        findViewById<MaterialButton>(R.id.create_file).setOnClickListener(this)
        findViewById<MaterialButton>(R.id.delete_file).setOnClickListener(this)
        findViewById<MaterialButton>(R.id.write_file).setOnClickListener(this)
        findViewById<MaterialButton>(R.id.read_file).setOnClickListener(this)
        findViewById<MaterialButton>(R.id.select_file).setOnClickListener(this)
        findViewById<MaterialButton>(R.id.newPic).setOnClickListener(this)
        findViewById<MaterialButton>(R.id.queryVideo).setOnClickListener(this)

    }

    //申请的文件夹权限的uri
    var treeUri: Uri? = null
        set(value) {
            field = value
            value?.let { saveFileTree(it) }
        }
        get() {
            if (field == null) {
                val tmp = getFileTree()
                return Uri.parse(tmp)
            } else {
                return field
            }
        }

    //测试读写的文件，被建立在treeUri表示的文件夹下
    var testFileUri: Uri = Uri.EMPTY

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onClick(p0: View?) {
        p0?.let { view ->
            when (view.id) {
                R.id.request_prem -> {
                    helper.requestOneFolder { tree_ ->
                        //拿到了被授予读写权限的路径，使用helper.savePerms(tree)保存权限后，可以自己存起来下次使用
                        treeUri = tree_
                        DocumentFile.fromTreeUri(this@MainActivity, tree_)?.let { df ->
                            //使用DocumentFile创建文件
                            df.createFile(MimeTypeConsts.txt, "测试saf框架")?.let {
                                helper.writeFile(it.uri) { outputStream ->
                                    val str = "DocumentFile测试\n"
                                    outputStream.use { oStream ->
                                        //获取DocumentFile的uri，然后写入东西
                                        oStream.write(str.toByteArray(StandardCharsets.UTF_8))
                                    }
                                }
                            }
                        }
                    }
                }
                R.id.save_prem -> {
                    treeUri?.let { it1 -> helper.savePerms(it1) }
                }
                R.id.create_file -> {
                    helper.createFile("测试文件", MimeTypeConsts.txt, treeUri.toString()) {
                        testFileUri = it
                        Log.d(tag, "$testFileUri")
                    }
                }
                R.id.delete_file -> {
                    if (testFileUri == Uri.EMPTY) {
                        makeToast("没有文件可删除")
                    } else
                        helper.deleteFile(testFileUri) {
                            if (it) {
                                testFileUri = Uri.EMPTY
                                makeToast("文件删除成功")
                            }
                        }
                }
                R.id.write_file -> {
                    if (testFileUri == Uri.EMPTY) {
                        makeToast("没有文件可写入")
                    } else
                        helper.writeFile(testFileUri) { outputStream ->
                            val str = "Storage Access Framework Example\n"
                            outputStream.use { oStream ->
                                oStream.write(str.toByteArray(StandardCharsets.UTF_8))
                            }
                        }
                }
                R.id.read_file -> {
                    if (testFileUri == Uri.EMPTY) {
                        makeToast("没有文件可读取")
                    } else
                        helper.readFile(testFileUri) {
                            val bytes = it.readBytes()
                            makeToast(bytes.toString())
                        }
                }
                R.id.select_file -> {
                    helper.selectFile {
                        testFileUri = it
                        Log.d(tag, "被选择文件：$it")
                    }
                }
                R.id.newPic -> {
                    val bitmap =
                        (resources.getDrawable(R.drawable.shotcut1, null) as BitmapDrawable)
                            .bitmap
                    mediaStoreHelper.newPhoto("测试图片", MimeTypeConsts.png) {
                        writeFileFromUri(it) { os ->
                            bitmap.compress(Bitmap.CompressFormat.PNG, 85, os)
                        }
                    }
                }
                R.id.queryVideo -> {
                    thread { query() }
                }
                else -> {

                }
            }
        }
    }

    /**
     * 这是一个查询15s以上视频的demo，谷歌的一个示例
     */
    @WorkerThread
    private fun query() {
        val videoList = mutableListOf<Video>()

        //查询列
        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.SIZE
        )

        //查询条件：视频时长
        val selection = "${MediaStore.Video.Media.DURATION} >= ?"
        //查询条件的值：15s以上
        val selectionArgs = arrayOf(
            TimeUnit.MILLISECONDS.convert(15, TimeUnit.SECONDS).toString()
        )

        //排序：名称的升序
        val sortOrder = "${MediaStore.Video.Media.DISPLAY_NAME} ASC"

        mediaStoreHelper.queryFile(FileLocate.VIDEO,
            projection,
            selection,
            selectionArgs,
            sortOrder) { cursor ->
            // Cache column indices.
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val nameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val durationColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
            val sizeColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)

            cursor.filter {
                // 比如这个地址 content://media/external/images/media/102 它的后面的 102 就是它的 id
                val id = it.getLong(idColumn)
                val name = it.getString(nameColumn)
                val duration = it.getInt(durationColumn)
                val size = it.getInt(sizeColumn)

                // 获取文件的真实路径，类似 /storage/emulated/0/Pictures/A目录/B文件.jpg
                val path = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))

                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    id
                )
                // 将列值和 contentUri 存储在表示媒体文件的本地对象中。
                videoList += Video(contentUri, name, duration, size)

                false
            }
            if (videoList.isNotEmpty())
                hander.post {
                    makeToast("获取了${videoList.size}个视频,第一个:${videoList.first().name}")
                }
            else
                hander.post {
                    makeToast("空白")
                }
        }
    }

    // 如果要用 MediaStore 读取非其他 app 创建的文件，要在AndroidManifest.xml中
    // 添加 <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" /> 并申请权限
    // 注：WRITE_EXTERNAL_STORAGE 权限已经无效了
    private fun requestPermission() {
        val storagePermissions = arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE)
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, storagePermissions, 233)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (i in permissions.indices) {
            Log.i(tag, "申请权限列表：" + permissions[i] + "；结果：" + grantResults[i])
        }
    }

    companion object {
        const val tag = "MainActivity"
    }
}

data class Video(
    val uri: Uri,
    val name: String,
    val duration: Int,
    val size: Int,
)
