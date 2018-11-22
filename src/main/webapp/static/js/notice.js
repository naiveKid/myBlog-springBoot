function checkContent(){
	var content = document.getElementById("content").value;
	if(content==""){
		alert("请输入留言内容！");
		document.getElementById("content").focus();
		return false;
	}
	document.forms["noticeForm"].submit();
}