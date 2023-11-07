<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entidad.Movimiento" %>
<%@include file="Layout.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.css">
    <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.js"></script>
    <script>
        $(document).ready(function() {
            $('#movimientosTable').DataTable({
                "paging": true,
                "lengthMenu": [5, 10, 25, 50, 100],
                "pageLength": 10,
                "searching": true,
                "ordering": true,
                "language": {
                    "emptyTable": "No hay datos disponibles en la tabla"
                }
            });
        });
    </script>
    <title>Historial de Movimientos</title>
</head>
<body>
    <h1>Historial de Movimientos</h1>

    <%
    // Recupera la lista de movimientos desde el atributo "movimientos"
    ArrayList<Movimiento> movimientos = (ArrayList<Movimiento>) request.getAttribute("movimientos");
    %>

    <table id="movimientosTable" class="display" border="1">
        <thead>
            <tr>
                <th>ID Movimiento</th>
                <th>Detalle</th>
                <th>Importe</th>
                <th>Tipo de Movimiento</th>
                <th>Fecha</th>
            </tr>
        </thead>
        <tbody>
            <%
            if (movimientos != null && !movimientos.isEmpty()) { 
                for (Movimiento movimiento : movimientos) {
            %>
            <tr>
                <td><%= movimiento.getIdMovimiento() %></td>
                <td><%= movimiento.getDetalle() %></td>
                <td><%= movimiento.getImporte() %></td>
                <td><%= movimiento.getIdTipoMovimiento().getDescripcion() %></td>
                <td><%= movimiento.getFecha() %></td>
            </tr>
            <%
                }
            }
            %>
        </tbody>
    </table>
</body>
</html>
