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

<form:form action="item/administrator/edit.do" modelAttribute="item">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:textbox code="item.name" path="name" />
	
	<acme:textbox code="item.sku" path="sku" />
	
	<acme:textarea code="item.description" path="description" />
	
	<acme:textbox code="item.amount" path="price.amount" />
	
	<acme:select items="${categories}" itemLabel="name" code="item.category" path="category"/>
	
	<acme:textarea code="item.picture" path="picture" />
	
	<acme:submit name="save" code="item.save"/>&nbsp; 
	
	<input type="button" name="cancel"
		value="<spring:message code="item.cancel" />"
		onclick="javascript: relativeRedir('item/administrator/list.do');" />
	<br />
	<jstl:if test="${item.id != 0}">
		<acme:submit name="delete" code="item.delete"/>
	</jstl:if>

</form:form>
