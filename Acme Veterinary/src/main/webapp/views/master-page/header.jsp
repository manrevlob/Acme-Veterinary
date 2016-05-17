<%--
 * header.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>


<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="Acme-Veterinary Co., Inc." />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->

		<security:authorize access="hasRole('ADMIN')">
			<li><a href="register/createVeterinary.do"> <spring:message
						code="master.page.register.veterinary" /></a></li>
			<li><a class="fNiv"><spring:message
						code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="spamword/administrator/list.do"><spring:message
								code="master.page.administrator.spamWord" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message
						code="master.page.manage" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="pet/customer/list.do"><spring:message
								code="master.page.customer.myPet" /></a></li>
					<li><a href="appointment/customer/list.do"><spring:message
						code="master.page.myAppointment" /> </a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('VETERINARY')">
			<li><a class="fNiv"><spring:message
						code="master.page.manage" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="appointment/veterinary/list.do"><spring:message
								code="master.page.veterinary.myAppointment" /></a></li>
				</ul></li>
			<li><a href="customer/veterinary/list.do"> <spring:message
						code="master.page.customer.list" /></a></li>
		</security:authorize>

		<security:authorize access="isAnonymous()">
			<li><a href="clinic/list.do"><spring:message
						code="master.page.clinic" /></a></li>
			<li><a href="register/createCustomer.do"> <spring:message
						code="master.page.register" /></a></li>
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>

		</security:authorize>

		<security:authorize access="isAuthenticated()">
			<li><a href="clinic/list.do"><spring:message
						code="master.page.clinic" /> </a></li>
			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="profile/actor/list.do"><spring:message
								code="master.page.profile.view" /></a></li>
					<li><a href="messageFolder/actor/list.do"><spring:message
								code="master.page.mail" /> </a></li>
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>


				</ul></li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

