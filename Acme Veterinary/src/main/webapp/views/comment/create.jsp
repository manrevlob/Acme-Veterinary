<%--
 * edit.jsp
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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:if test="${requestURI == 'comment/customer/createToItem.do'}">
	<form:form action="comment/customer/createToItem.do"
		modelAttribute="commentForm">

		<form:hidden path="id" />
		<form:hidden path="comment.customer" />
		<form:hidden path="comment.moment" />

		<acme:textbox code="comment.rating" path="comment.rating"
			placeholder="0 - 5" />

		<acme:textbox code="comment.description" path="comment.description" />

		<input type="submit" name="save"
			value="<spring:message code="comment.save" />" />&nbsp; 
		
	<input type="button" name="cancel"
			value="<spring:message code="comment.cancel" />"
			onclick="javascript:history.back()" />&nbsp; 
	
</form:form>
</jstl:if>

<jstl:if
	test="${requestURI == 'comment/customer/createToVeterinary.do'}">
	<form:form action="comment/customer/createToVeterinary.do"
		modelAttribute="commentForm">

		<form:hidden path="id" />
		<form:hidden path="comment.customer" />
		<form:hidden path="comment.moment" />

		<acme:textbox code="comment.rating" path="comment.rating"
			placeholder="0 - 5" />

		<acme:textbox code="comment.description" path="comment.description" />

		<input type="submit" name="save"
			value="<spring:message code="comment.save" />" />&nbsp; 
		
	<input type="button" name="cancel"
			value="<spring:message code="comment.cancel" />"
			onclick="javascript:history.back()" />&nbsp; 
	
</form:form>
</jstl:if>

<jstl:if test="${requestURI == 'comment/customer/createToBulletin.do'}">
	<form:form action="comment/customer/createToBulletin.do"
		modelAttribute="commentForm">

		<form:hidden path="id" />
		<form:hidden path="comment.customer" />
		<form:hidden path="comment.moment" />

		<acme:textbox code="comment.rating" path="comment.rating"
			placeholder="0 - 5" />

		<acme:textbox code="comment.description" path="comment.description" />

		<input type="submit" name="save"
			value="<spring:message code="comment.save" />" />&nbsp; 
		
	<input type="button" name="cancel"
			value="<spring:message code="comment.cancel" />"
			onclick="javascript:history.back()" />&nbsp; 
	
</form:form>
</jstl:if>