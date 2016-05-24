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



<b><spring:message code="pet.name" />:</b>
<jstl:out value="${pet.name}" />
<br />
<b><spring:message code="pet.birthDate" />:</b>
<fmt:formatDate pattern="dd-MM-yyyy" value="${pet.birthDate}" />
<br />
<b><spring:message code="pet.petType" />:</b>
<jstl:out value="${pet.petType.name}" />
<br />

<img src="${image}" alt="${pet.name}" style="width: 300px; height: 300px;" />

<a href="pet/customer/uploadImage.do?petId=${pet.id}"> <spring:message
		code="pet.uploadImage" />
</a>
<br>
<br>
<acme:cancel url="pet/customer/list.do" code="pet.cancel"/>
