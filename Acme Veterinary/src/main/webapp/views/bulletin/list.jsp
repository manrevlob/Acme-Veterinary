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


<jstl:if test="${requestURI == 'bulletin/list.do'}">

	<display:table name="bulletins" pagesize="10" class="displaytag"
		requestURI="${requestURI}" id="row">

		<spring:message code="clinic.name" var="nameHeader" />
		<display:column value="${row[0].name}" title="${nameHeader}"
			sortable="true" />

		<spring:message code="bulletin.description" var="descriptionHeader" />
		<display:column value="${row[1].description}"
			title="${descriptionHeader}" sortable="true" />

		<spring:message code="bulletin.moment" var="momentHeader" />
		<display:column value="${row[1].moment}" title="${momentHeader}"
			sortable="true" />

	</display:table>
</jstl:if>


<jstl:if test="${requestURI == 'bulletin/listByClinic.do'}">
	<display:table name="bulletins" pagesize="10" class="displaytag"
		requestURI="${requestURI}" id="row">

		<spring:message code="bulletin.description" var="descriptionHeader" />
		<display:column value="${row.description}"
			title="${descriptionHeader}" sortable="true" />

		<spring:message code="bulletin.moment" var="momentHeader" />
		<display:column value="${row.moment}" title="${momentHeader}"
			sortable="true" />
		<security:authorize access="hasRole('ADMIN')">
			<display:column>
				<a href="bulletin/administrator/edit.do?bulletinId=${row.id}"> <spring:message
						code="bulletin.edit" />
				</a>
			</display:column>

			<display:column>
				<a
					onclick="return confirm('<spring:message code="bulletin.delete.confirm" />')"
					href="bulletin/administrator/delete.do?bulletinId=${row.id}"> <spring:message
						code="bulletin.delete" />
				</a>
			</display:column>
		</security:authorize>
	</display:table>

	<security:authorize access="hasRole('ADMIN')">

		<a class="btn btn-primary" href="bulletin/administrator/create.do?clinicId=${clinicId}"> <spring:message
				code="bulletin.create" />
		</a>
	</security:authorize>
</jstl:if>


