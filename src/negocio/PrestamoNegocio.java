package negocio;

import java.util.ArrayList;

import dao.PrestamoDao;
import entidad.Movimiento;
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
	public ArrayList<Prestamo> ListarPendientes(String busqueda) {
		// TODO Auto-generated method stub
		return dao.ListarPendientes(busqueda);
	}

	@Override
	public boolean PedirPrestamo(Prestamo prestamo) {
		return dao.PedirPrestamo(prestamo) == 0 ? false : true;
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

	@Override
	public boolean RechazarPrestamoPorBajaCuenta(int numeroCuenta) {
		return dao.RechazarPrestamoPorBajaCuenta(numeroCuenta) == 0 ? false : true;
	}

	public ArrayList<Prestamo> filtrarLista(ArrayList<Prestamo> listaOriginal, String tipoFiltro, double saldoFiltro,
			int estadoFiltro) {
		ArrayList<Prestamo> listaFiltrada = new ArrayList<>();

		if (saldoFiltro == 0.0) {
			return	listaFiltroEstado(listaOriginal, estadoFiltro);		
		}

		for (Prestamo prestamo : listaOriginal) {
			boolean cumpleCondición = false;
			double saldo = prestamo.getImportePedido();

			if (tipoFiltro.equals("mayor")) {
				cumpleCondición = (saldo > saldoFiltro);
			} else if (tipoFiltro.equals("menor")) {
				cumpleCondición = (saldo < saldoFiltro);
			} else if (tipoFiltro.equals("igual")) {
				cumpleCondición = (saldo == saldoFiltro);
			}

			if (cumpleCondición) {
				listaFiltrada.add(prestamo);
			}
		}

		return listaFiltroEstado(listaFiltrada, estadoFiltro);
	}
	private ArrayList<Prestamo> listaFiltroEstado(ArrayList<Prestamo> listaOriginal, int estadoFiltro) {
		ArrayList<Prestamo> listaTocada = new ArrayList<Prestamo>();
		
		if (listaOriginal == null) 
			return null;
		if(estadoFiltro == 3)
			return listaOriginal;
		for (Prestamo prestamo : listaOriginal) {
			if(prestamo.getEstado().ordinal() == estadoFiltro) {
				listaTocada.add(prestamo);
			}
		}
		return listaTocada;
	}


	@Override
	public boolean RechazarPrestamosPorIdClienteEliminado(int idCliente) {
		return dao.RechazarPrestamosPorIdClienteEliminado(idCliente) == 0 ? false : true;
	}

	@Override
	public float PorcentajePrestamosPorCantDeCuotas(int cuotas, int estado) {

		return dao.PorcentajePrestamosPorCantDeCuotas(cuotas, estado);
	}

}