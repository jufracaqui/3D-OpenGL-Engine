package modelos;

public class ModeloBase {
	
	//VAO donde se va a guardary vertices que tiene el modelo
	private int idVAO, numeroVertices;
	
	public ModeloBase(int idVAO, int numeroVertices){
		this.idVAO = idVAO;
		this.numeroVertices = numeroVertices;
	}

	//Getters
	public int getIdVAO() {
		return idVAO;
	}

	public int getNumeroVertices() {
		return numeroVertices;
	}

}
