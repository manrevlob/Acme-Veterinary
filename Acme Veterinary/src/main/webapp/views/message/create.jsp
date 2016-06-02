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

<form:form action="message/actor/create.do" modelAttribute="sendMessage">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="sender" />
	<form:hidden path="moment" />
	<form:hidden path="messageFolder" />

	<form:label path="subject">
		<spring:message code="message.subject" />:
	</form:label>
	<form:input path="subject" />
	<form:errors cssClass="error" path="subject" />
	<br />

	<form:label path="body">
		<spring:message code="message.body" />:
	</form:label>
	<form:textarea path="body" />
	<form:errors cssClass="error" path="body" />
	<br />

	<form:label path="recipient">
		<spring:message code="message.recipient" />:
	</form:label>
	<form:select id="recipient" path="recipient">
		<form:options items="${recipients}" itemValue="id"
			itemLabel="userAccount.username" />
	</form:select>
	<form:errors cssClass="error" path="recipient" />
	<br />

	<input type="submit" name="send"
		value="<spring:message code="message.send" />" />&nbsp;
			
	<input type="button" name="cancel"
		value="<spring:message code="message.cancel" />"
		onclick="javascript: window.location.replace('messageFolder/actor/list.do')" />
	<br />

</form:form>