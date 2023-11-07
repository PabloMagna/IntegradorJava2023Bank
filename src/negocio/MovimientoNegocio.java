package negocio;

import java.util.ArrayList;

import dao.MovimientoDao;
import entidad.Movimiento;
import interfazNegocio.IMovimientoNegocio;

public class MovimientoNegocio implements IMovimientoNegocio {
	MovimientoDao dao = new MovimientoDao();

	@Override
	public ArrayList<Movimiento> ListarPorNumeroCuenta(int numeroCuenta) {
		return dao.ListarPorNumeroCuenta(numeroCuenta);
	}

	@Override
	public boolean Agregar(Movimiento movimiento) {
		return dao.Agregar(movimiento) == 0 ? false :true;
	}
	

}
