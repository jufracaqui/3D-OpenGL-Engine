package entidades;

import org.lwjglx.util.vector.Vector3f;

public class Luz {

	private Vector3f posicion, color;
	
	public Luz(Vector3f posicion, Vector3f color){
		this.posicion = posicion;
		this.color = color;
	}

	public Vector3f getPosicion() {
		return posicion;
	}

	public void setPosicion(Vector3f posicion) {
		this.posicion = posicion;
	}

	public Vector3f getColor() {
		return color;
	}

	public void setColor(Vector3f color) {
		this.color = color;
	}
	
}
