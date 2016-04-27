<%--
 * index.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<b><spring:message code="lopd.identity" /></b>
<p>
Acme Corporation
Universidad de sevilla</br>
Diseño y pruebas</br>
Grupo 30
</p>
<b><spring:message code="lopd.general" /></b>
<p>
<spring:message code="lopd.generalText" />
</p>
<b><spring:message code="lopd.lopd" /></b>
<p>
<spring:message code="lopd.lopdText" />
</p>
<b><spring:message code="lopd.confidentiality" /></b>
<p>
<spring:message code="lopd.confidentialityText" />
</p>