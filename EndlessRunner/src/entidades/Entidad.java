package entidades;

import org.lwjglx.util.vector.Vector3f;

import modelos.ModeloConTextura;

public class Entidad {
	
	private ModeloConTextura modelo;
	private Vector3f posicion;
	private float rotX, rotY, rotZ, escala;
	private int indiceTextura = 0;
	
	public Entidad(ModeloConTextura modelo, Vector3f posicion, float rotX, float rotY, float rotZ, float escala){
		this.modelo = modelo;
		this.posicion = posicion;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.escala = escala;
	}

	public Entidad(ModeloConTextura modelo, int indiceTextura, Vector3f posicion, float rotX, float rotY, float rotZ, float escala){
		this.modelo = modelo;
		this.indiceTextura = indiceTextura;
		this.posicion = posicion;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.escala = escala;
	}
	
	public float getCompensacionXTextura(){
		int columna = indiceTextura % modelo.getTextura().getNumeroDeFilas();
		return (float)columna / (float)modelo.getTextura().getNumeroDeFilas();
	}
	
	public float getCompensacionYTextura(){
		int fila = indiceTextura % modelo.getTextura().getNumeroDeFilas();
		return (float)fila / (float)modelo.getTextura().getNumeroDeFilas();
	}
	
	public void incrementarPosicion(float x, float y, float z){
		posicion.x += x;
		posicion.y += y;
		posicion.z += z;
	}
	
	public void incrementarRotacion(float x, float y, float z){
		rotX += x;
		rotY += y;
		rotZ += z;
	}

	public ModeloConTextura getModelo() {
		return modelo;
	}

	public void setModelo(ModeloConTextura modelo) {
		this.modelo = modelo;
	}

	public Vector3f getPosicion() {
		return posicion;
	}

	public void setPosicion(Vector3f posicion) {
		this.posicion = posicion;
	}

	public float getRotX() {
		return rotX;
	}

	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}

	public float getEscala() {
		return escala;
	}

	public void setEscala(float escala) {
		this.escala = escala;
	}
	
	
}
