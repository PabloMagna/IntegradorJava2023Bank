package interfazDao;

import java.util.ArrayList;
import entidad.Prestamo;
import entidad.Prestamo.Estado;

public interface IPrestamoDao {
	public ArrayList<Prestamo> ListarTodos();
	public ArrayList<Prestamo> ListarPendientes(String busqueda);
	public ArrayList<Prestamo> ListarPorClienteAprobados(int idCliente);
	public int PedirPrestamo(Prestamo prestamo);
	public int CambiarEstadoPrestamo(int idPrestamo, Estado estado);
	public Prestamo ObtenerPrestamoPorId(int idPrestamo);
	public float PorcentajePrestamosPorCantDeCuotas(int cuotas, int estado);
	public int RechazarPrestamosPorIdClienteEliminado(int idCliente);
	public int RechazarPrestamoPorBajaCuenta(int numeroCuenta);
}
