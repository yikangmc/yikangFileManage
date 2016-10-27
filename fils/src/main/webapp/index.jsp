<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>易康美辰</title>
</head>
<body>
	<form id="file_upload_id"
		action="<%=basePath%>fileUpload/doFileUpload"
		enctype="multipart/form-data" method="post">
		<div>
			<input type="file" name="file" />
		</div>
		<div>
			<input type="submit" value="上传" />
		</div>
	</form>
</body>
</html>
