<%--
 * forms.jsp
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

<form:form action="message/actor/moveToFolder.do" modelAttribute="moveMessageForm">

	<form:hidden path="message"/>

	<form:label path="messageFolder">
		<spring:message code="messageFolder.name" />:
	</form:label>
	<form:select id="messageFolder" path="messageFolder">
		<form:option value="0" label="----" />
		<form:options items="${messageFolders}" itemValue="id" itemLabel="name" />
	</form:select>
	<form:errors cssClass="error" path="messageFolder" />
	<br />

	<input type="submit" name="save"
		value="<spring:message code="actor.save" />" />&nbsp; 
	<input type="button" name="cancel"
		value="<spring:message code="actor.cancel" />"
		onclick="javascript: window.location.replace('messageFolder/actor/list.do')" />
	<br />

</form:form>