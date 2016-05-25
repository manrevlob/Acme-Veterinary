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

	<security:authorize access="hasRole('CUSTOMER')">
		<display:column>
			<a href="pet/customer/details.do?petId=${row.id}"> <spring:message
					code="pet.details" />
			</a>
		</display:column>

		<display:column title="${HistoryHeader}">
			<a href="history/customer/list.do?petId=${row.id}"> <spring:message
					code="pet.seeHistory" />
			</a>
		</display:column>

		<display:column title="${editHeader}">
			<a href="pet/customer/edit.do?petId=${row.id}"> <spring:message
					code="pet.edit" />
			</a>
		</display:column>

		<display:column>
			<jstl:if test="${row.isDeleted == false}">
				<a
					onclick="return confirm('<spring:message code="pet.delete.confirm" />')"
					href="pet/customer/delete.do?petId=${row.id}"> <spring:message
						code="pet.delete" />
				</a>
			</jstl:if>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('VETERINARY')">
		<display:column title="${HistoryHeader}">
			<a href="history/veterinary/list.do?petId=${row.id}"> <spring:message
					code="pet.seeHistory" />
			</a>
		</display:column>
	</security:authorize>

</display:table>
<security:authorize access="hasRole('CUSTOMER')">
	<a class="btn btn-primary" href="pet/customer/create.do"> <spring:message
			code="pet.create" />
	</a>
</security:authorize>
