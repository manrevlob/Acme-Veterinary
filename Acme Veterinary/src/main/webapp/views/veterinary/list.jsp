<%-- 
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="veterinaries" pagesize="10" class="displaytag"
	requestURI="${requestURI}" id="row">

	<spring:message code="veterinary.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="veterinary.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}"
		sortable="true" />

<security:authorize access="hasRole('CUSTOMER')">
	<display:column title="${appointmentHeader}">
		<a href="appointment/list.do?veterinaryId=${row.id}"> <spring:message
				code="veterinary.appointment" />
		</a>
	</display:column>
</security:authorize>
<security:authorize access="hasRole('ADMIN')">
	<display:column title="${appointmentHeader}">
		<a href="appointment/administrator/list.do?veterinaryId=${row.id}"> <spring:message
				code="veterinary.appointment" />
		</a>
	</display:column>
</security:authorize>
</display:table>
<acme:cancel url="clinic/list.do" code="veterinary.cancel"/>