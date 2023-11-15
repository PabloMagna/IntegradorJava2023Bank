<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="entidad.Cuenta"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="servlets.ServletCuenta"%>
<%@include file="Layout.jsp"%>
<%
	Cliente clienteUsuario = (Cliente) session.getAttribute("cliente");

	if (clienteUsuario == null || clienteUsuario.getTipoCliente() == Cliente.TipoCliente.ADMIN) {
		response.sendRedirect("ErrorPermiso.jsp");
	}
%>
<%
	Cuenta cuentaOrigen = (Cuenta) request.getSession().getAttribute("cuentaOrigen");
	if (cuentaOrigen == null) {
		response.sendRedirect("error.jsp");
	}

	String errorMensaje = (String) request.getAttribute("errorMensaje");
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<title>Transferencia</title>
</head>
<body>
	<div class="container">
		<h1 class="mt-4">Realizar Transferencia</h1>

		<%
			if (errorMensaje != null) {
				String iconType = (errorMensaje.equals("Transferencia exitosa.")) ? "success" : "error";
		%>
		<script>
        Swal.fire({
            icon: "<%=iconType%>",
            title: "Mensaje",
            text: "<%=errorMensaje%>",
				showConfirmButton : false,
				timer : 3000
			});
		</script>
		<%
			}
		%>




		<form action="ServletCuenta" method="post" class="mt-4">
			<div class="form-group">
				<div class="form-group">
					<label for="importe">Importe:</label> <input type="text"
						name="importe" id="importe" class="form-control" required
						pattern="^\d{1,8}(\.\d{1,2})?$" placeholder="ingresá numero ( hasta 8 enteros y 2 decimales)">
				</div>

				<div class="form-group">
					<label for="cbu">CBU (22 números):</label> <input type="text"
						name="cbu" id="cbu" class="form-control" pattern="[0-9]{22}"
						required>
				</div>
				<div class="form-group">
					<label for="origen">Cuenta de Origen:</label> <input type="text"
						class="form-control" value="<%=cuentaOrigen.getNumero()%>"
						readonly> <input type="hidden" name="cuentaOrigen"
						value="<%=cuentaOrigen.getNumero()%>">
				</div>
				<button type="submit" name="btnTransferir" class="btn btn-primary">Realizar
					Transferencia</button>
			</div>
		</form>
	</div>

	<a class="btn btn-primary" href="Inicio.jsp">Volver al Inicio</a>
</body>
</html>
