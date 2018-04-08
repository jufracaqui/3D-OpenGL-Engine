package entidades;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjglx.util.vector.Vector3f;

import manejadores.ManejadorTeclado;
import modelos.ModeloConTextura;
import motorRenderizado.ManagerVentana;
import terrenos.Terreno;

public class Jugador extends Entidad{
	
	private GLFWKeyCallback keyCallback;

	private float VELOCIDAD = 40;
	private float VELOCIDAD_GIRO = 160;
	private float GRAVEDAD = -50;
	private float FUERZA_SALTO = 30;
	private float ALTURA_TERRENO = 0;
	
	private float velocidadActual = 0;
	private float velocidadActualGiro = 0;
	private float velocidadSalto = 0;
	private boolean isSaltando = false;
	
	public Jugador(ModeloConTextura modelo, Vector3f posicion, float rotX, float rotY, float rotZ, float escala){
		super(modelo, posicion, rotX, rotY, rotZ, escala);
		glfwSetKeyCallback(ManagerVentana.getVentana(), keyCallback = new ManejadorTeclado());
	}
	
	public void saltar(){
		if(!isSaltando){
			velocidadSalto = FUERZA_SALTO;
			isSaltando = true;
		}
	}
	
	public void mover(Terreno terreno){
		comprobarEntradas();
		super.incrementarRotacion(0, velocidadActualGiro * ManagerVentana.obtenerTiempoEntreFrames(), 0);
		float distancia = velocidadActual * ManagerVentana.obtenerTiempoEntreFrames();
		float distanciaX = (float)(distancia * Math.sin(Math.toRadians(super.getRotY())));
		float distanciaZ = (float)(distancia * Math.cos(Math.toRadians(super.getRotY())));
		super.incrementarPosicion(distanciaX, 0, distanciaZ);
		velocidadSalto += GRAVEDAD * ManagerVentana.obtenerTiempoEntreFrames();
		super.incrementarPosicion(0, velocidadSalto * ManagerVentana.obtenerTiempoEntreFrames(), 0);
		float alturaTerreno = terreno.getAlturaTerreno(super.getPosicion().x, super.getPosicion().z);
		if(super.getPosicion().y < alturaTerreno){
			velocidadSalto = 0;
			isSaltando = false;
			super.getPosicion().y = alturaTerreno;
		}
	}

	private void comprobarEntradas(){
		if(ManejadorTeclado.isTeclaPulsada(GLFW_KEY_W)){
			this.velocidadActual = VELOCIDAD;
		}
		else if(ManejadorTeclado.isTeclaPulsada(GLFW_KEY_S)){
			this.velocidadActual = -VELOCIDAD;
		}
		else this.velocidadActual = 0;
		
		if(ManejadorTeclado.isTeclaPulsada(GLFW_KEY_D)){
			this.velocidadActualGiro = -VELOCIDAD_GIRO;
		}
		else if(ManejadorTeclado.isTeclaPulsada(GLFW_KEY_A)){
			this.velocidadActualGiro = VELOCIDAD_GIRO;
		}
		else this.velocidadActualGiro = 0;
		
		if(ManejadorTeclado.isTeclaPulsada(GLFW_KEY_SPACE)){
			saltar();
		}
	}
	
}
