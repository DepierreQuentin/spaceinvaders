package fr.unlim.iut.spaceinvaders.model;

public class Collision {
	
public static boolean detecterCollision(Sprite sprite1, Sprite sprite2) {
		
		return ( (sprite1.estOrdonneeCouverte(sprite2.ordonneeLaPlusBasse()) 
				&& sprite1.estAbscisseCouverte(sprite2.abscisseLaPlusAGauche()))
			|| (sprite1.estOrdonneeCouverte(sprite2.ordonneeLaPlusHaute()) 
				&& sprite1.estAbscisseCouverte(sprite2.abscisseLaPlusADroite()))
			|| (sprite1.estOrdonneeCouverte(sprite2.ordonneeLaPlusHaute()) 
				&& sprite1.estAbscisseCouverte(sprite2.abscisseLaPlusAGauche()))
			|| (sprite1.estOrdonneeCouverte(sprite2.ordonneeLaPlusBasse()) 
				&& sprite1.estAbscisseCouverte(sprite2.abscisseLaPlusADroite()))
			|| (sprite2.estOrdonneeCouverte(sprite1.ordonneeLaPlusBasse()) 
				&& sprite2.estAbscisseCouverte(sprite1.abscisseLaPlusAGauche()))
			|| (sprite2.estOrdonneeCouverte(sprite1.ordonneeLaPlusHaute()) 
				&& sprite2.estAbscisseCouverte(sprite1.abscisseLaPlusADroite()))
			|| (sprite2.estOrdonneeCouverte(sprite1.ordonneeLaPlusHaute()) 
				&& sprite2.estAbscisseCouverte(sprite1.abscisseLaPlusAGauche()))
			|| (sprite2.estOrdonneeCouverte(sprite1.ordonneeLaPlusBasse()) 
				&& sprite2.estAbscisseCouverte(sprite1.abscisseLaPlusADroite()))
			);
		
	}

}
