<%--
 * message.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<b><spring:message code="clinic.name" />:</b>
<jstl:out value="${clinic.name}" />
<br />

<b><spring:message code="clinic.address" />:</b>
<jstl:out value="${clinic.address}" />
<br />

<b><spring:message code="clinic.zipCode" />:</b>
<jstl:out value="${clinic.zipCode}" />
<br />

<b><spring:message code="clinic.pictures" />:</b>
<jstl:choose>
	<jstl:when test="${row.pictures == null}">
		<spring:message code="clinic.noImage" />
	</jstl:when>
	<jstl:when test="${row.pictures != null}">
		<jstl:out value="${clinic.pictures}" />
	</jstl:when>
</jstl:choose>


<br />

<br />

<input type="button" name="cancel"
	value="<spring:message code="clinic.cancel" />"
	onclick="javascript: window.location.replace('clinic/list.do')" />
<br />