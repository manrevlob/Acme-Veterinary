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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="customer/veterinary/search.do"
	modelAttribute="searchForm">
	<acme:textbox code="customer.search" path="keyword"/>
	<acme:submit name="search" code="customer.search2"/>
		
</form:form>

<display:table pagesize="10" class="displaytag" keepStatus="true"
	name="customers" requestURI="${requestURI}" id="row">

	<spring:message code="customer.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="customer.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" />

	<spring:message code="customer.phone" var="phoneHeader" />
	<display:column property="phone" title="${phoneHeader}" />

	<spring:message code="customer.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" />
	
	<display:column>
		<a href="customer/veterinary/listPet.do?customerId=${row.id}"> <spring:message
				code="customer.pets" />
		</a>
	</display:column>
</display:table>
