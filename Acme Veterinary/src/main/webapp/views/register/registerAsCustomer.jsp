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

<form:form action="register/createCustomer.do"
	modelAttribute="customerForm">
	
	<acme:textbox code="customer.username" path="username" />

	<acme:password code="customer.password" path="password" />

	<acme:password code="customer.secondPassword" path="secondPassword" />

	<acme:textbox code="customer.name" path="name" />

	<acme:textbox code="customer.surname" path="surname" />

	<acme:textbox code="customer.phone" path="phone" />
	
	<acme:textbox code="customer.email" path="email" />

		
	<br />
	<spring:message code='customer.terms' />
	<br />

	<acme:submit name="save" code="customer.save" />

	<input type="button" name="cancel"
		value="<spring:message code="customer.cancel" />"
		onclick="javascript: window.location.replace('welcome/index.do')" />

</form:form>