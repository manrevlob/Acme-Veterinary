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

<form:form action="pet/customer/edit.do" modelAttribute="pet">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="customer" />
	<form:hidden path="isDeleted" />

	<acme:textbox code="pet.name" path="name" />

	<acme:textbox code="pet.birthDate" path="birthDate"
		placeholder="dd/MM/yyyy" />

	<acme:select items="${petTypes}" itemLabel="name" code="pet.petType"
		path="petType" />

	<acme:submit name="save" code="pet.save" />

	<input class="btn" type="button" name="cancel"
		value="<spring:message code="pet.cancel" />"
		onclick="javascript: window.location.replace('pet/customer/list.do')" />

</form:form>
