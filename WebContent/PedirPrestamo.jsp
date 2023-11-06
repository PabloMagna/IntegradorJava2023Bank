<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@include file="Layout.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Solicitud de Préstamo</title>
</head>
<body>
    <h1>Solicitud de Préstamo</h1>
    <p>Número de Cuenta: x</p>
    <form action="ServletPrestamo" method="post">
        <label for="importe">Importe:</label>
        <input type="text" name="importe" required>
        <br>
        <label for="cuotas">Cuotas:</label>
        <input type="text" name="cuotas" required>
        <br>
        <input type="submit" name="btnPedirPrestamo" value="Solicitar Préstamo">
    </form>
</body>
</html>