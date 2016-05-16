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

<form:form action="appointment/customer/save.do"
	modelAttribute="appointmentForm">

	<form:hidden path="startMoment" />
	<form:hidden path="startTime" />
	<form:hidden path="endTime" />
	<form:hidden path="veterinary" />

	<acme:select items="${pets }" itemLabel="name" code="appointment.pet" path="pet"/>
	<acme:textarea code="appointment.reason" path="reason"/>
	
	<acme:submit name="save" code="appointment.save"/>
	

</form:form>
