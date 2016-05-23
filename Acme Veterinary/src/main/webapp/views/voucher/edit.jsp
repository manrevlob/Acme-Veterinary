<%--
 * action-2.jsp
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

<security:authorize access="hasRole('ADMIN')">

<form:form action="voucher/administrator/edit.do"
	modelAttribute="voucher">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:textbox code="voucher.code" path="code"/>
	<acme:textbox code="voucher.value" path="value"/>
	<acme:textbox code="voucher.validUntil" path="validUntil" placeholder="dd/MM/yyyy HH:mm"/>
	<acme:textbox code="voucher.minimumOrder" path="minimumOrder"/>

	<acme:submit name="save" code="voucher.save"/>
	<acme:cancel url="voucher/administrator/list.do" code="voucher.cancel"/>
	<jstl:if test="${item.id != 0}">
		<acme:delete name="delete" code="voucher.delete"/>
	</jstl:if>
	
</form:form>

</security:authorize>
