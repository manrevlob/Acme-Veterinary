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

<form:form action="clinic/administrator/edit.do"
	modelAttribute="clinic">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:label path="name">
		<spring:message code="clinic.name" />:
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />

	<form:label path="address">
		<spring:message code="clinic.address" />:
	</form:label>
	<form:input path="address" />
	<form:errors cssClass="error" path="address" />
	<br />
	
	<form:label path="zipCode">
		<spring:message code="clinic.zipCode" />:
	</form:label>
	<form:input path="zipCode" />
	<form:errors cssClass="error" path="zipCode" />
	<br />

	<form:label path="pictures">
		<spring:message code="clinic.pictures" />:
	</form:label>
	<form:input path="pictures" />
	<form:errors cssClass="error" path="pictures" /><spring:message code="clinic.addPictures" />
	<br />

	<input type="submit" name="save"
		value="<spring:message code="clinic.save" />" />&nbsp; 
		
	<input type="button" name="cancel"
		value="<spring:message code="clinic.cancel" />"
		onclick="javascript: window.location.replace('clinic/list.do')" />&nbsp; 
	
	<jstl:if test="${clinic.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="clinic.delete" />"
			onclick="return confirm('<spring:message code="clinic.confirm.delete" />')" />
	</jstl:if>

</form:form>
