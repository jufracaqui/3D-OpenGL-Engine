package manejadores;

import org.lwjgl.glfw.GLFWScrollCallback;

public class ManejadorRuletaRaton extends GLFWScrollCallback{
	//Variable que representa si la ruleta sube o baja
	private static double diferenciaY;

	@Override
	public void invoke(long window, double xoffset, double yoffset) {
		//Igualamos al valor
		this.diferenciaY = yoffset;		
	}

	//Getter y setters de la posicion de la ruleta
	public static double getDiferenciaY() {
		return diferenciaY;
	}

	public static void setDiferenciaY(double diferenciaY) {
		ManejadorRuletaRaton.diferenciaY = diferenciaY;
	}
}
