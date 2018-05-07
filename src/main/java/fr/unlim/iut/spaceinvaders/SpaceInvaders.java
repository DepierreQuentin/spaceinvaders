package fr.unlim.iut.spaceinvaders;

public class SpaceInvaders {

    int longueur;
    int hauteur;
    Vaisseau vaisseau;

    public SpaceInvaders(int longueur, int hauteur) {
	   this.longueur = longueur;
	   this.hauteur = hauteur;
   }
    
    
    @Override
	public String toString() {
		StringBuilder espaceDeJeu = new StringBuilder();
		for (int y = 0; y < hauteur; y++) {
			for (int x = 0; x < longueur; x++) {
				if (this.aUnVaisseau() && vaisseau.occupeLaPosition(x, y))
					espaceDeJeu.append('V');
				else
					espaceDeJeu.append('.');
			}
			espaceDeJeu.append('\n');
		}
		return espaceDeJeu.toString();
	}


	private boolean aUnVaisseau() {
		return this.vaisseau!=null;
	}


    public void positionnerUnNouveauVaisseau(int x, int y) {
        this.vaisseau = new Vaisseau (x,y);
	}

}

