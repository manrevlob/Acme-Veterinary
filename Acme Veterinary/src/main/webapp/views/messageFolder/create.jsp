<%--
 * edit.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="messageFolder">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="system" />

	<acme:textbox code="messageFolder.name" path="name"/>

	<acme:submit name="save" code="messageFolder.save"/>
		
	<jstl:if test="${messageFolder.system == false && edit == true }">
		<acme:delete name="delete" code="messageFolder.delete"/>		
	</jstl:if>
	<acme:cancel url="messageFolder/actor/list.do" code="messageFolder.cancel"/>
	


</form:form>