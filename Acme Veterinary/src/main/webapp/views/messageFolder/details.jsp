<%--
 * message.jsp
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



	<b><spring:message code="messageFolder.name" />:</b>
	<jstl:out value="${messageFolder.name}" />
	<br />

	<b><spring:message code="messageFolder.system" />:</b>
	<jstl:choose>
		<jstl:when test="${messageFolder.system == true}">
			<spring:message code="messageFolder.yes" />
		</jstl:when>
		<jstl:otherwise>
			<spring:message code="messageFolder.no" />
		</jstl:otherwise>
	</jstl:choose>
	<br />

	<b><spring:message code="messageFolder.messages" />:</b>
	<jstl:out value="${messageFolder.messages.size()}" />
	<br /><br />

	

<form:form action="messageFolder/actor/details.do" modelAttribute="messageFolder">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="name" />
	<form:hidden path="system" />
	<form:hidden path="messages" />

		<input type="button" onclick="javascript:history.back();"
			value="<spring:message code='messageFolder.cancel' />" />


</form:form>