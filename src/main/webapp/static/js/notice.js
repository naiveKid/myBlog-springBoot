function checkContent(){
	var content = document.getElementById("content").value;
	if(content==""){
        layer.alert("请输入留言内容！", {icon: 2});
		document.getElementById("content").focus();
		return false;
	}
	document.forms["noticeForm"].submit();
}