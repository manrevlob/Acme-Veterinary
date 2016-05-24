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

<display:table name="clinics" pagesize="10" class="displaytag"
	requestURI="${requestURI}" id="row">

	<spring:message code="clinic.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="clinic.address" var="addressHeader" />
	<display:column property="address" title="${addressHeader}" />

	<spring:message code="clinic.zipCode" var="zipCodeHeader" />
	<display:column property="zipCode" title="${zipCodeHeader}" />

	<spring:message code="clinic.pictures" var="picturesHeader" />
	<display:column property="pictures" title="${picturesHeader}" />


	<display:column title="${veterinariesHeader}">
		<a href="veterinary/list.do?clinicId=${row.id}"> <spring:message
				code="clinic.veterinaries" />
		</a>
	</display:column>

	<display:column title="${detailsHeader}">
		<a href="clinic/details.do?clinicId=${row.id}"> <spring:message
				code="clinic.details" />
		</a>
	</display:column>


	<display:column title="${bulletinHeader}">
		<a href="bulletin/listByClinic.do?clinicId=${row.id}"> <spring:message
				code="clinic.bulletins" />
		</a>
	</display:column>

	<security:authorize access="hasRole('ADMIN')">
		<display:column title="${editHeader}">
			<a href="clinic/administrator/edit.do?clinicId=${row.id}"> <spring:message
					code="clinic.edit" />
			</a>
		</display:column>
	</security:authorize>
</display:table>

<security:authorize access="hasRole('ADMIN')">
	<a class="btn btn-primary" href="clinic/administrator/create.do"> <spring:message
			code="clinic.create" />
	</a>
</security:authorize>
