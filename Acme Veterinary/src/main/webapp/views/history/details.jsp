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

<b><spring:message code="history.moment" />:</b>
<fmt:formatDate pattern="dd-MM-yyyy HH:mm" value="${history.moment}" />
<br />

<b><spring:message code="history.diagnosis" />:</b>
<jstl:out value="${history.diagnosis}" />
<br /><br />

<jstl:if test="${history.treatment != null}">
	<fieldset>
		<legend>
			<b><spring:message code="history.treatment" /></b>
		</legend>
	
		<b><spring:message code="treatment.description" />:</b>
		<jstl:out value="${history.treatment.description}" />
		<br /> 
		
		<b><spring:message code="treatment.startMoment" />:</b>
		<fmt:formatDate pattern="dd-MM-yyyy" value="${history.treatment.startMoment}" />
		<br /> 
		
		<b><spring:message code="treatment.endMoment" />:</b>
		<fmt:formatDate pattern="dd-MM-yyyy" value="${history.treatment.endMoment}" />
		<br /> 
		
	</fieldset>
	<br />
</jstl:if>

<input type="button" name="cancel"
	value="<spring:message code="history.cancel" />"
	onclick="javascript:history.back();" />
<br />