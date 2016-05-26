<%--
 * message.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<b><spring:message code="appointment.customer" />:</b>
<jstl:out value="${appointment.pet.customer.name}" />
<br />

<b><spring:message code="appointment.pet" />:</b>
<jstl:out value="${appointment.pet.name}" />
<br />

<b><spring:message code="appointment.reason" />:</b>
<jstl:out value="${appointment.reason}" />
<br />

<b><spring:message code="appointment.moment" />:</b>
<fmt:formatDate pattern="dd-MM-yyyy HH:mm" value="${appointment.moment}" />
<br />

<jstl:if test="${appointment.payment!=  null}">

	<b><spring:message code="payment.totalCost" />:</b>
	<jstl:out value="${appointment.payment.totalCost.amount}" />
	<jstl:out value="${appointment.payment.totalCost.currency}" />

	<jstl:if test="${appointment.payment.creditCard!=  null}">
		<table>
			<tr>
				<td><spring:message code="creditCard.name" /></td>
				<td><spring:message code="creditCard.brand" /></td>
				<td><spring:message code="creditCard.number" /></td>
			</tr>
			<tr>
				<td><jstl:out value="${appointment.payment.creditCard.name}" /></td>
				<td><jstl:out value="${appointment.payment.creditCard.brand}" /></td>
				<td><jstl:out value="${appointment.payment.creditCard.number}" /></td>
			</tr>

		</table>
	</jstl:if>
</jstl:if>

<br />
<security:authorize access="hasRole('VETERINARY')">
	<jstl:if test="${appointment.history == null}">
		<a class="btn btn-primary" href="history/veterinary/create.do?appointmentId=${appointment.id}">
			<spring:message code="appointment.history.create" />
		</a>
	&nbsp;

</jstl:if>

	<jstl:if test="${appointment.payment == null}">
		<a class="btn btn-warning" href="payment/veterinary/create.do?appointmentId=${appointment.id}">
			<spring:message code="appointment.payment.create" />
		</a>&nbsp;&nbsp;
	</jstl:if>
</security:authorize>
<security:authorize access="hasRole('CUSTOMER')">
	<acme:cancel url="appointment/customer/list.do" code="appointment.cancel"/>
</security:authorize>
<security:authorize access="hasRole('VETERINARY')">
	<acme:cancel url="appointment/veterinary/list.do" code="appointment.cancel"/>
</security:authorize>