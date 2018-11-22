<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');

    function getContent() {
        document.getElementById("content").value = UE.getEditor('editor').getContent();
        document.getElementById("text").value = UE.getEditor('editor').getPlainTxt();
    }
</script>