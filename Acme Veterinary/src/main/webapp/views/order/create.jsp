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
	<spring:message code="order.totalPrice"/>: <jstl:out value="${order.totalPrice.amount}" /> Euros
</div>
<form:form action="voucher/customer/apply.do" modelAttribute="voucherForm">
	<form:hidden path="message" />
	<form:hidden path="customer" />
	<form:hidden path="ticker" />
	<form:hidden path="moment" />
	<form:hidden path="totalPrice.amount" />
	<form:hidden path="totalPrice.currency" />
	<form:hidden path="isCanceled" />
	<form:hidden path="voucher" />

	<acme:textbox code="order.voucher.code" path="code"/>
	<acme:submit name="apply" code="order.voucher.apply"/>
</form:form>
<jstl:if test="${voucherForm.message != null && voucherForm.message != ''}">
	<span style="color: red;"><spring:message code="${voucherForm.message}"/></span>
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

	<form:label path="fullName">
		<spring:message code="order.fullName" />:
	</form:label>
	<form:input path="fullName" />
	<form:errors cssClass="error" path="fullName" />
	<br />

	<form:label path="address">
		<spring:message code="order.address" />:
	</form:label>
	<form:input path="address" />
	<form:errors cssClass="error" path="address" />
	<br />

	<form:label path="creditCard.name">
		<spring:message code="order.ccName" />:
	</form:label>
	<form:input path="creditCard.name" />
	<form:errors cssClass="error" path="creditCard.name" />
	<br />

	<form:label path="creditCard.brand">
		<spring:message code="order.ccBrand" />:
	</form:label>
	<form:input path="creditCard.brand" />
	<form:errors cssClass="error" path="creditCard.brand" />
	<br />

	<form:label path="creditCard.number">
		<spring:message code="order.ccNumber" />:
	</form:label>
	<form:input path="creditCard.number" />
	<form:errors cssClass="error" path="creditCard.number" />
	<br />

	<form:label path="creditCard.expirationMonth">
		<spring:message code="order.ccExpirationMonth" />:
	</form:label>
	<form:input path="creditCard.expirationMonth" /> (mm)
	<form:errors cssClass="error" path="creditCard.expirationMonth" />
	<br />

	<form:label path="creditCard.expirationYear">
		<spring:message code="order.ccExpirationYear" />:
	</form:label>
	<form:input path="creditCard.expirationYear" /> (YYYY)
	<form:errors cssClass="error" path="creditCard.expirationYear" />
	<br />

	<form:label path="creditCard.cvv">
		<spring:message code="order.ccCvv" />:
	</form:label>
	<form:input path="creditCard.cvv" /> (100-999)
	<form:errors cssClass="error" path="creditCard.cvv" />
	<br />

	<acme:submit name="save" code="order.save"/>
	<acme:cancel url="shoppingCart/customer/show.do" code="order.cancel"/>
</form:form>