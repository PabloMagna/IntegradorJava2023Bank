<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="entidad.Cuenta"%>
<%@include file="Layout.jsp"%>
<%
	Cliente clienteUsuario = (Cliente) session.getAttribute("cliente");

	if (clienteUsuario == null || clienteUsuario.getTipoCliente() == Cliente.TipoCliente.ADMIN) {
		response.sendRedirect("ErrorPermiso.jsp");
	}
%>
<%
	Cuenta cuentaPrestamo = (Cuenta) session.getAttribute("cuentaPrestamo");
	if (cuentaPrestamo == null) {
		out.println("La cuenta de préstamo no está disponible.");
	} else {
%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<title>Solicitud de Préstamo</title>
</head>
<body>

	<%
			Boolean alertaExito = (Boolean) session.getAttribute("pedidoExitoso");
			if (alertaExito != null && alertaExito) {
	%>
	<script>
		Swal.fire({
			icon : "success",
			title : "Éxito",
			text : "Préstamo pedido correctamente."
		});
	</script>
	<%
				session.setAttribute("pedidoExitoso", false);
			}
	%>

	<div class="container">
	<div class="border rounded p-4 mx-auto mt-3" style="max-width: 600px;">
		<h1 class="mt-4">Solicitud de Préstamo</h1>
		<p class="mb-4">
			Número de Cuenta:
			<%=cuentaPrestamo.getNumero()%></p>

		<form action="ServletPrestamo" method="post">
			<div class="form-group">
				<label for="importe">Importe:</label> <input type="text"
					name="importe" id="importe" class="form-control" required
					pattern="^\d{1,8}(\.\d{1,2})?$"
					placeholder="ingresá numero ( hasta 6 enteros y 2 decimales)" >
			</div>

			<div class="form-group">
				<label for="cuotas">Cuotas:</label> <select name="cuotas"
					class="form-control" required>
					<option value="3">3</option>
					<option value="6">6</option>
					<option value="12">12</option>
					<option value="24">24</option>
				</select>
			</div>

			<button type="submit" name="btnPedirPrestamo" class="btn btn-success">Solicitar
				Préstamo</button>
		</form>
		</div>
	</div>
	<div class="d-flex justify-content-center">
		<a class="btn btn-primary mt-3" href="Inicio.jsp">Volver al Inicio</a>
	</div>
</body>
</html>
<%
	}
%>
