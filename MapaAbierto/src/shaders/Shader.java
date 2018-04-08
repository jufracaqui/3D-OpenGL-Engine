package shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;

public abstract class Shader {

	private int idPrograma, idVertexShader, idFragmentShader;
	private static FloatBuffer bufferMatrices = BufferUtils.createFloatBuffer(16);
	
	public Shader(String archivoVertex, String archivoFragment){
		//Leo los archivos y los compilo
		idVertexShader = cargarShader(archivoVertex, GL20.GL_VERTEX_SHADER);
		idFragmentShader = cargarShader(archivoFragment, GL20.GL_FRAGMENT_SHADER);
		idPrograma = GL20.glCreateProgram();
		GL20.glAttachShader(idPrograma, idVertexShader);
		GL20.glAttachShader(idPrograma, idFragmentShader);
		bindearAtributos();
		GL20.glLinkProgram(idPrograma);
		GL20.glValidateProgram(idPrograma);
		obtenerPosicionesUniformes();
	}

	protected abstract void obtenerPosicionesUniformes();
	
	protected int obtenerPosicionUniforme(String nombreUniforme){
		return GL20.glGetUniformLocation(idPrograma, nombreUniforme);
	}

	protected abstract void bindearAtributos();
	
	protected void bindearAtributos(int atributo, String nombreVariable){
		GL20.glBindAttribLocation(idPrograma, atributo, nombreVariable);
	}
	
	public void iniciar(){
		//Arranco el programa
		GL20.glUseProgram(idPrograma);
	}
	
	public void parar(){
		//Cargo un prorgma vacio
		GL20.glUseProgram(0);
	}
	
	public void limpiar(){
		//Libero shaders en el programa
		GL20.glDetachShader(idPrograma, idVertexShader);
		GL20.glDetachShader(idPrograma, idFragmentShader);
		//Borro shaders
		GL20.glDeleteShader(idVertexShader);
		GL20.glDeleteShader(idFragmentShader);
		//Borro programa
		GL20.glDeleteProgram(idPrograma);
	}
	
	private int cargarShader(String archivo, int tipoShader) {
		//Creo un string builder que se pasara a opengl para que lo compile
		StringBuilder codigoShader = new StringBuilder();
		try{
			//Abro el archivo
			BufferedReader lector = new BufferedReader(new FileReader(archivo));
			String linea;
			//Leo hasta que la linea este vacia
			while((linea = lector.readLine()) != null)
				//Añado al codigo del shader y meto un sdalto de linea
				codigoShader.append(linea).append("\n");
			//Cierro el lector
			lector.close();
		}
		catch (IOException e){
			//Doy error
			System.err.println("No se ha podido leer el fichero");
			//Cierro
			System.exit(-1);
		}
		
		//Creo el shader y lo compilo
		int idShader = GL20.glCreateShader(tipoShader);
		GL20.glShaderSource(idShader, codigoShader);
		GL20.glCompileShader(idShader);
		
		//Compruebo si se ha compilado correctamente
		if(GL20.glGetShaderi(idShader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE){
			//Recojo el error del shader
			System.out.println(GL20.glGetShaderInfoLog(idShader, 500));
			//Saco mensaje
			System.err.println("No se ha podido compilar el shader");
			//Cierro
			System.exit(-1);
		}
		
		return idShader;
	}
	
	//Para cada tipo de variable en los shaders hace falta un metodo para carfar
	//cada tipo 
	protected void cargarMatriz(int posicion, Matrix4f matriz){
		//Guardo
		matriz.store(bufferMatrices);
		//Lista para leer
		bufferMatrices.flip();
		//Posicion, no es transpuesta, buffer de matrices
		GL20.glUniformMatrix4fv(posicion, false, bufferMatrices);
	}
	
	protected void cargarFloat(int posicion, float valor){
		GL20.glUniform1f(posicion, valor);
	}
	
	protected void cargarInt(int posicion, int valor){
		GL20.glUniform1i(posicion, valor);
	}
	
	protected void cargarVector(int posicion, Vector3f vector){
		GL20.glUniform3f(posicion, vector.x, vector.y, vector.z);
	}
	
	protected void cargar2DVector(int posicion, Vector2f vector){
		GL20.glUniform2f(posicion, vector.x, vector.y);
	}
	
	protected void cargarBooleano(int posicion, boolean valor){
		float verdadero = 0;
		if(valor)
			verdadero = 1;
		GL20.glUniformMatrix4fv(posicion, false, bufferMatrices);
	}
}
