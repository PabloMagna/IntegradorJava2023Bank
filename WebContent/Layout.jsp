<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="entidad.Cliente"%>
<%@ page import="entidad.Cliente.TipoCliente"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Group3 Bank</title>
<link rel="stylesheet" type="text/css" href="css/Layout.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://code.iconify.design/iconify-icon/1.0.7/iconify-icon.min.js"></script>
</head>
<body>
	<%		
		HttpSession ses = request.getSession(false); 
		Cliente cliente = (Cliente) ses.getAttribute("cliente");
		if (cliente != null) {
	%>
	<div id="titulo">
		<h1 class="h1-layout">
			<%
				if (cliente.getTipoCliente() == TipoCliente.ADMIN) {
			%>
			Área Administración
			<%
				} else {
			%>
			Área clientes
			<%
				}
			%>
		</h1>
	</div>
	<div id="menu">
		<nav class="navbar navbar-expand-lg navbar-light bg-light"> <a id="homeLink" 
			class="navbar-brand" href="Inicio.jsp"><iconify-icon icon="fa:home"></iconify-icon> Group3 Bank</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarNav" aria-controls="navbarNav"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav mr-auto nav-tabs">
				<!-- Mover elementos a la izquierda -->
				<%
					if (cliente.getTipoCliente() == TipoCliente.CLIENTE) {
				%>
				<li class="nav-item li-layout"><a class="nav-link"
					href="ServletCuenta?listaPorId=1">Listar Cuentas</a></li>
				<li class="nav-item li-layout"><a class="nav-link"
					href="ServletPrestamo?listaPagar=1">Pagar Préstamos</a></li>
				<li class="nav-item li-layout"><a class="nav-link"
					href="DetalleCliente.jsp">Datos Personales</a></li>
				<%
					} else if (cliente.getTipoCliente() == TipoCliente.ADMIN) {
				%>
				<li class="nav-item li-layout"><a class="nav-link"
					href="ServletCliente?lista=1">Listar Clientes</a></li>
				<li class="nav-item li-layout"><a class="nav-link"
					href="ServletCliente?alta=1">Agregar Cliente</a></li>
				<li class="nav-item li-layout"><a class="nav-link"
					href="ServletPrestamo?lista=1">Listar Préstamos Admin</a></li>
				<li class="nav-item li-layout"><a class="nav-link"
					href="ServletCuenta?lista=1">Listar Cuentas</a></li>
				<li class="nav-item li-layout"><a class="nav-link" 
					href="ServletCuenta?informe1=1">Reporte 1</a></li>
				<li class="nav-item li-layout"><a class="nav-link" 
					href="ServletPrestamo?informe2=1.jsp">Reporte 2</a></li>
				<%
					}
				%>
			</ul>
			<ul style="" class="navbar-nav ml-auto">
				<!-- Mover elementos a la derecha -->
				<li id="li-usuario" class="nav-item"><label style="font-weight: bold;" class="nav-link"><%=cliente.getUsuario()%></label>
				</li>
				<li class="nav-item">
					<form id="logoutForm" action="ServletCliente" method="POST">
						<button type="submit" id="exit" class="btn btn-link" id="exitIcon" name="btnCerrarSesion"><iconify-icon icon="mdi:exit-run"></iconify-icon> Cerrar
							Sesión</button>
					</form>
				</li>
			</ul>

		</div>
		</nav>
	</div>
	<%
		} else {
	%>
	<div id="titulo">
		<h1 class="h1-layout">Inicio</h1>
	</div>
	<%
		}
	%>
	<div id="contenido">
		<!-- Espacio para mostrar el contenido particular de cada JSP -->
	</div>
</body>
</html>