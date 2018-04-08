package manejadores;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.*;

//Extiende del controlador de puslacion de teclas
public class ManejadorTeclado extends GLFWKeyCallback{
	//Array de booleanos en el que meteremos las teclas pulsadas
	private static boolean[] teclas = new boolean[65536];

	@Override
	public void invoke(long ventana, int tecla, int scancode, int action, int mods) {
		//Añadimos las teclas que no se esten soltando
		teclas[tecla] = action != GLFW_RELEASE;
	}
	
	public static boolean isTeclaPulsada(int codigoTecla) {
		//Devolvemos un booleano que representa si la tecla con el
		//codigo que digamos esta o no pulsada
		return teclas[codigoTecla];		
	}
}
