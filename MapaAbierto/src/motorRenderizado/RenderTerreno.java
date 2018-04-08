package motorRenderizado;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector3f;

import modelos.ModeloBase;
import shaders.ShaderTerreno;
import terrenos.Terreno;
import texturas.PackTexturasTerreno;
import utiles.Maths;

public class RenderTerreno {
	
	private ShaderTerreno shader;
	
	public RenderTerreno(ShaderTerreno shader, Matrix4f matrizProyectada){
		this.shader = shader;
		shader.iniciar();
		shader.cargarMatrizProyectada(matrizProyectada);
		shader.conectarUnidadesTexturas();
		shader.parar();
	}
	
	public void renderizar(List<Terreno> terrenos){
		for(Terreno terreno:terrenos){
			prepararTerreno(terreno);
			cargarMatrizModelo(terreno);
			
			GL11.glDrawElements(GL11.GL_TRIANGLES, terreno.getModelo().getNumeroVertices(), GL11.GL_UNSIGNED_INT, 0);
			
			desbindearTexturasModelo();
		}
	}

	private void desbindearTexturasModelo() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}

	private void cargarMatrizModelo(Terreno terreno) {
		Matrix4f matrizTransformada = Maths.createTransformationMatrix(new Vector3f(terreno.getX(), 0, terreno.getZ()), 0, 0, 0, 1);
		shader.cargarMatrizTransformada(matrizTransformada);
	}

	private void prepararTerreno(Terreno terreno) {
		ModeloBase modeloBase = terreno.getModelo();
		GL30.glBindVertexArray(modeloBase.getIdVAO());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		bindearTexturas(terreno);
		shader.cargarVariablesBrillo(1, 0);
	}

	private void bindearTexturas(Terreno terreno) {
		PackTexturasTerreno packTexturas = terreno.getPackTexturas();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, packTexturas.getTexturaFondo().getIdTextura());
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, packTexturas.getTexturaR().getIdTextura());
		GL13.glActiveTexture(GL13.GL_TEXTURE2);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, packTexturas.getTexturaG().getIdTextura());
		GL13.glActiveTexture(GL13.GL_TEXTURE3);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, packTexturas.getTexturaB().getIdTextura());
		GL13.glActiveTexture(GL13.GL_TEXTURE4);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, terreno.getMapaDeMezclas().getIdTextura());
	}

}
