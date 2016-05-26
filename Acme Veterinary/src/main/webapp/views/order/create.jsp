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

<div style="font-size: x-large;">
	<spring:message code="order.totalPrice" />
	:
	<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${order.totalPrice.amount}" />
	
	Euros
</div>
<form:form action="voucher/customer/apply.do"
	modelAttribute="voucherForm">
	<form:hidden path="message" />
	<form:hidden path="customer" />
	<form:hidden path="ticker" />
	<form:hidden path="moment" />
	<form:hidden path="totalPrice.amount" />
	<form:hidden path="totalPrice.currency" />
	<form:hidden path="isCanceled" />
	<form:hidden path="voucher" />

	<acme:textbox code="order.voucher.code" path="code" />
	<acme:submit name="apply" code="order.voucher.apply" />
</form:form>
<jstl:if
	test="${voucherForm.message != null && voucherForm.message != ''}">
	<span style="color: red;"><spring:message
			code="${voucherForm.message}" /></span>
</jstl:if>

<form:form action="order/customer/create.do" modelAttribute="order">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="customer" />
	<form:hidden path="ticker" />
	<form:hidden path="comment" />
	<form:hidden path="moment" />
	<form:hidden path="totalPrice.amount" />
	<form:hidden path="totalPrice.currency" />
	<form:hidden path="isCanceled" />
	<form:hidden path="voucher" />



	<acme:textbox code="order.fullName" path="fullName" />

	<acme:textbox code="order.address" path="address" />

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

	<acme:submit name="save" code="order.save" />
	<acme:cancel url="shoppingCart/customer/show.do" code="order.cancel" />
</form:form>