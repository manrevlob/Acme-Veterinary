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

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<jstl:if test="${requestURI == 'item/list.do'}">
	<form:form action="item/search.do" modelAttribute="searchForm">

		<acme:textbox code="item.search" path="keyword" />
		<acme:submit name="search" code="item.search2" />

	</form:form>
</jstl:if>

<display:table pagesize="10" class="displaytag" keepStatus="true"
	name="items" requestURI="${requestURI}" id="row">

	<spring:message code="item.picture" var="pictureHeader" />
	<display:column title="${pictureHeader}">
		<jstl:choose>
			<jstl:when test="${row.picture != null && row.picture != ''}">
				<img src="${row.picture}" alt="${row.name}"
					style="width: 50px; height: 50px;" />
			</jstl:when>
			<jstl:when test="${row.picture == null || row.picture == ''}">
				<spring:message code="item.noImage" />
			</jstl:when>
		</jstl:choose>
	</display:column>

	<spring:message code="item.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="item.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" />

	<spring:message code="item.sku" var="skuHeader" />
	<display:column property="sku" title="${skuHeader}" />

	<spring:message code="item.amount" var="amountHeader" />
	<display:column title="${amountHeader}" sortable="true">
		<fmt:formatNumber type="number" value="${row.price.amount}"
			maxFractionDigits="2" />
	</display:column>

	<spring:message code="item.coin" var="coinHeader" />
	<display:column title="${coinHeader}">
		<jstl:out value="${coin}" />
	</display:column>

	<security:authorize access="hasRole('CUSTOMER')">
		<display:column>
			<a href="shoppingCart/customer/addItem.do?itemId=${row.id}"> <spring:message
					code="item.add" />
			</a>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('ADMIN')">
		<spring:message code="item.isDelete" var="isDeteleHeader" />
		<display:column property="isDeleted" title="${isDeteleHeader}" />

		<display:column>
			<a href="item/administrator/edit.do?itemId=${row.id}"> <spring:message
					code="item.edit" />
			</a>
		</display:column>
	</security:authorize>

</display:table>

<security:authorize access="hasRole('ADMIN')">
	<a href="item/administrator/create.do"> <spring:message
			code="item.create" />
	</a>
</security:authorize>

