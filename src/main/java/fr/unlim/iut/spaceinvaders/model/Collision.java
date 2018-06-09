package fr.unlim.iut.spaceinvaders.model;

public class Collision {
	
	public static boolean detecterCollision(Sprite sprite1, Sprite sprite2) {
		if (sprite1 != null && sprite2 != null) { 
			return ( testColisionDUnSpriteSurUnAutre(sprite1, sprite2)
				|| testColisionDUnSpriteSurUnAutre(sprite2, sprite1)
				);
		}
		return false;
		
	}

	private static boolean testColisionDUnSpriteSurUnAutre(Sprite sprite1, Sprite sprite2) {
		return couvreCoinBasGauche(sprite1, sprite2)
			|| couvreCoinHautDroit(sprite1, sprite2)
			|| couvreCoinHautGauche(sprite1, sprite2)
			|| couvreCoinBasDroit(sprite1, sprite2);
	}

	private static boolean couvreCoinBasGauche(Sprite sprite1, Sprite sprite2) {
		return sprite1.estOrdonneeCouverte(sprite2.ordonneeLaPlusBasse()) 
				&& sprite1.estAbscisseCouverte(sprite2.abscisseLaPlusAGauche());
	}

	private static boolean couvreCoinHautDroit(Sprite sprite1, Sprite sprite2) {
		return sprite1.estOrdonneeCouverte(sprite2.ordonneeLaPlusHaute()) 
			&& sprite1.estAbscisseCouverte(sprite2.abscisseLaPlusADroite());
	}

	private static boolean couvreCoinHautGauche(Sprite sprite1, Sprite sprite2) {
		return sprite1.estOrdonneeCouverte(sprite2.ordonneeLaPlusHaute()) 
			&& sprite1.estAbscisseCouverte(sprite2.abscisseLaPlusAGauche());
	}

	private static boolean couvreCoinBasDroit(Sprite sprite1, Sprite sprite2) {
		return sprite1.estOrdonneeCouverte(sprite2.ordonneeLaPlusBasse()) 
			&& sprite1.estAbscisseCouverte(sprite2.abscisseLaPlusADroite());
	}

}
