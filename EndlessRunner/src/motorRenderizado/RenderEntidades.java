package motorRenderizado;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjglx.util.vector.Matrix4f;

import entidades.Entidad;
import modelos.ModeloBase;
import modelos.ModeloConTextura;
import shaders.ShaderEstatico;
import texturas.TexturaModelo;
import utiles.Maths;

public class RenderEntidades {
	
	public ShaderEstatico shader;
	
	public RenderEntidades(ShaderEstatico shader, Matrix4f matrizProyeccion){
		this.shader = shader;
		shader.iniciar();
		shader.cargarMatrizProyeccion(matrizProyeccion);
		shader.parar();
	}
	
	public void renderizar(Map<ModeloConTextura, List<Entidad>> entidades){
		//Por cada key en el map
		for(ModeloConTextura modelo:entidades.keySet()){
			prepararModeloConTexturas(modelo);
			//Recojo los modelos del map
			List<Entidad> lote = entidades.get(modelo);
			//Por cada entidad en el lote
			for(Entidad entidad:lote){
				prepararInstancia(entidad);
				//Dibujo los triangulos de la entidad, numero de vertices
				GL11.glDrawElements(GL11.GL_TRIANGLES, modelo.getModeloBase().getNumeroVertices(), GL11.GL_UNSIGNED_INT, 0);
			}
			liberarModeloConTexturas();
		}
	}

	private void liberarModeloConTexturas() {
		RenderMaestro.habilitarCulling();
		//Libero memoria
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}

	private void prepararInstancia(Entidad entidad) {
		//Creo una matriz con los datos de la entidad
		Matrix4f matrizTransformada = Maths.createTransformationMatrix(entidad.getPosicion(), entidad.getRotX(), entidad.getRotY(), entidad.getRotZ(), entidad.getEscala());
		//Cargo la matriz al shader
		shader.cargarMatrizTransformada(matrizTransformada);
		//Cargo la compensacion de la textura al shader
		shader.cargarCompensacion(entidad.getCompensacionXTextura(), entidad.getCompensacionYTextura());
	}

	private void prepararModeloConTexturas(ModeloConTextura modelo) {
		ModeloBase modeloBase = modelo.getModeloBase();
		//Bindeo el VAO con los vertices
		GL30.glBindVertexArray(modeloBase.getIdVAO());
		//Recojo lso vertices de cada lista del VAO
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		//Cargo textura
		TexturaModelo textura = modelo.getTextura();
		//Paso al shader el numero de filas
		shader.cargarNumeroFilas(textura.getNumeroDeFilas());
		
		if(textura.isTieneTransparencia())//Si tiene transparencia
			//Deshabilito el culling
			RenderMaestro.deshabilitarCulling();
		
		//Cargo al shader si usa luz falsa, el brillo y la reflectividad
		shader.cargarVariableLuzFalsa(textura.isUsaLuzFalsa());
		shader.cargarVariablesBrillo(textura.getCantidadBrillo(), textura.getReflectividad());
		
		//Inicio la textura 0 para el modelo
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		//Bindeo la textura al modelo
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, modelo.getTextura().getIdTextura());
	}

}
