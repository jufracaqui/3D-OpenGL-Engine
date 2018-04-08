package texturas;

public class PackTexturasTerreno {
	
	private TexturaTerreno texturaFondo, texturaR, texturaG, texturaB;
	
	public PackTexturasTerreno(TexturaTerreno texturaFondo, TexturaTerreno texturaR, TexturaTerreno texturaG, TexturaTerreno texturaB){
		this.texturaFondo = texturaFondo;
		this.texturaR = texturaR;
		this.texturaG = texturaG;
		this.texturaB = texturaB;
	}

	public TexturaTerreno getTexturaFondo() {
		return texturaFondo;
	}

	public TexturaTerreno getTexturaR() {
		return texturaR;
	}

	public TexturaTerreno getTexturaG() {
		return texturaG;
	}

	public TexturaTerreno getTexturaB() {
		return texturaB;
	}

}
