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

<form:form action="profile/actor/edit.do" modelAttribute="actor">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<acme:textbox code="actor.name" path="name" />
	<acme:textbox code="actor.surname" path="surname" />

	<acme:textbox code="actor.phone" path="phone" />
	<acme:textbox code="actor.email" path="email" />
	<br />

	<acme:submit name="save" code="profile.save" />
	<input type="button" name="cancel"
		value="<spring:message code="profile.cancel" />"
		onclick="javascript:history.back();" />

</form:form>
