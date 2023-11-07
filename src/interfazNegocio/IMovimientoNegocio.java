
package interfazNegocio;

import java.util.ArrayList;

import entidad.Movimiento;

public interface IMovimientoNegocio {
	public ArrayList<Movimiento> ListarPorNumeroCuenta(int numeroCuenta);
	public boolean Agregar(Movimiento movimiento);
}
