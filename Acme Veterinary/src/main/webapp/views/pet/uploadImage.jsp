<%--
 * action-2.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<html>
<head>
<title>Upload File Request Page</title>
</head>
<body>

	<form method="POST" action="pet/customer/uploadImage.do"
		enctype="multipart/form-data">
		<input type="hidden" name="petId" value="${petId}"> 
		File to upload: <input type="file" name="file"  accept="image/x-png">
		<br /> 
		<br /> 
		<input type="submit" name="upload"> Press here to upload the file!
	</form>

</body>
</html>  
