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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<b><spring:message code="actor.name" />:</b>
<jstl:out value="${actor.name}" />
<br />
<b><spring:message code="actor.surname" />:</b>
<jstl:out value="${actor.surname}" />
<br />
<b><spring:message code="actor.phone" />:</b>
<jstl:out value="${actor.phone}" />
<br />
<b><spring:message code="actor.email" />:</b>
<jstl:out value="${actor.email}" />
<br />
<security:authorize access="hasRole('USER')">
	<b><spring:message code="actor.registerSince" />:</b>
	<fmt:formatDate pattern="dd-MM-yyyy HH:mm" value="${customer.registerMoment}" />
	<br />
</security:authorize>
<br />

<div>
	<a href="profile/actor/edit.do"><spring:message
			code="profile.edit" /></a> <a
		href="profile/actor/changePassword.do?actorId=${actor.id}"><spring:message
			code="profile.changePassword" /></a>
</div>

