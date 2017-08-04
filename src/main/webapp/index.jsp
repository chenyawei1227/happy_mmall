<%@ page language="java" import="java.util.*" contentType="text/html;charset=utf-8"%>

<html>
<body>
    <h2>Hello World!</h2>

    <p>springMvc普通上传文件</p>
    <form name="form1" action="/manage/product/upload.do" method="post" enctype="multipart/form-data">
        <input type="file" name="upload_file" />
        <input type="submit" value="springmvc上传文件"/>
    </form>

    <p>富文本图片上传文件</p>
    <form name="form1" action="/manage/product/richtext_img_upload.do" method="post" enctype="multipart/form-data">
        <input type="file" name="upload_file" />
        <input type="submit" value="富文本上传文件"/>
    </form>
</body>
</html>
