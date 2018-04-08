package motorRenderizado;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;
import org.lwjglx.Sys;

public class ManagerVentana {
	//Representa la ventana
	private static long ventana;
	//Es el tiempo del ultimo frame
	private static long tiempoUltimoFrame;
	//Es el tiempo entre frames
	private static float delta;
	
	public static void crearVentana(int ancho, int alto, String titulo){
		//Creo la ventana
		glfwInit();
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		
		//Pongo los datos en la  ventana
		ventana = glfwCreateWindow(ancho, alto, titulo, MemoryUtil.NULL, MemoryUtil.NULL);
		
		glfwMakeContextCurrent(ventana);
		//Sincronización vertical
		glfwSwapInterval(1);
		//Ejecuto la ventana
		glfwShowWindow(ventana);
		
		GL.createCapabilities();
		//Obtener el tiempo del ultimo frame
		tiempoUltimoFrame = obtenerTiempoActual();
	}
	
	public static void cerrarVentana(){
		//Destruye el proceso de la ventana
		glfwDestroyWindow(ventana);
	}
	
	public static void actualizarVentana(){
		long tiempoFrameActual = obtenerTiempoActual();
		delta = (tiempoFrameActual - tiempoUltimoFrame) / 1000f;
		tiempoUltimoFrame = tiempoFrameActual;
	}
	
	private static long obtenerTiempoActual(){
		//Para calcular el tiempo que transcurre entre frames
		return Sys.getTime() * 1000 / Sys.getTimerResolution();
	}
	
	public static float obtenerTiempoEntreFrames(){
		return delta;
	}
	
	public static long getVentana(){
		return ventana;
	}
}
