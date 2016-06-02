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
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<display:table name="petTypes" pagesize="10" class="displaytag"
	requestURI="${requestURI}" id="row">

	<spring:message code="petType.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="petType.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" />

	<display:column title="${editHeader}">
		<a href="petType/administrator/edit.do?petTypeId=${row.id}"> <spring:message
				code="petType.edit" />
		</a>
	</display:column>
</display:table>

<security:authorize access="hasRole('ADMIN')">
	<a class="btn btn-primary" href="petType/administrator/create.do">
		<spring:message code="petType.create" />
	</a>
</security:authorize>
