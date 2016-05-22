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

<form:form action="spamword/administrator/edit.do"
	modelAttribute="spamWord">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<acme:textbox code="spamWord.keyWord" path="keyWord"/>

	<acme:submit name="save" code="spamWord.save"/>
	<jstl:if test="${spamWord.id != 0}">
		<input class="btn btn-danger" type="submit" name="delete"
			value="<spring:message code="spamWord.delete" />"
			onclick="return confirm('<spring:message code="spamWord.delete.confirm" />')" />&nbsp;
		</jstl:if>
	<input class="btn" type="button" name="cancel"
		value="<spring:message code="spamWord.cancel" />"
		onclick="javascript: window.location.replace('spamword/administrator/list.do')" />
	<br />

</form:form>
