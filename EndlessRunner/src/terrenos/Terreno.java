package terrenos;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;

import modelos.ModeloBase;
import motorRenderizado.Cargador;
import texturas.PackTexturasTerreno;
import texturas.TexturaTerreno;
import utiles.Maths;

public class Terreno {
	
	private float TAMAÑO = 800;
	private float ALTURA_MAXIMA = 40;
	private float ALTURA_MINIMA = 40;
	private float COLOR_PIXEL_MAXIMO = 256*256*256;
	
	private float x, z;
	private ModeloBase modelo;
	private PackTexturasTerreno packTexturas;
	private TexturaTerreno mapaDeMezclas;
	private float[][] alturas;
	
	public Terreno(int cuadriculaX, int cuadriculaZ, Cargador cargador, PackTexturasTerreno packTexturas, TexturaTerreno mapaDeMezclas, String mapaAlturas){
		this.packTexturas = packTexturas;
		this.mapaDeMezclas = mapaDeMezclas;
		this.x = cuadriculaX * TAMAÑO;
		this.z = cuadriculaZ * TAMAÑO;
		this.modelo = generarTerreno(cargador, mapaAlturas);
	}

	public float getX() {
		return x;
	}

	public float getZ() {
		return z;
	}

	public ModeloBase getModelo() {
		return modelo;
	}

	public PackTexturasTerreno getPackTexturas() {
		return packTexturas;
	}

	public TexturaTerreno getMapaDeMezclas() {
		return mapaDeMezclas;
	}

	public float getAlturaTerreno(float worldX, float worldZ){
		float terrainX = worldX - this.x;
		float terrainZ = worldZ - this.z;
		float gridSquareSize = TAMAÑO / ((float)alturas.length - 1);
		int gridX = (int)Math.floor(terrainX / gridSquareSize);
		int gridZ = (int)Math.floor(terrainZ / gridSquareSize);
		if(gridZ >= alturas.length - 1 || gridZ >= alturas.length -1 || gridX < 0 || gridZ < 0){
			return 0;
		}
		float xCoord = (terrainX % gridSquareSize) / gridSquareSize;
		float zCoord = (terrainZ % gridSquareSize) / gridSquareSize;
		float answer;
		if(xCoord <= (1 - zCoord)){
			answer = Maths.barryCentric(new Vector3f(0, alturas[gridX][gridZ], 0), new Vector3f(1, alturas[gridX + 1][gridZ], 0), new Vector3f(0, alturas[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
		}
		else{
			answer = Maths.barryCentric(new Vector3f(1, alturas[gridX + 1][gridZ], 0), new Vector3f(1, alturas[gridX + 1][gridZ + 1], 1), new Vector3f(0, alturas[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
		}
		return answer;
	}

	private ModeloBase generarTerreno(Cargador loader, String heightMap){
		
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("res/terrenos/" + heightMap + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		int VERTEX_COUNT = image.getHeight();
		alturas = new float[VERTEX_COUNT][VERTEX_COUNT];
		
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count*2];
		int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
		int vertexPointer = 0;
		for(int i=0;i<VERTEX_COUNT;i++){
			for(int j=0;j<VERTEX_COUNT;j++){
				vertices[vertexPointer*3] = (float)j/((float)VERTEX_COUNT - 1) * TAMAÑO;
				float height = getHeight(j, i, image);
				alturas[j][i] = height;
				vertices[vertexPointer*3+1] = height;
				vertices[vertexPointer*3+2] = (float)i/((float)VERTEX_COUNT - 1) * TAMAÑO;
				normals[vertexPointer*3] = 0;
				normals[vertexPointer*3+1] = 1;
				normals[vertexPointer*3+2] = 0;
				textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
				textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for(int gz=0;gz<VERTEX_COUNT-1;gz++){
			for(int gx=0;gx<VERTEX_COUNT-1;gx++){
				int topLeft = (gz*VERTEX_COUNT)+gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		return loader.guardarEnVAO(vertices, textureCoords, normals, indices);
	}
	
	private float getHeight(int x, int y, BufferedImage image){
		float a = 0;
		if(x < 0 || x >= image.getHeight() || z < 0 || z >= image.getHeight()){
			a = 0;
		}
		float height = image.getRGB(x, y);
		height += COLOR_PIXEL_MAXIMO / 2f;
		height /= COLOR_PIXEL_MAXIMO / 2f;
		height *= ALTURA_MAXIMA;
		a = height;
		return a;
	}
}
