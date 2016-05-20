<%--
 * list.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<display:table pagesize="10" class="displaytag" keepStatus="true"
	name="orders" requestURI="${requestURI}" id="row">

	<spring:message code="order.ticker" var="tickerHeader" />
	<display:column property="ticker" title="${tickerHeader}" />

	<spring:message code="order.comment" var="commentHeader" />
	<display:column property="comment" title="${commentHeader}" />


	<spring:message code="order.moment" var="momentHeader" />
	<display:column title="${momentHeader}" sortable="true">
		<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${row.moment}" />
	</display:column>

	<spring:message code="order.isCanceled" var="canceledHeader" />
	<display:column property="isCanceled" title="${canceledHeader}"
		sortable="true" />

	<security:authorize access="hasRole('CUSTOMER')">

		<display:column>
			<a href="order/customer/details.do?orderId=${row.id}"> <spring:message
					code="order.details" />
			</a>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('ADMIN')">

		<display:column>
			<a href="order/administrator/details.do?orderId=${row.id}"> <spring:message
					code="order.details" />
			</a>
		</display:column>
	</security:authorize>

</display:table>
