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

<form:form action="category/administrator/edit.do"
	modelAttribute="category">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<acme:textbox code="category.name" path="name"/>

	<acme:textarea code="category.description" path="description"/>

	<acme:submit name="save" code="category.save"/>
		
	<acme:cancel url="category/administrator/list.do" code="category.cancel"/>
	
	<jstl:if test="${category.id != 0}">
		<input class="btn btn-danger" type="submit" name="delete"
			value="<spring:message code="category.delete" />"
			onclick="return confirm('<spring:message code="category.confirm.delete" />')" />
		
	</jstl:if>

</form:form>
