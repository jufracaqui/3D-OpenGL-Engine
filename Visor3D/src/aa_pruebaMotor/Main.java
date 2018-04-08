package aa_pruebaMotor;

import motorRenderizado.Cargador;
import motorRenderizado.CargadorOBJ;
import motorRenderizado.ManagerVentana;
import motorRenderizado.RenderMaestro;
import terrenos.Terreno;
import texturas.PackTexturasTerreno;
import texturas.TexturaModelo;
import texturas.TexturaTerreno;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.WindowConstants;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjglx.util.vector.Vector3f;

import entidades.Camara;
import entidades.Entidad;
import entidades.Jugador;
import entidades.Luz;
import manejadores.*;
import modelos.ModeloBase;
import modelos.ModeloConTextura;

public class Main {
	
	private static GLFWCursorPosCallback posicionRatonCallBack;
	private static GLFWScrollCallback ruletaRatonCallBack;

	public static void main(String[] args) {
		
		ManagerVentana.crearVentana(1280, 720, "Visor de Modelos 3D");		
		Cargador cargador = new Cargador();
		
		Luz luz = new Luz(new Vector3f(100, 500, -50), new Vector3f(1, 1, 1));

		ModeloBase modeloBase = CargadorOBJ.cargarModeloOBJ("fern", cargador);
		ModeloConTextura modelo = new ModeloConTextura(modeloBase, new TexturaModelo(cargador.cargarTextura("fern")));
		
		Jugador jugador = new Jugador(modelo, new Vector3f(100, 0, -50), 0, 0, 0, 1);
		Camara camara = new Camara(jugador);
		RenderMaestro renderer = new RenderMaestro();
		
		ControlesCamara controles = new ControlesCamara();
		controles.setVisible(true);
		controles.setLocation(900, 500);
		controles.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
						
		while(glfwWindowShouldClose(ManagerVentana.getVentana()) == GL_FALSE){
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glfwPollEvents();
			
			camara.mover();			
			
			luz.setColor(new Vector3f((float)controles.getColor().getRed() / 255, (float)controles.getColor().getGreen() / 255, (float)controles.getColor().getBlue() / 255));
			
			if(controles.isCbEjeX){
				jugador.incrementarRotacion(1f, 0, 0);
			}
			if(controles.isCbEjeY){
				jugador.incrementarRotacion(0, 1f, 0);
			}
			if(controles.isCbEjeZ){
				jugador.incrementarRotacion(0, 0, 1f);
			}
			
			modelo.getTextura().setReflectividad(controles.getReflectividad());
			jugador.setEscala(controles.getEscala());
			modelo.getTextura().setTieneTransparencia(controles.isTransparencia);
			modelo.getTextura().setUsaLuzFalsa(controles.isLuzFalsa);
			
			renderer.procesarEntidad(jugador);
			renderer.renderizar(luz, camara);
			
			ManagerVentana.actualizarVentana();
			
			glFlush();
			glfwSwapBuffers(ManagerVentana.getVentana());
		}
		
		renderer.limpiar();
		cargador.limpiar();
		ManagerVentana.cerrarVentana();
	}
	
}
