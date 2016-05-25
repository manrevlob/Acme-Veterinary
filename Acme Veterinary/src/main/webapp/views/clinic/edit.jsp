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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="clinic/administrator/edit.do"
	modelAttribute="clinic">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<acme:textbox code="clinic.name" path="name"/>
	<acme:textbox code="clinic.address" path="address"/>
	<acme:textbox code="clinic.zipCode" path="zipCode"/>
	<acme:textbox code="clinic.pictures" path="pictures"/>

	<acme:submit name="save" code="clinic.save"/>
	<acme:cancel url="clinic/list.do" code="clinic.cancel"/>
	
	<jstl:if test="${clinic.id != 0}">
		<acme:delete name="delete" code="clinic.delete"/>
	</jstl:if>

</form:form>
