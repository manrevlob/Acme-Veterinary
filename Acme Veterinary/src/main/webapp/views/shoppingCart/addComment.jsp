<%--
 * addComment.jsp
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

<form:form action="shoppingCart/customer/addComment.do" modelAttribute="shoppingCart">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="customer"/>

	<acme:textbox code="shoppingCart.comment" path="comment"/>

	<acme:submit name="save" code="shoppingCart.save"/>&nbsp; 
	<jstl:if test="${shoppingCart.comment != null}">
		<input type="submit" name="delete"
			value="<spring:message code="shoppingCart.delete" />"
			onclick="return confirm('<spring:message code="shoppingCart.confirm.delete" />')" />&nbsp;
	</jstl:if>
		
	<acme:cancel url="shoppingCart/customer/show.do" code="shoppingCart.cancel"/>

</form:form>
