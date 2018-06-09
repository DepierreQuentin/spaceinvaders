package fr.unlim.iut.spaceinvaders.model;

public class Calculs {

	public static final int nbLignesEnvahisseurs = calculerNbLignes();
	public static final int nbEnvahisseursParLigne = calculerNbEnvahisseursParLigne();
	

	
	private static int calculerNbLignes() {
		return (int) ( ( (float) (Constante.ESPACEJEU_HAUTEUR/3) ) / ( ((float)Constante.ENVAHISSEUR_HAUTEUR) * 1.5 ) ) - 1;
	}

	private static int calculerNbEnvahisseursParLigne() {
		return (int) ( ( (float) Constante.ESPACEJEU_LONGUEUR ) / ( ((float)Constante.ENVAHISSEUR_LONGUEUR) * 1.5 ) ) - 1;
	}
}
