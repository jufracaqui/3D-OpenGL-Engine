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
		
		ManagerVentana.crearVentana(1280, 720, "EndlessRunner");		
		Cargador cargador = new Cargador();
		
		Luz luz = new Luz(new Vector3f(100, 500, -50), new Vector3f(1, 1, 1));

		ModeloBase modeloBase = CargadorOBJ.cargarModeloOBJ("dragon", cargador);
		ModeloConTextura modelo = new ModeloConTextura(modeloBase, new TexturaModelo(cargador.cargarTextura("blanco")));
		
		Jugador jugador = new Jugador(modelo, new Vector3f(100, 0, -50), 0, 0, 0, 1);
		Camara camara = new Camara(jugador);
		RenderMaestro renderer = new RenderMaestro();
		
		TexturaTerreno texturaFondo = new TexturaTerreno(cargador.cargarTextura("grass"));
		TexturaTerreno texturaR = new TexturaTerreno(cargador.cargarTextura("dirt"));
		TexturaTerreno texturaG = new TexturaTerreno(cargador.cargarTextura("grassFlowers"));
		TexturaTerreno texturaB = new TexturaTerreno(cargador.cargarTextura("path"));
		
		PackTexturasTerreno packTexturas = new PackTexturasTerreno(texturaFondo, texturaR, texturaG, texturaB);
		TexturaTerreno mapaMezclas = new TexturaTerreno(cargador.cargarTextura("mapaDeMezclas"));
		
		Terreno terreno1 = new Terreno(0, -1, cargador, packTexturas, mapaMezclas, "mapaDeAlturas");
		Terreno terreno2 = new Terreno(0, 0, cargador, packTexturas, mapaMezclas, "mapaDeAlturas");
		Terreno terreno3 = new Terreno(0, 1, cargador, packTexturas, mapaMezclas, "mapaDeAlturas");
		Terreno terreno4 = new Terreno(0, 2, cargador, packTexturas, mapaMezclas, "mapaDeAlturas");	
		Terreno terreno5 = new Terreno(0, -2, cargador, packTexturas, mapaMezclas, "mapaDeAlturas");	
				
		while(glfwWindowShouldClose(ManagerVentana.getVentana()) == GL_FALSE){
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glfwPollEvents();
			
			if(jugador.getPosicion().z < 800){
				jugador.mover(terreno2);
			}
			else if (jugador.getPosicion().z < 1600){
				jugador.mover(terreno3);
			}
			else jugador.mover(terreno4);
			
			if(jugador.getPosicion().z < -50 || jugador.getPosicion().z > 2400){
				jugador.setPosicion(new Vector3f(100, 0, -50));
			}
			
			camara.mover();
			
			renderer.procesarEntidad(jugador);
			renderer.renderizar(luz, camara);
			
			renderer.procesarTerreno(terreno1);
			renderer.procesarTerreno(terreno2);
			renderer.procesarTerreno(terreno3);
			renderer.procesarTerreno(terreno4);
			
			ManagerVentana.actualizarVentana();
			
			glFlush();
			glfwSwapBuffers(ManagerVentana.getVentana());
		}
		
		renderer.limpiar();
		cargador.limpiar();
		ManagerVentana.cerrarVentana();
	}
	
}
