package motorRenderizado;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;

import modelos.ModeloBase;

public class CargadorOBJ {
	
	public static ModeloBase cargarModeloOBJ(String fichero, Cargador cargador){
		FileReader fr = null;
		try {
			fr = new FileReader(new File("res/modelos/" + fichero + ".obj"));
		} catch (FileNotFoundException e) {
			System.out.println("No se ha podido leer el fichero.");
		}
		
		//Para leer linea a linea
		BufferedReader lector = new BufferedReader(fr);
		String linea;
		//Listas para cada tip ode dato
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List<Vector2f> texturas = new ArrayList<Vector2f>();
		List<Vector3f> normales = new ArrayList<Vector3f>();
		List<Integer> indices = new ArrayList<Integer>();
		
		float[] arrayVertices = null;
		float[] arrayNormales = null;
		float[] arrayTexturas = null;
		int[] arrayIndices = null;
		try{
			while(true){
				linea = lector.readLine();
				String[] lineaActual = linea.split(" ");
				if(linea.startsWith("v ")){//Es un vertice
					Vector3f vertice = new Vector3f(Float.parseFloat(lineaActual[1]), Float.parseFloat(lineaActual[2]), Float.parseFloat(lineaActual[3]));
					vertices.add(vertice);
				}
				else if(linea.startsWith("vt ")){//Es una textura
					Vector2f textura = new Vector2f(Float.parseFloat(lineaActual[1]), Float.parseFloat(lineaActual[2]));
					texturas.add(textura);
				}
				else if(linea.startsWith("vn ")){//Es un normal
					Vector3f normal = new Vector3f(Float.parseFloat(lineaActual[1]), Float.parseFloat(lineaActual[2]), Float.parseFloat(lineaActual[3]));
					normales.add(normal);				
				}
				else if(linea.startsWith("f ")){//Es una posicion
					arrayTexturas = new float[vertices.size() * 2];
					arrayNormales = new float[vertices.size() * 3];
					break;
				}
			}
			while(linea != null){
				if(!linea.startsWith("f ")){
					linea = lector.readLine();
					continue;
				}
				String[] lineaActual = linea.split(" ");
				String[] vertice1 = lineaActual[1].split("/");
				String[] vertice2 = lineaActual[2].split("/");
				String[] vertice3 = lineaActual[3].split("/");
				
				procesarVertice(vertice1, indices, texturas, normales, arrayTexturas, arrayNormales);
				procesarVertice(vertice2, indices, texturas, normales, arrayTexturas, arrayNormales);
				procesarVertice(vertice3, indices, texturas, normales, arrayTexturas, arrayNormales);
				linea = lector.readLine();
			}
			lector.close();
		}
		catch (Exception e){	
		}
		
		arrayVertices = new float[vertices.size() * 3];
		arrayIndices = new int[indices.size()];
		
		int punteroVertices = 0;
		for(Vector3f vertice:vertices){
			arrayVertices[punteroVertices++] = vertice.x;
			arrayVertices[punteroVertices++] = vertice.y;
			arrayVertices[punteroVertices++] = vertice.z;
		}
		for(int i = 0; i < indices.size(); i++){
			arrayIndices[i] = indices.get(i);
		}
		return cargador.guardarEnVAO(arrayVertices, arrayTexturas, arrayNormales, arrayIndices);
	}

	private static void procesarVertice(String[] datosVertice, List<Integer> indices, List<Vector2f> texturas, List<Vector3f> normales, float[] arrayTexturas, float[] arrayNormales) {
		int punteroVerticesActual = Integer.parseInt(datosVertice[0]) - 1;
		indices.add(punteroVerticesActual);
		Vector2f texturaActual = texturas.get(Integer.parseInt(datosVertice[1]) - 1);
		arrayTexturas[punteroVerticesActual * 2] = texturaActual.x;
		arrayTexturas[punteroVerticesActual * 2 + 1] = 1 - texturaActual.y;
		Vector3f normalActual = normales.get(Integer.parseInt(datosVertice[2]) - 1);
		arrayNormales[punteroVerticesActual * 3] = normalActual.x;
		arrayNormales[punteroVerticesActual * 3 + 1] = normalActual.y;
		arrayNormales[punteroVerticesActual * 3 + 2] = normalActual.z;
	}

}
