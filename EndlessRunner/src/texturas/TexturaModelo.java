package texturas;

public class TexturaModelo {
	
	private int idTextura;	
	private float cantidadBrillo = 1;
	private float reflectividad = 0;
	private boolean tieneTransparencia = false;
	private boolean usaLuzFalsa = false;
	private int numeroDeFilas = 1;
	
	public TexturaModelo(int idTextura){
		this.idTextura = idTextura;
	}

	public float getCantidadBrillo() {
		return cantidadBrillo;
	}

	public void setCantidadBrillo(float cantidadBrillo) {
		this.cantidadBrillo = cantidadBrillo;
	}

	public float getReflectividad() {
		return reflectividad;
	}

	public void setReflectividad(float reflectividad) {
		this.reflectividad = reflectividad;
	}

	public boolean isTieneTransparencia() {
		return tieneTransparencia;
	}

	public void setTieneTransparencia(boolean tieneTransparencia) {
		this.tieneTransparencia = tieneTransparencia;
	}

	public boolean isUsaLuzFalsa() {
		return usaLuzFalsa;
	}

	public void setUsaLuzFalsa(boolean usaLuzFalsa) {
		this.usaLuzFalsa = usaLuzFalsa;
	}

	public int getNumeroDeFilas() {
		return numeroDeFilas;
	}

	public void setNumeroDeFilas(int numeroDeFilas) {
		this.numeroDeFilas = numeroDeFilas;
	}

	public int getIdTextura() {
		return idTextura;
	}

	
}
