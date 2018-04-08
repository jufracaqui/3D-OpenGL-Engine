package shaders;

import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;

import entidades.Camara;
import entidades.Luz;
import utiles.Maths;

public class ShaderEstatico extends Shader{

	private static final String ARCHIVO_VERTEX = "src/shaders/vertexShader.txt";
	private static final String ARCHIVO_FRAGMENT = "src/shaders/fragmentShader.txt";
	
	private int posicion_matrizTransformada, posicion_matrizProyeccion, posicion_matrizVision,
	posicion_posicionLuz, posicion_colorLuz, posicion_cantidadBrillo, posicion_reflectividad,
	posicion_usaLuzFalsa, posicion_colorCielo, posicion_numeroDeFilas, posicion_compensacion;
	
	//Creo el shader
	public ShaderEstatico(){
		super(ARCHIVO_VERTEX, ARCHIVO_FRAGMENT);
	}
	
	@Override
	protected void bindearAtributos() {
		super.bindearAtributos(0, "posicion");
		super.bindearAtributos(1, "coordenadasTextura");
		super.bindearAtributos(2, "normal");
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
		posicion_usaLuzFalsa = super.obtenerPosicionUniforme("usaLuzFalsa");
		posicion_colorCielo = super.obtenerPosicionUniforme("colorCielo");
		posicion_numeroDeFilas = super.obtenerPosicionUniforme("numeroDeFilas");
		posicion_compensacion = super.obtenerPosicionUniforme("compensacion");
	}
	
	public void cargarNumeroFilas(int numeroFilas){
		super.cargarFloat(posicion_numeroDeFilas, numeroFilas);
	}
	
	public void cargarCompensacion(float x, float y){
		super.cargar2DVector(posicion_compensacion, new Vector2f(x, y));
	}
	
	public void cargarColorCielo(float r, float g, float b){
		super.cargarVector(posicion_colorCielo, new Vector3f(r, g, b));
	}
	
	public void cargarVariableLuzFalsa(boolean usaLuzFalsa){
		super.cargarBooleano(posicion_usaLuzFalsa, usaLuzFalsa);
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
		Matrix4f matrixVision = Maths.createViewMatrix(camara);
		super.cargarMatriz(posicion_matrizVision, matrixVision);
	}
	
	public void cargarMatrizProyeccion(Matrix4f proyeccion){
		super.cargarMatriz(posicion_matrizProyeccion, proyeccion);
	}
}
