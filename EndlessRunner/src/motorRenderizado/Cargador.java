package motorRenderizado;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import modelos.ModeloBase;

public class Cargador {
	
	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	private List<Integer> texturas = new ArrayList<Integer>();
	
	public ModeloBase guardarEnVAO(float[] posiciones, float[] coordenadasTextura, float[] normales, int[] indices){
		
		int idVAO = crearVAO();
		bindearBufferDeIndices(indices);
		guardarDatosEnListaDeAtributos(0, 3, posiciones);
		guardarDatosEnListaDeAtributos(1, 2, coordenadasTextura);
		guardarDatosEnListaDeAtributos(2, 3, normales);
		desbindearVAO();
		return new ModeloBase(idVAO, indices.length);
		
	}

	private void desbindearVAO() {
		
		GL30.glBindVertexArray(0);
		
	}

	private void guardarDatosEnListaDeAtributos(int numeroAtributo, int tamañoCoordenadas, float[] datos) {
		
		int idVBO = GL15.glGenBuffers();
		vbos.add(idVBO);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, idVBO);
		
		FloatBuffer buffer = guardarDatosEnBufferFloat(datos);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		//Definimos los valores
		GL20.glVertexAttribPointer(numeroAtributo, tamañoCoordenadas, GL11.GL_FLOAT, false, 0, 0);
		//Liberamos
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
	}
	
	private FloatBuffer guardarDatosEnBufferFloat(float[] datos) {

		FloatBuffer buffer = BufferUtils.createFloatBuffer(datos.length);
		buffer.put(datos);
		buffer.flip();
		return buffer;
		
	}

	private void bindearBufferDeIndices(int[] indices) {

		//Creo el VBO
		int idVBO = GL15.glGenBuffers();
		//Lo añado a la lista
		vbos.add(idVBO);
		//Genero el buffer
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, idVBO);
		
		//Genero el buffer
		IntBuffer buffer = guardarDatosEnBufferInt(indices);
		//Lo guardo
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		
	}

	private IntBuffer guardarDatosEnBufferInt(int[] datos) {

		//Creamos un buffer indicando el tamaño de los datos
		IntBuffer buffer = BufferUtils.createIntBuffer(datos.length);
		//Meter datos en el buffer
		buffer.put(datos);
		//Indica que hemos terminado de escribir en el buffer y se puede leer
		buffer.flip();
		return buffer;
		
	}

	private int crearVAO() {

		//Creo un VAO vacio
		int idVAO = GL30.glGenVertexArrays();
		//Lo añado a la lista
		vaos.add(idVAO);
		//Lo activo para poder trabajar con el
		GL30.glBindVertexArray(idVAO);
		return idVAO;
		
	}
	
	public int cargarTextura(String nombreFichero){
		
		//Creo una textura
		Texture textura = null;
		try {
			//Cargo la imagen png de la textura
			textura = TextureLoader.getTexture("PNG", new FileInputStream("res/texturas/" + nombreFichero + ".png"));
			//Parametros openGL
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -0.4f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		//Obtengo el id de lka textura
		int idTextura = textura.getTextureID();
		//Añado a la lista
		texturas.add(idTextura);
		return idTextura;
		
	}
	
	//Limpio los datos de memoria
	public void limpiar(){
		
		for(int vao:vaos){
			GL30.glDeleteVertexArrays(vao);
		}
		
		for(int vbo:vbos){
			GL15.glDeleteBuffers(vbo);
		}
		
		for(int texture:texturas){
			GL11.glDeleteTextures(texture);
		}
		
	}

}
