<%-- 
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('ADMIN')">
<display:table name="vouchers" pagesize="10" class="displaytag"
	requestURI="${requestURI}" id="row">

	<spring:message code="voucher.code" var="codeHeader" />
	<display:column property="code" title="${codeHeader}" sortable="true" />
	
	<spring:message code="voucher.value" var="valueHeader" />
	<display:column property="value" title="${valueHeader}" sortable="true" />
	
	<spring:message code="voucher.validUntil" var="validUntilHeader" />
	<display:column property="validUntil" title="${validUntilHeader}" sortable="true" />
	
	<spring:message code="voucher.minimumOrder" var="minimumOrderHeader" />
	<display:column property="minimumOrder" title="${minimumOrderHeader}" sortable="true" />

	<display:column title="${veterinariesHeader}">
		<a href="voucher/administrator/edit.do?voucherId=${row.id}"> <spring:message
				code="voucher.edit" />
		</a>
	</display:column>

</display:table>
<br/>
	<a class="btn btn-primary" href="voucher/administrator/create.do"> <spring:message
			code="voucher.create" />
	</a>
</security:authorize>
