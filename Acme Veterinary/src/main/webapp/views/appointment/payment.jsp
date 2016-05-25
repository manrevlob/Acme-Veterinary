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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="payment/veterinary/create.do"
	modelAttribute="payment">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="appointment" />
	<form:hidden path="moment" />
	<form:hidden path="totalCost.currency" />

	<fieldset>
		<legend>
			<spring:message code="customer.creditCard" />
		</legend>

		<acme:textbox code="creditCard.name" path="creditCard.name" />

		<acme:textbox code="creditCard.brand" path="creditCard.brand" />

		<acme:number code="creditCard.number" path="creditCard.number" min="0"
			step="1" />

		<form:label path="creditCard.expirationMonth">
			<spring:message code="creditCard.expirationMonth" />:
						</form:label>

		<form:select path="creditCard.expirationMonth" class="form-control">
			<form:option value="0" label="----" />
			<form:options items="${months}" />
		</form:select>

		<form:label path="creditCard.expirationYear">
			<spring:message code="creditCard.expirationYear" />:
						</form:label>

		<form:select path="creditCard.expirationYear" class="form-control">
			<form:option value="0" label="----" />
			<form:options items="${years}" />
		</form:select>

		<acme:number code="creditCard.CVV" path="creditCard.cvv" min="0"
			step="1" />

	</fieldset>
	<br>
	<acme:number code="payment.totalCost" path="totalCost.amount" min="0.0"
		step="0.1" />

	<br>

	<acme:submit name="save" code="payment.save" />
	<acme:cancel url="appointment/veterinary/list.do" code="payment.cancel" />

</form:form>
