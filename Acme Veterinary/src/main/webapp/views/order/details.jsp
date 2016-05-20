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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<b><spring:message code="order.ticker" />:</b>
<jstl:out value="${order.ticker}" />
<br />

<b><spring:message code="order.fullName" />:</b>
<jstl:out value="${order.fullName}" />
<br />

<b><spring:message code="order.comment" />:</b>
<jstl:out value="${order.comment}" />
<br />

<b><spring:message code="order.address" />:</b>
<jstl:out value="${order.address}" />
<br />

<b><spring:message code="order.moment" />:</b>
<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${order.moment}" />
<br />

<b><spring:message code="order.price.amount" />:</b>
<jstl:out value="${order.totalPrice.amount}" />

<br>


<table>
	<tr>
		<td><spring:message code="order.creditCard.name" /></td>
		<td><spring:message code="order.creditCard.brand" /></td>
		<td><spring:message code="order.creditCard.number" /></td>
	</tr>
	<tr>
		<td><jstl:out value="${order.creditCard.name}" /></td>
		<td><jstl:out value="${order.creditCard.brand}" /></td>
		<td><jstl:out value="${order.creditCard.number}" /></td>
	</tr>

</table>

<display:table pagesize="10" class="displaytag" keepStatus="true"
	name="itemOrders" requestURI="${requestURI}" id="row">

	<spring:message code="itemOrder.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="itemOrder.price" var="priceHeader" />
	<display:column property="price.amount" title="${priceHeader}"
		sortable="true" />

	<spring:message code="itemOrder.currency" var="currencyHeader" />
	<display:column property="price.currency" title="${currencyHeader}" />

	<spring:message code="itemOrder.quantity" var="quantityHeader" />
	<display:column property="quantity" title="${quantityHeader}"
		sortable="true" />

</display:table>

<security:authorize access="hasRole('CUSTOMER')">
	<jstl:if test="${order.isCanceled == false}">
		<a href="order/customer/cancel.do?orderId=${order.id}"> <spring:message
				code="order.cancel" />
		</a>
	</jstl:if>
</security:authorize>

<security:authorize access="hasRole('ADMIN')">
	<jstl:if test="${order.isCanceled == false}">
		<a href="order/administrator/cancel.do?orderId=${order.id}"> <spring:message
				code="order.cancel" />
		</a>
	</jstl:if>
</security:authorize>

