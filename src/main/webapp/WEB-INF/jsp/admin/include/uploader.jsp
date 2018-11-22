<%@page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<script type="text/javascript">
    //1.初始化WebUpload，以及配置全局的参数
    var uploader = WebUploader.create({
        //flashk控件的地址
        swf: "/static/css/Uploader.swf",
        //后台提交地址
        server: "/web/Upload",
        //选择文件控件的标签
        pick: "#filePicker",
        //自动上传文件
        auto: true,
        // 只允许选择图片文件。
        accept: {
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/*'
        }
    });

    uploader.on("fileQueued", function (file) {
        //追加文件信息div
        $("#fileList")
            .html(
                "<div id='" + file.id + "'><img class='fileInfo' /><span>"
                + file.name
                + "</span><div class='state'>等待上传...</div><span class='text'></span></div>");

        uploader.makeThumb(file, function (error, src) {
            var id = $("#" + file.id);
            //如果失败，则显示“不能预览”
            if (error) {
                id.find("img").replaceWith("不能预览");
            }
            //成功，则显示缩略图到指定位置
            id.find("img").attr("src", src);
        });
    });

    uploader.on("uploadProgress", function (file, percentage) {
        var id = $("#" + file.id);
        //更新状态信息
        id.find("div.state").text("上传中...");
        //更新上传百分比
        id.find("span.text").text(Math.round(percentage * 100) + "%");
    });

    uploader.on("uploadSuccess", function (file, response) {
        //更新状态信息
        $("#" + file.id).find("div.state").text("上传完毕");
        var data = $.parseJSON(response._raw);
        $("#pictureId").val(data.pictureId);
        $("#pictureName").val(data.pictureName);
    });
</script>