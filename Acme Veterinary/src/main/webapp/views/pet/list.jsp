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

<display:table name="pets" pagesize="10" class="displaytag"
	requestURI="${requestURI}" id="row">

	<spring:message code="pet.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="pet.type" var="typeHeader" />
	<display:column property="type" title="${typeHeader}" sortable="true" />

	<spring:message code="pet.birthDate" var="birthDateHeader" />
	<fmt:formatDate pattern="dd-MM-yyyy" value="${row.birthDate}" />

	<security:authorize access="hasRole('CUSTOMER')">
		<display:column title="${HistoryHeader}">
			<a href="history/customer/list.do?petId=${row.id}"> <spring:message
					code="pet.seeHistory" />
			</a>
		</display:column>
	</security:authorize>

</display:table>

