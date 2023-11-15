<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="entidad.Cliente"%>
<%@ page import="entidad.Cliente.TipoCliente"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@include file="Layout.jsp"%>

<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<%
    Boolean checkLogin = (Boolean) session.getAttribute("checkLogin");
    if (checkLogin != null && checkLogin && cliente == null) {
%>
<script>
    Swal.fire({
        icon: "error",
        title: "Usuario y/o contraseña incorrectas.",
        showConfirmButton: true,
    });
</script>
<%
    }
    if (checkLogin != null) {
        session.setAttribute("checkLogin", false);
    }
%>


<div class="container">
	<%
		if (cliente != null) {
	%>
	<div class="border rounded p-4 mx-auto mt-3" style="max-width: 600px;">
					<h2 class="card-title mt-5">
						¡Bienvenido
						<%=(cliente.getTipoCliente() == TipoCliente.CLIENTE) ? "Cliente" : "Admin"%>!
					</h2>
					<ul class="list-group list-group-flush">
						<%
							if (cliente.getTipoCliente() == TipoCliente.CLIENTE) {
						%>
						<li class="list-group-item"><a
							href="ServletCuenta?listaPorId=1">Listar Cuentas</a></li>
						<li class="list-group-item"><a
							href="ServletPrestamo?listaPagar=1">Pagar Préstamos</a></li>
						<li class="list-group-item"><a href="DetalleCliente.jsp">Datos
								Personales</a></li>
						<%
							} else if (cliente.getTipoCliente() == TipoCliente.ADMIN) {
						%>
						<li class="list-group-item"><a href="ServletCliente?lista=1">Listar
								Clientes</a></li>
						<li class="list-group-item"><a href="ServletCliente?alta=1">Agregar
								Cliente</a></li>
						<li class="list-group-item"><a href="ServletPrestamo?lista=1">Listar
								Préstamos Admin</a></li>
						<li class="list-group-item"><a href="ServletCuenta?lista=1">Listar
								Cuentas</a></li>
						<li class="list-group-item"><a href="Informe1.jsp">Informe
								1</a></li>
						<li class="list-group-item"><a href="Informe2.jsp">Informe
								2</a></li>
						<%
							}
						%>
					</ul>
				</div>
		<%
			} else {
		%>
		<div class="col-md-6">
			<div class="card">
				<div class="card-body">
					<h2 class="card-title">Ingresar</h2>
					<form id="loginForm" action="ServletCliente" method="POST">
						<div class="form-group">
							<label for="username">Usuario:</label> <input type="text"
								class="form-control" id="username" name="username" required>
						</div>
						<div class="form-group">
							<label for="password">Contraseña:</label> <input type="password"
								class="form-control" id="password" name="password" required>
						</div>
						<button type="submit" class="btn btn-primary" name="btnLogin">Iniciar
							Sesión</button>
					</form>
					<p id="message" class="mt-3"></p>
				</div>
			</div>
		</div>
		<%
			}
		%>
	</div>