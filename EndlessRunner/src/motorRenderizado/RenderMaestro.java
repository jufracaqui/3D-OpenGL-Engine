package motorRenderizado;

import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjglx.util.vector.Matrix4f;

import entidades.Camara;
import entidades.Entidad;
import entidades.Luz;
import modelos.ModeloConTextura;
import shaders.ShaderEstatico;
import shaders.ShaderTerreno;
import terrenos.Terreno;

public class RenderMaestro {
	
	private static final float FOV = 70;
	private static final float PLANO_CERCANO = 0.1f;
	private static final float PLANO_LEJANO = 1000;
	private static final float RED = 0.5f;
	private static final float GREEN = 0.5f;
	private static final float BLUE = 0.5f;

	private Matrix4f matrizProyeccion;
	private ShaderEstatico shader = new ShaderEstatico();
	private RenderEntidades renderer;
	private RenderTerreno rendererTerreno;
	private ShaderTerreno shaderTerreno = new ShaderTerreno();	
	private Map<ModeloConTextura, List<Entidad>> entidades = new HashMap<ModeloConTextura, List<Entidad>>();
	private List<Terreno> terrenos = new ArrayList<Terreno>();
	
	public RenderMaestro(){
		habilitarCulling();
		createProjectionMatrix();
		renderer = new RenderEntidades(shader, matrizProyeccion);
		rendererTerreno = new RenderTerreno(shaderTerreno, matrizProyeccion);
	}

	private void createProjectionMatrix() {
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		glfwGetWindowSize(ManagerVentana.getVentana(), w, h);
		
		float aspectRatio = (float) w.get(0) / (float) h.get(0);
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV/2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = PLANO_LEJANO - PLANO_CERCANO;
        
        matrizProyeccion = new Matrix4f();
        matrizProyeccion.m00 = x_scale;
        matrizProyeccion.m11 = y_scale;
        matrizProyeccion.m22 = -((PLANO_LEJANO + PLANO_CERCANO) / frustum_length);
        matrizProyeccion.m23 = -1;
        matrizProyeccion.m32 = -((2 * PLANO_CERCANO * PLANO_LEJANO) / frustum_length);
        matrizProyeccion.m33 = 0;		
	}

	//Transparencia
	public static void habilitarCulling() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
	
	public static  void deshabilitarCulling() {
		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	
	public void renderizar(Luz sol, Camara camara){
		preparar();
		shader.iniciar();
		shader.cargarColorCielo(RED, GREEN, BLUE);
		shader.cargarLuz(sol);
		shader.cargarMatrizVision(camara);
		renderer.renderizar(entidades);
		shader.parar();
		
		shaderTerreno.iniciar();
		shaderTerreno.cargarColorCielo(RED, GREEN, BLUE);
		shaderTerreno.cargarLuz(sol);
		shaderTerreno.cargarMatrizVision(camara);
		rendererTerreno.renderizar(terrenos);
		shaderTerreno.parar();
		
		terrenos.clear();
		entidades.clear();
	}

	private void preparar() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(RED, GREEN, BLUE, 1);
	}
	
	public void limpiar(){
		shader.limpiar();
		shaderTerreno.limpiar();
	}
	
	public void procesarTerreno(Terreno terreno){
		terrenos.add(terreno);
	}
	
	//Si ya esta definido, no crea uno nuevo, asigna una al nuevo lote
	public void procesarEntidad(Entidad entidad){
		ModeloConTextura modeloEntidad = entidad.getModelo();
		List<Entidad> lote = entidades.get(modeloEntidad);
		if(lote != null)
			lote.add(entidad);
		else{
			List<Entidad> nuevoLote = new ArrayList<Entidad>();
			nuevoLote.add(entidad);
			entidades.put(modeloEntidad, nuevoLote);
		}
	}
}
