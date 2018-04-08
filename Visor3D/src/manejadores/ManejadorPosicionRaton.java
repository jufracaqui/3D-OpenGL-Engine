package manejadores;

import org.lwjgl.glfw.GLFWCursorPosCallback;

public class ManejadorPosicionRaton extends GLFWCursorPosCallback{
	//Variables que representan la posicion del raton en los dos ejes
	private static double posX, posY;
	
	@Override
	public void invoke(long window, double xpos, double ypos) {
		//Igualamos las variable
		this.posX = xpos;
		this.posY = ypos;
	}

	//Getters con las posiciones del raton
	public static double getPosX(){
		return posX;
	}
	
	public static double getPosY(){
		return posY;
	}
}
