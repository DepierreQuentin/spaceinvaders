package fr.unlim.iut.spaceinvaders.model;

import java.util.ArrayList;
import java.util.List;

public class LigneEnvahisseur {
	private List<Envahisseur> envahisseurs;
	
	public LigneEnvahisseur() {
		this.envahisseurs = new ArrayList<Envahisseur>();
	}
	
	public List<Envahisseur> getAll() {
		return this.envahisseurs;
	}
	
	public Envahisseur recupererUnEnvahisseur(int index) {
		return this.envahisseurs.get(index);
	}

}
