<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="Layout.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Préstamos</title>
</head>
<body>
    <h1>Lista de Préstamos</h1>
    <table border="1">
        <tr>
            <th>ID Prestamo</th>
            <th>Número de Cuenta</th>
            <th>ID Cliente</th>
            <th>Importe Pedido</th>
            <th>Importe por Mes</th>
            <th>Cuotas</th>
            <th>Fecha Pedido</th>
            <th>Estado</th>
            <th>Aprobar</th>
        </tr>

        <tr>
            <td>1</td>
            <td>12</td>
            <td>1</td>
            <td>100.00</td>
            <td>11.00</td>
            <td>10</td>
            <td>2023-10-27</td>
            <td>PENDIENTE</td>
            <td>
                <form action="ServletPrestamo" method="post">
                    <input type="hidden" name="idPrestamo" name="idPrestamo" value="1">
                    <button type="submit" name="btnAprobar" value="1">Aprobar</button>
                    <button type="submit" name="btnRechazar" value="1">Rechazar</button>
                </form>
            </td>
        </tr>
    </table>
    <br>
</body>
</html>
