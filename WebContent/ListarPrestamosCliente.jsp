<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.*" %>
<%@include file="Layout.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Préstamos</title>
</head>
<body>
    <h1>Lista de Préstamos</h1>

    <form action="ServletPrestamo" method="post">
        <table border="1">
            <tr>
                <th>ID Préstamo</th>
                <th>Seleccionar Cuota</th>
                <th>Importe</th>
                <th>Seleccionar Cuenta</th> <!-- Agregado -->
                <th>Pagar</th>
            </tr>

          
            <tr>
                <td>2</td>
                <td>
                    <select name="cuota">
                        <option value="">Seleccionar Cuota</option>
                        
                        <option value="3">3</option>
                        
                    </select>
                </td>
                <td><input id="importe" name="importe" value=2300 readonly></td>
                <td> 3
                    <select name="cuenta">
                        <option value="">Seleccionar Cuenta</option>
                        
                        <option value="2">21</option>
                        
                    </select>
                </td>
                <td>
                    <button type="submit" name="btnPagar" value="2">Pagar</button>
                </td>
            </tr>
           
           
           
        </table>
    </form>
</body>
</html>>