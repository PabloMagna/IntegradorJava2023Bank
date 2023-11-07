package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.mysql.cj.xdevapi.Statement;

import entidad.Movimiento;
import entidad.TipoMovimiento;
import interfazDao.IMovimientoDao;

public class MovimientoDao implements IMovimientoDao {
	 private Connection conexion;

	    public MovimientoDao() {
	        conexion = Conexion.obtenerConexion();
	    }

	    @Override
	    public ArrayList<Movimiento> ListarPorNumeroCuenta(int numeroCuenta) {
	        ArrayList<Movimiento> listaMovimientos = new ArrayList<>();

	        try {
	            // Crea la consulta SQL para obtener los movimientos de la cuenta específica, ordenados por ID descendente
	            String query = "SELECT m.idMovimiento, m.detalle, m.importe, m.idTipoMovimiento, t.descripcion, m.fecha FROM movimiento m " +
	                           "INNER JOIN tiposmovimiento t ON m.idTipoMovimiento = t.idTipoMovimiento " +
	                           "WHERE m.numeroCuenta = ? ORDER BY m.idMovimiento DESC";

	            // Crea una instancia de PreparedStatement con la consulta
	            PreparedStatement preparedStatement = conexion.prepareStatement(query);
	            preparedStatement.setInt(1, numeroCuenta); // Asigna el valor del número de cuenta

	            // Ejecuta la consulta
	            ResultSet resultSet = preparedStatement.executeQuery();

	            // Procesa los resultados
	            while (resultSet.next()) {
	                int idMovimiento = resultSet.getInt("idMovimiento");
	                String detalle = resultSet.getString("detalle");
	                double importe = resultSet.getDouble("importe");
	                int idTipoMovimiento = resultSet.getInt("idTipoMovimiento");
	                String tipoMovimientoDescripcion = resultSet.getString("descripcion");
	                LocalDate fecha = resultSet.getDate("fecha").toLocalDate();

	                // Crea una instancia de TipoMovimiento
	                TipoMovimiento tipoMovimiento = new TipoMovimiento();
	                tipoMovimiento.setIdTipoMovimiento(idTipoMovimiento);
	                tipoMovimiento.setDescripcion(tipoMovimientoDescripcion);

	                // Crea una instancia de Movimiento
	                Movimiento movimiento = new Movimiento();
	                movimiento.setIdMovimiento(idMovimiento);
	                movimiento.setNumeroCuenta(numeroCuenta);
	                movimiento.setDetalle(detalle);
	                movimiento.setImporte(importe);
	                movimiento.setIdTipoMovimiento(tipoMovimiento);
	                movimiento.setFecha(fecha);
	                
	                // Agrega el movimiento a la lista
	                listaMovimientos.add(movimiento);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return listaMovimientos;
	    }

	    @Override
	    public int Agregar(Movimiento movimiento) {
	        try {
	            // Define la consulta SQL para insertar un nuevo movimiento
	            String query = "INSERT INTO movimiento (numeroCuenta, detalle, importe, idTipoMovimiento, fecha) VALUES (?, ?, ?, ?, ?)";

	            // Crea una instancia de PreparedStatement con la consulta
	            PreparedStatement preparedStatement = conexion.prepareStatement(query);

	            // Establece los valores de los parámetros
	            preparedStatement.setInt(1, movimiento.getNumeroCuenta());
	            preparedStatement.setString(2, movimiento.getDetalle());
	            preparedStatement.setDouble(3, movimiento.getImporte());
	            preparedStatement.setInt(4, movimiento.getIdTipoMovimiento().getIdTipoMovimiento());
	            
	            // Verifica si la fecha es nula y, si lo es, establece la fecha actual
	            LocalDate fecha = movimiento.getFecha();
	            if (fecha == null) {
	                fecha = LocalDate.now();
	            }
	            preparedStatement.setDate(5, Date.valueOf(fecha));

	            // Ejecuta la consulta para insertar el nuevo movimiento
	            int filasInsertadas = preparedStatement.executeUpdate();

	            // Cierra el recurso
	            preparedStatement.close();

	            return filasInsertadas;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return 0; // Retorna un valor negativo en caso de error
	        }
	    }
	    @Override
	    public ArrayList<Movimiento> ListarTodosMovimientos() {
	        ArrayList<Movimiento> listaMovimientos = new ArrayList<>();

	        try {
	            // Crea la consulta SQL para obtener todos los movimientos, ordenados por ID de movimiento en orden descendente
	            String query = "SELECT m.idMovimiento, m.numeroCuenta, m.detalle, m.importe, m.idTipoMovimiento, t.descripcion, m.fecha FROM movimiento m " +
	                           "INNER JOIN tiposmovimiento t ON m.idTipoMovimiento = t.idTipoMovimiento ORDER BY m.idMovimiento DESC";

	            // Crea una instancia de Statement para ejecutar la consulta
	            PreparedStatement preparedStatement = conexion.prepareStatement(query);

	            // Ejecuta la consulta
	            ResultSet resultSet = preparedStatement.executeQuery(query);

	            // Procesa los resultados
	            while (resultSet.next()) {
	                int idMovimiento = resultSet.getInt("idMovimiento");
	                int numeroCuenta = resultSet.getInt("numeroCuenta");
	                String detalle = resultSet.getString("detalle");
	                double importe = resultSet.getDouble("importe");
	                int idTipoMovimiento = resultSet.getInt("idTipoMovimiento");
	                String tipoMovimientoDescripcion = resultSet.getString("descripcion");
	                LocalDate fecha = resultSet.getDate("fecha").toLocalDate();

	                // Crea una instancia de TipoMovimiento
	                TipoMovimiento tipoMovimiento = new TipoMovimiento();
	                tipoMovimiento.setIdTipoMovimiento(idTipoMovimiento);
	                tipoMovimiento.setDescripcion(tipoMovimientoDescripcion);

	                // Crea una instancia de Movimiento
	                Movimiento movimiento = new Movimiento();
	                movimiento.setIdMovimiento(idMovimiento);
	                movimiento.setNumeroCuenta(numeroCuenta);
	                movimiento.setDetalle(detalle);
	                movimiento.setImporte(importe);
	                movimiento.setIdTipoMovimiento(tipoMovimiento);
	                movimiento.setFecha(fecha);

	                // Agrega el movimiento a la lista
	                listaMovimientos.add(movimiento);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return listaMovimientos;
	    }



}
