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
		
		ManagerVentana.crearVentana(1280, 720, "Mapa Abierto");		
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
		
		Terreno terreno = new Terreno(0, -1, cargador, packTexturas, mapaMezclas, "heightmap");
		
		ModeloBase arbolBase = CargadorOBJ.cargarModeloOBJ("tree", cargador);
		ModeloConTextura arbol = new ModeloConTextura(arbolBase, new TexturaModelo(cargador.cargarTextura("tree")));
		
		ModeloBase helechoBase = CargadorOBJ.cargarModeloOBJ("fern", cargador);
		ModeloConTextura helecho = new ModeloConTextura(helechoBase, new TexturaModelo(cargador.cargarTextura("fern")));
		
		helecho.getTextura().setTieneTransparencia(true);
		helecho.getTextura().setUsaLuzFalsa(true);
		
		List<Entidad> entidades = new ArrayList<Entidad>();
		Random random = new Random(676452);
		for(int i = 0; i < 400; i++){
			if(i % 20 == 0){
				float x = random.nextFloat() * 800 - 400;
				float z = random.nextFloat() * 800 - 400;
				float y = terreno.getAlturaTerreno(x, z);
				entidades.add(new Entidad(arbol, new Vector3f(x, y, z), 0, 0, 0, 7));
			}
			if(i % 5 == 0){
				float x = random.nextFloat() * 800 - 400;
				float z = random.nextFloat() * -600;
				float y = terreno.getAlturaTerreno(x, z);
				entidades.add(new Entidad(helecho, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, 0.9f));
			}
		}
				
		while(glfwWindowShouldClose(ManagerVentana.getVentana()) == GL_FALSE){
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glfwPollEvents();
			
			jugador.mover(terreno);
			camara.mover();
			
			for(Entidad entidad:entidades){
				renderer.procesarEntidad(entidad);
			}
			
			renderer.procesarEntidad(jugador);
			renderer.renderizar(luz, camara);
			
			renderer.procesarTerreno(terreno);
			
			ManagerVentana.actualizarVentana();
			
			glFlush();
			glfwSwapBuffers(ManagerVentana.getVentana());
		}
		
		renderer.limpiar();
		cargador.limpiar();
		ManagerVentana.cerrarVentana();
	}
	
}
