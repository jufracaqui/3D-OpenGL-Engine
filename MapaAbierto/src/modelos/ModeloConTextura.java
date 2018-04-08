package modelos;

import texturas.TexturaModelo;

public class ModeloConTextura {
	
	private ModeloBase modeloBase;
	private TexturaModelo textura;
	
	public ModeloConTextura(ModeloBase modeloBase, TexturaModelo textura){
		this.modeloBase = modeloBase;
		this.textura = textura;
	}

	public ModeloBase getModeloBase() {
		return modeloBase;
	}

	public TexturaModelo getTextura() {
		return textura;
	}
	
}
