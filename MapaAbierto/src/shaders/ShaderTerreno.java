package shaders;

import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector3f;

import entidades.Camara;
import entidades.Luz;
import utiles.Maths;

public class ShaderTerreno extends Shader{
	
	private static final String ARCHIVO_VERTEX = "src/shaders/vertexShaderTerreno.txt";
	private static final String ARCHIVO_FRAGMENT = "src/shaders/fragmentShaderTerreno.txt";
	
	private int posicion_matrizTransformada, posicion_matrizProyeccion, posicion_matrizVision,
	posicion_posicionLuz, posicion_colorLuz, posicion_cantidadBrillo, posicion_reflectividad,
	posicion_colorCielo, posicion_texturaFondo, posicion_texturaR, posicion_texturaG, posicion_texturaB,
	posicion_mapaFundido;
	
	public ShaderTerreno() {
		super(ARCHIVO_VERTEX, ARCHIVO_FRAGMENT);
	}

	@Override
	protected void obtenerPosicionesUniformes() {
		posicion_matrizTransformada = super.obtenerPosicionUniforme("matrizTransformada");
		posicion_matrizProyeccion = super.obtenerPosicionUniforme("matrizProyeccion");
		posicion_matrizVision = super.obtenerPosicionUniforme("matrizVision");
		posicion_posicionLuz = super.obtenerPosicionUniforme("posicionLuz");
		posicion_colorLuz = super.obtenerPosicionUniforme("colorLuz");
		posicion_cantidadBrillo = super.obtenerPosicionUniforme("cantidadBrillo");
		posicion_reflectividad = super.obtenerPosicionUniforme("reflectividad");
		posicion_colorCielo = super.obtenerPosicionUniforme("colorCielo");
		posicion_texturaFondo = super.obtenerPosicionUniforme("texturaFondo");
		posicion_texturaR = super.obtenerPosicionUniforme("texturaR");
		posicion_texturaG = super.obtenerPosicionUniforme("texturaG");
		posicion_texturaB = super.obtenerPosicionUniforme("texturaB");
		posicion_mapaFundido = super.obtenerPosicionUniforme("mapaFundido");
	}

	@Override
	protected void bindearAtributos() {
		super.bindearAtributos(0, "posicion");
		super.bindearAtributos(1, "coordenadasTextura");
		super.bindearAtributos(2, "normal");
	}
	
	public void conectarUnidadesTexturas(){
		super.cargarInt(posicion_texturaFondo, 0);
		super.cargarInt(posicion_texturaR, 1);
		super.cargarInt(posicion_texturaG, 2);
		super.cargarInt(posicion_texturaB, 3);
		super.cargarInt(posicion_mapaFundido, 4);
	}
	
	public void cargarColorCielo(float r, float g, float b){
		super.cargarVector(posicion_colorCielo, new Vector3f(r, g, b));
	}
	
	public void cargarVariablesBrillo(float cantidadBrillo, float reflectividad){
		super.cargarFloat(posicion_cantidadBrillo, cantidadBrillo);
		super.cargarFloat(posicion_reflectividad, reflectividad);
	}
	
	public void cargarMatrizTransformada(Matrix4f matriz){
		super.cargarMatriz(posicion_matrizTransformada, matriz);
	}
	
	public void cargarLuz(Luz luz){
		super.cargarVector(posicion_posicionLuz, luz.getPosicion());
		super.cargarVector(posicion_colorLuz, luz.getColor());
	}
	
	public void cargarMatrizVision(Camara camara){
		Matrix4f matrizVision = Maths.createViewMatrix(camara);
		super.cargarMatriz(posicion_matrizVision, matrizVision);
	}
	
	public void cargarMatrizProyectada(Matrix4f proyeccion){
		super.cargarMatriz(posicion_matrizProyeccion, proyeccion);
	}
	
}
