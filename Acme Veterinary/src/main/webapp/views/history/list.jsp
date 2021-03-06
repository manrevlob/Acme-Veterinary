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

<display:table name="histories" pagesize="10" class="displaytag"
	requestURI="${requestURI}" id="row">

	<spring:message code="history.moment" var="momentHeader" />
	<fmt:formatDate pattern="dd-MM-yyyy HH:mm" value="${row.moment}" />

	<spring:message code="history.diagnosis" var="diagnosisHeader" />
	<display:column property="diagnosis" title="${diagnosisHeader}"
		sortable="true" />

	<spring:message code="history.treatmentQ" var="treatmentHeader" />
	<display:column title="${treatmentHeader}" sortable="true">
		<jstl:choose>
			<jstl:when test="${row.treatment == null}">
				<spring:message code="history.no" />
			</jstl:when>
			<jstl:when test="${row.treatment != null}">
				<spring:message code="history.yes" />
			</jstl:when>
		</jstl:choose>
	</display:column>

	<security:authorize access="hasRole('CUSTOMER')">
		<display:column title="${detailsHeader}">
			<a href="history/customer/details.do?historyId=${row.id}"> <spring:message
					code="history.details" />
			</a>
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('VETERINARY')">
		<display:column title="${detailsHeader}">
			<a href="history/veterinary/details.do?historyId=${row.id}"> <spring:message
					code="history.details" />
			</a>
		</display:column>
	</security:authorize>

</display:table>
<br>
<jstl:if test="${requestURI == 'history/veterinary/list.do'}">
	<acme:cancel url="customer/veterinary/list.do" code="history.cancel"/>
</jstl:if>
<jstl:if test="${requestURI == 'history/customer/list.do'}">
	<acme:cancel url="customer/veterinary/listPet.do?customerId=${customerId}" code="history.cancel"/>
</jstl:if>

