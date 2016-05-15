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

<form:form action="history/veterinary/edit.do"
	modelAttribute="historyForm">
	
	<form:hidden path="appointment"/>

	<acme:textbox code="history.diagnosis" path="diagnosis" />
	
	<fieldset>
		<legend>
			<spring:message code="history.treatment" />
		</legend>

		<acme:textbox code="treatment.description" path="treatmentDescription" />

		<acme:textbox code="treatment.startMoment" path="treatmentStartMoment" />

		<acme:textbox code="treatment.endMoment" path="treatmentEndMoment" />
	</fieldset>

	<acme:submit name="save" code="history.save" />

	<input type="button" name="cancel"
		value="<spring:message code="history.cancel" />"
		onclick="javascript: window.location.replace('appointment/veterinary/list.do')" />

</form:form>