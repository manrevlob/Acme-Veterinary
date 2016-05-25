<%--
 * dashboard.jsp
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

<h2>
	<spring:message code="dashboard.lastMonth" />
</h2>

<h3>
	<spring:message code="dashboard.numberNewCustomers" />
	:
	<jstl:out value="${numberNewCustomers}" />
</h3>

<h3>
	<spring:message code="dashboard.vetMoreBusy" />
</h3>

<display:table pagesize="10" class="displaytag" keepStatus="true"
	name="vetMoreBusy" requestURI="${requestURI}" uid="row">

	<spring:message code="veterinary.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="veterinary.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}"
		sortable="true" />

</display:table>

<h3>
	<spring:message code="dashboard.vetLessBusy" />
</h3>
<display:table pagesize="10" class="displaytag" keepStatus="true"
	name="vetLessBusy" requestURI="${requestURI}" uid="row">

	<spring:message code="veterinary.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="veterinary.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}"
		sortable="true" />

</display:table>

<h3>
	<spring:message code="dashboard.customerMorePay" />
</h3>

<display:table pagesize="10" class="displaytag" keepStatus="true"
	name="customerMorePay" requestURI="${requestURI}" uid="row">

	<spring:message code="customer.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="customer.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}"
		sortable="true" />

</display:table>

<h2>
	<spring:message code="dashboard.allTime" />
</h2>

<h3>
	<spring:message code="dashboard.numberCustomers" />
	:
	<jstl:out value="${numberCustomers}" />
</h3>

<h3>
	<spring:message code="dashboard.appointMoreUse" />
</h3>
<!--<display:table pagesize="10" class="displaytag" keepStatus="true"
	name="appointMoreUse" requestURI="${requestURI}" uid="row">

	<spring:message code="veterinary.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="veterinary.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}"
		sortable="true" />

</display:table>-->

<h3>
	<spring:message code="dashboard.clinicWithNumAppoint" />
</h3>
<display:table pagesize="10" class="displaytag" keepStatus="true"
	name="clinicWithNumAppoint" requestURI="${requestURI}" uid="row">

	<spring:message code="clinic.name" var="nameHeader" />
	<display:column value="${row[0].name}" title="${nameHeader}"
		sortable="true" />

	<spring:message code="dashboard.count" var="countHeader" />
	<display:column value="${row[1]}" title="${countHeader}"
		sortable="true" />

</display:table>

<h3>
	<spring:message code="dashboard.mostDemandedItem" />
</h3>
<display:table pagesize="10" class="displaytag" keepStatus="true"
	name="mostDemandedItem" requestURI="${requestURI}" uid="row">

	<spring:message code="item.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}"
		sortable="true" />

	<spring:message code="item.description" var="nameHeader" />
	<display:column property="description" title="${nameHeader}"
		sortable="true" />
	
</display:table>

<h3>
	<spring:message code="dashboard.avgBulletinsByClinic" />
</h3>
<display:table pagesize="10" class="displaytag" keepStatus="true"
	name="avgBulletinsByClinic" requestURI="${requestURI}" uid="row">

	<spring:message code="clinic.name" var="nameHeader" />
	<display:column value="${row[0].name}" title="${nameHeader}"
		sortable="true" />

	<spring:message code="dashboard.avg" var="countHeader" />
	<display:column value="${row[1]}" title="${countHeader}"
		sortable="true" />

</display:table>

