package negocio;

import java.util.ArrayList;

import dao.PrestamoDao;
import entidad.Prestamo;
import entidad.Prestamo.Estado;
import interfazNegocio.IPrestamoNegocio;

public class PrestamoNegocio implements IPrestamoNegocio {
	PrestamoDao dao = new PrestamoDao();

	@Override
	public ArrayList<Prestamo> ListarTodos() {
		return dao.ListarTodos();
	}

	@Override
	public ArrayList<Prestamo> ListarPendientes() {
		// TODO Auto-generated method stub
		return dao.ListarPendientes();
	}

	@Override
	public boolean PedirPrestamo(Prestamo prestamo) {
		return dao.PedirPrestamo(prestamo) == 0? false : true;
	}


	@Override
	public boolean CambiarEstadoPrestamo(int idPrestamo, Estado estado) {
		return dao.CambiarEstadoPrestamo(idPrestamo, estado) == 0 ? false : true;
	}

	@Override
	public Prestamo ObtenerPrestamoPorId(int idPrestamo) {
		return dao.ObtenerPrestamoPorId(idPrestamo);
	}

	@Override
	public ArrayList<Prestamo> ListarPorClienteAprobados(int idCliente) {
		return dao.ListarPorClienteAprobados(idCliente);
	}
	
}
