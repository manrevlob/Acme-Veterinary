<%--
 * show.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('CUSTOMER')">

	<jstl:choose>
		<jstl:when test="${shoppingCartLines.size() != 0}">

			<display:table pagesize="10" class="displaytag" keepStatus="true"
				name="shoppingCartLines" requestURI="${requestURI}" id="row">

				<spring:message code="shoppingCartLine.itemName" var="nameHeader" />
				<display:column property="item.name" title="${nameHeader}"
					sortable="true" />

				<spring:message code="shoppingCartLine.quantity"
					var="quantityHeader" />
				<display:column property="quantity" title="${quantityHeader}" />

				<spring:message code="shoppingCartLine.price" var="priceHeader" />
				<display:column title="${priceHeader}" sortable="true">
					<fmt:formatNumber type="number" value="${row.price.amount}"
						maxFractionDigits="2" />
				</display:column>

				<spring:message code="shoppingCartLine.currency"
					var="currencyHeader" />
				<display:column title="${currencyHeader}">
					<jstl:out value="${coin}" />
				</display:column>

				<display:column>
					<a
						href="shoppingCart/customer/more.do?shoppingCartLineId=${row.id}">
						<img src="./images/add.ico" style="width: 20px; height: 20px;">
					</a>
				</display:column>


				<display:column>
					<jstl:if test="${row.quantity >= 2}">

						<a
							href="shoppingCart/customer/less.do?shoppingCartLineId=${row.id}">
							<img src="./images/delete.ico" style="width: 20px; height: 20px;">
						</a>
					</jstl:if>
				</display:column>
				<display:column>
					<a
						href="shoppingCart/customer/delete.do?shoppingCartLineId=${row.id}">
						<spring:message code="shoppingCartLine.delete" />
					</a>
				</display:column>

			</display:table>

			<div>
				<spring:message code="shoppingCartLine.total" />
				&nbsp;&nbsp;&nbsp;
				<fmt:formatNumber type="number" value="${total}"
					maxFractionDigits="2" />
				<p>
					<jstl:if test="${shoppingCart.comment == null}">
						<a href="shoppingCart/customer/addComment.do"> <spring:message
								code="shoppingCart.addComment" />
						</a>
					</jstl:if>
					<jstl:if test="${shoppingCart.comment != null}">
						<spring:message code="shoppingCart.comment" />
						<jstl:out value="${shoppingCart.comment}" />
						<br />

						<a href="shoppingCart/customer/addComment.do"> <spring:message
								code="shoppingCart.modifyComment" />
						</a>

					</jstl:if>
				<p>
					<a href="order/customer/create.do"> <spring:message
							code="shoppingCart.buy" />
					</a>
			</div>
		</jstl:when>
		<jstl:otherwise>
			<spring:message code="shoppingCartLine.noItem" />
		</jstl:otherwise>
	</jstl:choose>

</security:authorize>