# 用途

* 简化了下`SAF`访问框架的使用

* 添加了一点点`MediaStore`的简便方法

# 如何使用

## SAF部分

1. 在`FragmentActivity`或`Fragment`中初始化一个`helper`对象。（不一定非要在`onCreate`之类的地方初始化）

```
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
		val helper: SAFHelper.Helper = StoreX.initSaf(this)
	}
```

2. 调用`helper`中的方法

```
//申请一个目录的读写权限
helper.requestOneFolder {uri-> //被用户选择的目录uri

}

//保存目录的读写权限
helper.savePerms(uri)

//删除一个文件
helper.deleteFile(fileUri) {b->
  if (b) {
     makeToast("文件删除成功")
  }
}

//写文件
 helper.writeFile(testFileUri) { outputStream ->
    val str = "Storage Access Framework Example\n"
    outputStream.use { oStream ->
    	oStream.write(str.toByteArray(StandardCharsets.UTF_8))
         }
       }

//读文件
 helper.readFile(testFileUri) {
   val bytes = it.readBytes()
   makeToast(bytes.toString())
                    
//选择文件
helper.selectFile {
	testFileUri = it
	Log.d(tag, "被选择文件：$it")
}
```

## MediaStore部分

1. 在`FragmentActivity`或`Fragment`中初始化一个`helper`对象。（不一定非要在`onCreate`之类的地方初始化）

```
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
		val helper: MediaStoreHelper.Helper = StoreX.initMediaStore(this)
	}
```

2. 调用`helper`中的方法，例如：

​				把一张图片存进图库里

```
  val bitmap =
      (resources.getDrawable(R.drawable.shotcut1, null) as BitmapDrawable)
      .bitmap
      mediaStoreHelper.newPhoto("测试图片", MimeTypeConsts.png) {
          writeFileFromUri (it) { os ->
          bitmap.compress(Bitmap.CompressFormat.PNG, 85, os)
             }
   }
```



* 更多用法暂时没时间写了，在app module下有示例代码。

