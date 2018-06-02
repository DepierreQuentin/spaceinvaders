package fr.unlim.iut.spaceinvaders.moteurjeu;

import fr.unlim.iut.spaceinvaders.model.Constante;
import fr.unlim.iut.spaceinvaders.model.DessinSpaceInvaders;
import fr.unlim.iut.spaceinvaders.model.MoteurGraphique;
import fr.unlim.iut.spaceinvaders.model.SpaceInvaders;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		SpaceInvaders jeu = new SpaceInvaders(Constante.ESPACEJEU_LONGUEUR, Constante.ESPACEJEU_HAUTEUR);
		jeu.initialiserJeu();
		DessinSpaceInvaders afficheur = new DessinSpaceInvaders(jeu);

		MoteurGraphique moteur = new MoteurGraphique(jeu, afficheur);
		moteur.lancerJeu(Constante.ESPACEJEU_LONGUEUR, Constante.ESPACEJEU_HAUTEUR);
	}

}