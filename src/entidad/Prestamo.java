package entidad;

import java.time.LocalDate;

public class Prestamo {
    private int idPrestamo;
    private Cuenta cuenta;
    private double importePedido;
    private double importePorMes;
    private int cuotas;
    private LocalDate fechaPedido;
    private Estado estado;
    
    public enum Estado{
    	PENDIENTE,
    	APROBADO,
    	RECHAZADO    	
    }
    
	public Prestamo() {}

	public Prestamo(int idPrestamo, Cuenta cuenta, double importePedido, double importePorMes, int cuotas,
			LocalDate fechaPedido, Estado estado) {
		super();
		this.idPrestamo = idPrestamo;
		this.cuenta = cuenta;
		this.importePedido = importePedido;
		this.importePorMes = importePorMes;
		this.cuotas = cuotas;
		this.fechaPedido = fechaPedido;
		this.estado = estado;
	}
	
	

	public int getIdPrestamo() {
		return idPrestamo;
	}

	public void setIdPrestamo(int idPrestamo) {
		this.idPrestamo = idPrestamo;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public double getImportePedido() {
		return importePedido;
	}

	public void setImportePedido(double importePedido) {
		this.importePedido = importePedido;
	}

	public double getImportePorMes() {
		return importePorMes;
	}

	public void setImportePorMes(double importePorMes) {
		this.importePorMes = importePorMes;
	}

	public int getCuotas() {
		return cuotas;
	}

	public void setCuotas(int cuotas) {
		this.cuotas = cuotas;
	}

	public LocalDate getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(LocalDate fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}	
	public void setEstado(int estado) {
	    switch (estado) {
	        case 0:
	            this.estado = Estado.PENDIENTE;
	            break;
	        case 1:
	            this.estado = Estado.APROBADO;
	            break;
	        case 2:
	            this.estado = Estado.RECHAZADO;
	            break;
	    }
	}

	public Estado getEstado() {
		return this.estado;
	}
	
	public String getEstadoAsString() {
        switch (this.estado) {
            case PENDIENTE:
                return "Pendiente";
            case APROBADO:
                return "Aprobado";
            case RECHAZADO:
                return "Rechazado";
            default:
                return "Desconocido";
        }
    }

	@Override
	public String toString() {
		return "Prestamo [idPrestamo=" + idPrestamo + ", cuenta=" + cuenta + ", importePedido=" + importePedido
				+ ", importePorMes=" + importePorMes + ", cuotas=" + cuotas + ", fechaPedido=" + fechaPedido
				+ ", estado=" + estado + "]";
	}
	
	
}
