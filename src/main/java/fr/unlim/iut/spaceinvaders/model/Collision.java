package fr.unlim.iut.spaceinvaders.model;

public class Collision {
	
	public static boolean detecterCollision(Sprite missile, Sprite envahisseur) {
		if (uneDesAbscissesDuMissileEstEntreLesAbscissesDeEnvahisseur(missile, envahisseur) &&
				uneDesOrdonneesDuMissileEstEntreLesOrdonneesDeEnvahisseur(missile, envahisseur)) 
				return true;
		
		return false;
	}
	
	private static boolean uneDesOrdonneesDuMissileEstEntreLesOrdonneesDeEnvahisseur(Sprite missile, Sprite envahisseur) {
		return envahisseur.estOrdonneeCouverte(missile.ordonneeLaPlusHaute()) ||
				envahisseur.estOrdonneeCouverte(missile.ordonneeLaPlusBasse());
	}

	private static boolean uneDesAbscissesDuMissileEstEntreLesAbscissesDeEnvahisseur(Sprite missile, Sprite envahisseur) {
		return envahisseur.estAbscisseCouverte(missile.abscisseLaPlusAGauche()) ||
				envahisseur.estAbscisseCouverte(missile.abscisseLaPlusADroite());
	}

}
