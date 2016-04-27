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


<p><spring:message code="cookies.policyText" /></p>
<h2><b><spring:message code="cookies.wathCookies" /></b></h2>
<p><spring:message code="cookies.wathCookiesText" /></p>
<h2><b><spring:message code="cookies.tipeCookes" /></b></h2>
<b><spring:message code="cookies.necessary" /></b>
<p><spring:message code="cookies.necessaryText" /></p>
<b><spring:message code="cookies.analysis" /></b>
<p><spring:message code="cookies.analysisText" /></p>
<b><spring:message code="cookies.functional" /></b>
<p><spring:message code="cookies.functionalText" /></p>
<b><spring:message code="cookies.advertising" /></b>
<p><spring:message code="cookies.advertisingText" /></p>
<h2><b><spring:message code="cookies.manage" /></b></h2>
<p><spring:message code="cookies.manageText" /></p>
<b><spring:message code="cookies.disableChrome" /></b>
<p><a href="https://support.google.com/chrome/answer/95647?hl=es">https://support.google.com/chrome/answer/95647?hl=es</a></p>
<b><spring:message code="cookies.disableFirefox" /></b>
<p><a href="http://support.mozilla.org/es/kb/impedir-que-los-sitios-web-guarden-sus-preferencia">http://support.mozilla.org/es/kb/impedir-que-los-sitios-web-guarden-sus-preferencia</a></p>
<b><spring:message code="cookies.disableInternetExplorer" /></b>
<p><a href="http://windows.microsoft.com/es-es/windows-vista/block-or-allow-cookies">http://windows.microsoft.com/es-es/windows-vista/block-or-allow-cookies</a></p>