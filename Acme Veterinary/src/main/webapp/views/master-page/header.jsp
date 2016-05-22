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

<nav class="navbar navbar-default" role="navigation">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse"
			data-target="#menucete">
			<span class="icon-bar"></span> <span class="icon-bar"></span> <span
				class="icon-bar"></span>
		</button>
	</div>
	<div id="menucete" class="collapse navbar-collapse navbar-ex1-collapse">
		<ul class="nav navbar-nav">
			<!-- Do not forget the "fNiv" class for the first level links !! -->

			<li><a href="item/list.do"><spring:message
						code="master.page.item" /></a></li>

			<security:authorize access="hasRole('ADMIN')">
				<li><a href="register/createVeterinary.do"> <spring:message
							code="master.page.register.veterinary" /></a></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown"> <spring:message
							code="master.page.manage" /> <b class="caret"></b>
				</a>
					<ul class="dropdown-menu">
						<li><a href="spamword/administrator/list.do"><spring:message
									code="master.page.administrator.spamWord" /></a></li>
						<li><a href="item/administrator/list.do"><spring:message
									code="master.page.administrator.item" /></a></li>
						<li><a href="category/administrator/list.do"><spring:message
									code="master.page.administrator.category" /></a></li>
						<li><a href="order/administrator/list.do"><spring:message
									code="master.page.administrator.order" /></a></li>
					</ul></li>
			</security:authorize>

			<security:authorize access="hasRole('CUSTOMER')">
				<li><a href="shoppingCart/customer/show.do"><spring:message
							code="master.page.consumer.shoppingcart" /></a></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown"> <spring:message
							code="master.page.manage" /> <b class="caret"></b>
				</a>

					<ul class="dropdown-menu">

						<li><a href="pet/customer/list.do"><spring:message
									code="master.page.customer.myPet" /></a></li>
						<li><a href="appointment/customer/list.do"><spring:message
									code="master.page.myAppointment" /> </a></li>
						<li><a href="order/customer/list.do"><spring:message
									code="master.page.customer.myOrders" /> </a></li>
					</ul></li>
			</security:authorize>

			<security:authorize access="hasRole('VETERINARY')">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown"> <spring:message
							code="master.page.manage" /> <b class="caret"></b>
				</a>
					<ul class="dropdown-menu">
						<li><a href="appointment/veterinary/list.do"><spring:message
									code="master.page.veterinary.myAppointment" /></a></li>
					</ul></li>
				<li><a href="customer/veterinary/list.do"> <spring:message
							code="master.page.customer.list" /></a></li>
			</security:authorize>

			<security:authorize access="isAnonymous()">
				<li><a href="clinic/list.do"><spring:message
							code="master.page.clinic" /></a></li>
				<li><a href="bulletin/list.do"><spring:message
							code="master.page.bulletin" /></a></li>
				<li><a href="register/createCustomer.do"> <spring:message
							code="master.page.register" /></a></li>
				<li><a class="fNiv" href="security/login.do"><spring:message
							code="master.page.login" /></a></li>

			</security:authorize>

			<security:authorize access="isAuthenticated()">
				<li><a href="clinic/list.do"><spring:message
							code="master.page.clinic" /> </a></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown"> <spring:message
							code="master.page.profile" /> (<security:authentication
							property="principal.username" />) <b class="caret"></b>
				</a>
					<ul class="dropdown-menu">
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
</nav>
<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

