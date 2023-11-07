package interfazDao;

import java.util.ArrayList;

import entidad.Movimiento;

public interface IMovimientoDao {
	public ArrayList<Movimiento> ListarPorNumeroCuenta(int numeroCuenta);
	public int Agregar(Movimiento movimiento);
	ArrayList<Movimiento> ListarTodosMovimientos();
}
