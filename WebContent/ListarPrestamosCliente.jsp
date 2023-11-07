<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="entidad.Cuota"%>
<%@ page import="entidad.Cuenta"%>
<%@ page import="java.util.*"%>
<%@include file="Layout.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lista de Préstamos</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.js"></script>
<script>
	$(document).ready(function() {
		$('#prestamosTable').DataTable();
	});
</script>
</head>
<body>
	<h1>Lista de Préstamos</h1>

	<form action="ServletPrestamo" method="post">
		<table border="1" id="prestamosTable" class="display">
			<thead>
				<tr>
					<th>ID Préstamo</th>
					<th>Seleccionar Cuota</th>
					<th>Importe</th>
					<th>Seleccionar Cuenta</th>
					<th>Pagar</th>
				</tr>
			</thead>
			<tbody>
				<%
					ArrayList<Cuota> listaCuotas = (ArrayList<Cuota>) request.getAttribute("listaCuotas");
					ArrayList<Cuenta> listaCuentas = (ArrayList<Cuenta>) request.getAttribute("listaCuentas");

					if (listaCuotas != null) {
						Set<Integer> idPrestamosUnicos = new HashSet<>();
						for (Cuota cuota : listaCuotas) {
							idPrestamosUnicos.add(cuota.getIdPrestamo());
						}

						for (Integer idPrestamo : idPrestamosUnicos) {
							ArrayList<Cuota> cuotasDelPrestamo = new ArrayList<Cuota>();
							for (Cuota cuota : listaCuotas) {
								if (cuota.getIdPrestamo() == idPrestamo) {
									cuotasDelPrestamo.add(cuota);
								}
							}
				%>
				<tr>
					<td><%=idPrestamo%></td>
					<td><select name="cuota" class="form-select">
							<option value="">Seleccionar Cuota</option>
							<%
								for (Cuota cuota : cuotasDelPrestamo) {
							%>
							<option value="<%=cuota.getNumeroCuota()%>"><%=cuota.getNumeroCuota()%></option>
							<%
								}
							%>
					</select></td>

					<td><input id="importe" name="importe"
						value=<%=cuotasDelPrestamo.get(0).getImporte()%> readonly></td>
					<td><select name="cuenta" class="form-select">
							<option value="">Seleccionar Cuenta</option>
							<%
								for (Cuenta cuenta : listaCuentas) {
							%>
							<option value="<%=cuenta.getNumero()%>"><%=cuenta.getNumero()%></option>
							<%
								}
							%>
					</select></td>
					<td>
						<button type="submit" class="btn btn-success" name="btnPagar"
							value="<%=idPrestamo%>">
							<i class="bi bi-cash"></i> Pagar
						</button>
					</td>
				</tr>
				<%
					}
					} else {
				%>
				<tr>
					<td colspan="5">No hay préstamos para mostrar.</td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>
	</form>
</body>
</html>
