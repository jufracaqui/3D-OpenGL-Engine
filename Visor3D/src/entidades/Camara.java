package entidades;

import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjglx.util.vector.Vector3f;

import manejadores.ManejadorPosicionRaton;
import manejadores.ManejadorRuletaRaton;
import motorRenderizado.ManagerVentana;

public class Camara {
	
	private static GLFWCursorPosCallback posicionRatonCallBack;
	private static GLFWScrollCallback ruletaRatonCallBack;

	private float distanciaAlJugador = 50;
	private float anguloConJugador = 0;
	
	private Vector3f posicion = new Vector3f(100, 35, 50);
	private float inclinacion = 20;
	private float angulo = 0;
	private float direccion;

	private Jugador jugador;
	
	public Camara(Jugador jugador){
		glfwSetCursorPosCallback(ManagerVentana.getVentana(), posicionRatonCallBack = new ManejadorPosicionRaton());
		glfwSetScrollCallback(ManagerVentana.getVentana(), ruletaRatonCallBack = new ManejadorRuletaRaton());
		this.jugador = jugador;
	}
	
	public void mover(){
		calcularZoom();
		calcularInclinacion();
		calcularAnguloAlJugador();
		float distanciaHorizontal = calcularDistanciaHorizontal();
		float distanciaVertical = calcularDistanciaVertical();
		calcularPosicionCamara(distanciaHorizontal, distanciaVertical);
		this.angulo = 180 - (jugador.getRotY() + anguloConJugador);
	}
	
	public Vector3f getPosicion() {
		return posicion;
	}
	
	public float getInclinacion() {
		return inclinacion;
	}
	
	public float getAngulo() {
		return angulo;
	}
	
	public float getDireccion() {
		return direccion;
	}
	
	public void setPosicion(Vector3f posicion) {
		this.posicion = posicion;
	}
	
	public void setInclinacion(float inclinacion) {
		this.inclinacion = inclinacion;
	}
	
	public void setAngulo(float angulo) {
		this.angulo = angulo;
	}
	
	public void setDireccion(float direccion) {
		this.direccion = direccion;
	}
	
	private void calcularPosicionCamara(float distanciaHorizontal, float distanciaVertical) {
		float theta = jugador.getRotY() + anguloConJugador;
		float compensacionX = (float)(distanciaHorizontal * Math.sin(Math.toRadians(theta)));
		float compensacionZ = (float)(distanciaHorizontal * Math.cos(Math.toRadians(theta)));
		posicion.x = jugador.getPosicion().x - compensacionX;
		posicion.z = jugador.getPosicion().z - compensacionZ;
		posicion.y = jugador.getPosicion().y + distanciaVertical;
	}

	private float calcularDistanciaHorizontal() {
		return (float) (distanciaAlJugador * Math.cos(Math.toRadians(inclinacion)));
	}
	
	private float calcularDistanciaVertical() {
		return (float) (distanciaAlJugador * Math.sin(Math.toRadians(inclinacion)));
	}

	private void calcularZoom(){
		float nivelZoom = (float)(ManejadorRuletaRaton.getDiferenciaY());
		distanciaAlJugador -= nivelZoom;
		ManejadorRuletaRaton.setDiferenciaY(0);
	}

	private void calcularInclinacion() {
		if(glfwGetMouseButton(ManagerVentana.getVentana(), GLFW_MOUSE_BUTTON_LEFT) == 1){
			float cambioInclinacion = (float) ((ManejadorPosicionRaton.getPosY() - 291) * 0.01f);
			inclinacion -= cambioInclinacion;
		}
	}
	
	private void calcularAnguloAlJugador() {
		if(glfwGetMouseButton(ManagerVentana.getVentana(), GLFW_MOUSE_BUTTON_RIGHT) == 1){
			float cambioAngulo = (float) ((ManejadorPosicionRaton.getPosX() - 631) * 0.01f);
			anguloConJugador -= cambioAngulo;
		}
	}

}
