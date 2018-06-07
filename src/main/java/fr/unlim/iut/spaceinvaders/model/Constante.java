package fr.unlim.iut.spaceinvaders.model;

public class Constante {

	   public static final int ESPACEJEU_LONGUEUR = 750;
	   public static final int ESPACEJEU_HAUTEUR = 500;
	
	   public static final int VAISSEAU_LONGUEUR = 60;
	   public static final int VAISSEAU_HAUTEUR = 30;
	   public static final int VAISSEAU_VITESSE = 7;
	   
	   public static final int MISSILE_LONGUEUR = 15;
	   public static final int MISSILE_HAUTEUR = 25;
	   public static final int MISSILE_VITESSE = 5;
	   
	   public static final int ENVAHISSEUR_LONGUEUR = 30;
	   public static final int ENVAHISSEUR_HAUTEUR = 20;
	   public static final int ENVAHISSEUR_VITESSE = 2;
	
	   public static final char MARQUE_FIN_LIGNE = '\n';
	   public static final char MARQUE_VIDE = '.';
	   public static final char MARQUE_VAISSEAU = 'V';
	   public static final char	MARQUE_MISSILE = 'M';
	   public static final char	MARQUE_ENVAHISSEUR = 'E';
	   public static final char	MARQUE_MISSILE_ENVAHISSEUR = 'W';
	   
	   
	   public static final int POINTS_DESTRUCTION_ENVAHISSEUR = 100;
	   public static final int ZONE_SCORE_HAUTEUR = 50;
	   public static final int SCORE_ABSCISSE = 25;
	   public static final int SCORE_ORDONNEE = ESPACEJEU_HAUTEUR + ZONE_SCORE_HAUTEUR/2 ;
}