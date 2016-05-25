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

<security:authorize access="hasRole('VETERINARY')">
	<h2>
		<spring:message code="appointment.infoVet" />
	</h2>
</security:authorize>

<display:table name="appointments" pagesize="10" class="displaytag"
	requestURI="${requestURI}" id="row">

	<spring:message code="appointment.moment" var="momentHeader" />
	<display:column title="${momentHeader}">
		<fmt:formatDate pattern="dd-MM-yyyy HH:mm" value="${row.moment}" />
	</display:column>

	<spring:message code="appointment.day" var="dayHeader" />
	<display:column title="${dayHeader}" sortable="true">
		<fmt:formatDate pattern="dd-MM-yyyy" value="${row.day}" />
	</display:column>

	<spring:message code="appointment.startTime" var="startTimeHeader" />
	<display:column property="startTime" title="${startTimeHeader}"
		sortable="true" />

	<spring:message code="appointment.endTime" var="endTimeHeader" />
	<display:column property="endTime" title="${endTimeHeader}"
		sortable="true" />


	<spring:message code="appointment.reason" var="reasonHeader" />
	<display:column property="reason" title="${reasonHeader}"
		sortable="true" />

	<security:authorize access="isAuthenticated()">
		<security:authorize access="hasRole('CUSTOMER')">
			<display:column title="${detailsHeader}">
				<a href="appointment/customer/details.do?appointmentId=${row.id}">
					<spring:message code="appointment.details" />
				</a>
			</display:column>
			<display:column title="${cancelHeader}">
				<a
					onclick="return confirm('<spring:message code="appointment.delete.confirm" />')"
					href="appointment/customer/cancel.do?appointmentId=${row.id}">
					<spring:message code="appointment.cancelAppointment" />
				</a>
			</display:column>
		</security:authorize>
		<security:authorize access="hasRole('VETERINARY')">

			<display:column title="${detailsHeader}">
				<a href="appointment/veterinary/details.do?appointmentId=${row.id}">
					<spring:message code="appointment.details" />
				</a>
			</display:column>
			<display:column title="${cancelHeader}">
				<a
					onclick="return confirm('<spring:message code="appointment.delete.confirm" />')"
					href="appointment/veterinary/cancel.do?appointmentId=${row.id}">
					<spring:message code="appointment.cancelAppointment" />
				</a>
			</display:column>
		</security:authorize>
		<security:authorize access="hasRole('ADMIN')">
			<display:column title="${cancelHeader}">
				<a
					onclick="return confirm('<spring:message code="appointment.delete.confirm" />')"
					href="appointment/administrator/cancel.do?appointmentId=${row.id}">
					<spring:message code="appointment.cancelAppointment" />
				</a>
			</display:column>
		</security:authorize>
	</security:authorize>


</display:table>

