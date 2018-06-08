package fr.unlim.iut.spaceinvaders.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.unlim.iut.spaceinvaders.moteurjeu.Commande;
import fr.unlim.iut.spaceinvaders.moteurjeu.Jeu;
import fr.unlim.iut.spaceinvaders.utils.DebordementEspaceJeuException;
import fr.unlim.iut.spaceinvaders.utils.HorsEspaceJeuException;
import fr.unlim.iut.spaceinvaders.utils.MissileException;

public class SpaceInvaders implements Jeu {

	private int longueur;
	private int hauteur;
	private Vaisseau vaisseau;
	private List<Missile> missiles;
	private LigneEnvahisseur[] tousLesEnvahisseurs;
	private List<Missile> missilesEnvahisseurs;
	private int score;
	private boolean partieTerminee;
	private Random rand;

	public SpaceInvaders(int longueur, int hauteur) {
		this.longueur = longueur;
		this.hauteur = hauteur;
		this.missiles = new ArrayList<Missile>();
		
		int nbLignes = calculerNbLignes();
		this.tousLesEnvahisseurs = new LigneEnvahisseur[nbLignes];
		
		for(int i = 0 ; i < nbLignes ; i++) {
			this.tousLesEnvahisseurs[i] = new LigneEnvahisseur();
		}
		
		this.missilesEnvahisseurs = new ArrayList<Missile>();
		this.score = 0;
		
		rand = new Random();
		
		this.partieTerminee = false;
	}

	public void initialiserJeu() {
		// vaisseau
		Position positionVaisseau = new Position(this.longueur/2,this.hauteur-1);
		Dimension dimensionVaisseau = new Dimension(Constante.VAISSEAU_LONGUEUR, Constante.VAISSEAU_HAUTEUR);
		positionnerUnNouveauVaisseau(dimensionVaisseau, positionVaisseau, Constante.VAISSEAU_VITESSE);
		
		// envahisseurs
		positionnerTousLesEnvahisseurs();
		
	}
	
	public void positionnerTousLesEnvahisseurs() {
		int nbLignes = this.calculerNbLignes();
		for (int numLigne=0; numLigne < nbLignes; numLigne++) {
			positionnerUneLigneEnvahisseurs(numLigne);
		}
	}
	
	private void positionnerUneLigneEnvahisseurs(int numLigne) {
		int nbEnvahisseurParLigne = calculerNbEnvahisseursParLigne();

		for(int i = 0 ; i < nbEnvahisseurParLigne ; i++) {
			positionnerUnNouvelEnvahisseur(new Dimension(Constante.ENVAHISSEUR_LONGUEUR, Constante.ENVAHISSEUR_HAUTEUR),
					new Position(calculerAbscisseEnvahisseur(i), calculerOrdonneeEnvahisseur(numLigne)),
					Constante.ENVAHISSEUR_VITESSE, numLigne);
		}
	}
	
	private int calculerOrdonneeEnvahisseur(int j) {
		return (int) ( (float)j * ( ((float)Constante.ENVAHISSEUR_HAUTEUR) * 1.5 ) ) + Constante.ENVAHISSEUR_HAUTEUR;
	}
	
	private int calculerNbLignes() {
		return (int) ( ( (float) (Constante.ESPACEJEU_HAUTEUR/3) ) / ( ((float)Constante.ENVAHISSEUR_HAUTEUR) * 1.5 ) ) - 1;
	}

	private int calculerAbscisseEnvahisseur(int i) {
		return (int) ( (float)i * ( ((float)Constante.ENVAHISSEUR_LONGUEUR) * 1.5 ) );
	}

	private int calculerNbEnvahisseursParLigne() {
		return (int) ( ( (float) Constante.ESPACEJEU_LONGUEUR ) / ( ((float)Constante.ENVAHISSEUR_LONGUEUR) * 1.5 ) ) - 1;
	}
	

	public String recupererEspaceJeuDansChaineASCII() {
		StringBuilder espaceDeJeu = new StringBuilder();
		for (int y = 0; y < hauteur; y++) {
			for (int x = 0; x < longueur; x++) {
				espaceDeJeu.append(recupererMarqueDeLaPosition(x, y));
			}
			espaceDeJeu.append(Constante.MARQUE_FIN_LIGNE);
		}
		return espaceDeJeu.toString();
	}

	private char recupererMarqueDeLaPosition(int x, int y) {
		char marque;
		if (this.aUnVaisseauQuiOccupeLaPosition(x, y))
			marque = Constante.MARQUE_VAISSEAU;
		else if (this.aUnMissileQuiOccupeLaPosition(x, y))
				marque = Constante.MARQUE_MISSILE;
		else if (this.aUnEnvahisseurQuiOccupeLaPosition(x,y))
			marque = Constante.MARQUE_ENVAHISSEUR;
		else if (this.aUnMissileEnvahisseurQuiOccupeLaPosition(x, y))
			marque = Constante.MARQUE_MISSILE_ENVAHISSEUR;
		else marque = Constante.MARQUE_VIDE;
		return marque;
	}

	private boolean aUnVaisseauQuiOccupeLaPosition(int x, int y) {
		return this.aUnVaisseau() && vaisseau.occupeLaPosition(x, y);
	}

	public boolean aUnVaisseau() {
		return vaisseau != null;
	}

	public void positionnerUnNouveauVaisseau(Dimension dimension, Position position, int vitesse) {

		int x = position.abscisse();
		int y = position.ordonnee();

		if (!estDansEspaceJeu(x, y))
			throw new HorsEspaceJeuException("La position du vaisseau est en dehors de l'espace jeu");

		int longueurVaisseau = dimension.longueur(); 
		int hauteurVaisseau = dimension.hauteur();

		if (!estDansEspaceJeu(x + longueurVaisseau - 1, y))
			throw new DebordementEspaceJeuException(
					"Le vaisseau déborde de l'espace jeu vers la droite à cause de sa longueur");
		if (!estDansEspaceJeu(x, y - hauteurVaisseau + 1))
			throw new DebordementEspaceJeuException(
					"Le vaisseau déborde de l'espace jeu vers le bas à cause de sa hauteur");

		vaisseau = new Vaisseau(dimension,position,vitesse);
	}

	private boolean estDansEspaceJeu(int x, int y) {
		return ((x >= 0) && (x < longueur)) && ((y >= 0) && (y < hauteur));
	}

	public void deplacerVaisseauVersLaDroite() {
		if (vaisseau.abscisseLaPlusADroite() < (longueur - 1)) {
			vaisseau.deplacerHorizontalementVers(Direction.DROITE);
			if (!estDansEspaceJeu(vaisseau.abscisseLaPlusADroite(), vaisseau.ordonneeLaPlusHaute())) {
				vaisseau.positionner(longueur - vaisseau.longueur(), vaisseau.ordonneeLaPlusHaute());
			}
		}
	}

	public void deplacerVaisseauVersLaGauche() {
		if (0 < vaisseau.abscisseLaPlusAGauche())
			vaisseau.deplacerHorizontalementVers(Direction.GAUCHE);
		if (!estDansEspaceJeu(vaisseau.abscisseLaPlusAGauche(), vaisseau.ordonneeLaPlusHaute())) {
			vaisseau.positionner(0, vaisseau.ordonneeLaPlusHaute());
		}
	}

	public Vaisseau recupererVaisseau() {
		return this.vaisseau;
	}

	@Override
	public void evoluer(Commande commandeUser) {

		if (commandeUser.gauche) {
			deplacerVaisseauVersLaGauche();
		}

		if (commandeUser.droite) {
			deplacerVaisseauVersLaDroite();
		}
		
		if (commandeUser.tir) {
			tirerUnMissile(new Dimension(Constante.MISSILE_LONGUEUR, Constante.MISSILE_HAUTEUR),
					Constante.MISSILE_VITESSE);
		}
		
		/*
		if(rand.nextFloat() < Constante.ENVAHISSEUR_PROBABILITE_TIR) {
			tirerMissileEnvahisseurAleatoirement(new Dimension(Constante.MISSILE_LONGUEUR, Constante.MISSILE_HAUTEUR), Constante.MISSILE_VITESSE);
		}
		*/
		
		this.deplacerTousLesMissiles();
		
		this.deplacementAutomatiqueDesEnvahisseurs();
		
		
		this.controlerTousLesMissiles();
		
	}
	
	public void controlerTousLesMissiles() {
		this.controlerMissilesVaisseau();
		
		this.controlerMissilesEnvahisseurs();
	}
	
	public void tirerMissileEnvahisseurAleatoirement(Dimension dimensionMissile, int vitesseMissile) {
		if (this.aUnEnvahisseur()) {
			int indexAleatoire = rand.nextInt(tousLesEnvahisseurs[0].getAll().size());
		
			tirerMissileEnvahisseur(dimensionMissile, vitesseMissile, indexAleatoire);
		}
	}
	
	public void controlerMissilesEnvahisseurs() {
		for (int i=0; i < missilesEnvahisseurs.size(); i++) {
			if (Collision.detecterCollision(this.vaisseau, missilesEnvahisseurs.get(i))) {
				this.partieTerminee = true;
			}
		}
	}
	
	public int score() {
		return this.score;
	}
	
	public void controlerMissilesVaisseau() {
		for (int i=0; i < missiles.size(); i++) {
			for (int numLigne=0; numLigne < tousLesEnvahisseurs.length; numLigne++) {
				for (int j=0; j < tousLesEnvahisseurs[numLigne].getAll().size(); j++) {
					if (this.aUnMissile() && this.aUnEnvahisseurSurLaLigne(numLigne)) {
						if (Collision.detecterCollision(tousLesEnvahisseurs[numLigne].recupererUnEnvahisseur(j), missiles.get(i))) {
							tousLesEnvahisseurs[numLigne].getAll().remove(j);
							missiles.remove(i);
							this.MAJPoints();
						}
					}
				}
			}
			/*
			 * cas d'utilisation supprimé, explication dans SpaceInvadersTest
			 * 
			for (int j=0; j < missilesEnvahisseurs.size(); j++) {
				if (this.aUnMissile() && this.aUnMissileEnvahisseur()) {
					if (Collision.detecterCollision(missilesEnvahisseurs.get(j), missiles.get(i))) {
						missilesEnvahisseurs.remove(j);
						missiles.remove(i);
					}
				}
			}
			*/
		}
	}
	
	public void MAJPoints() {
		this.score += Constante.POINTS_DESTRUCTION_ENVAHISSEUR;
	}
	
	@Override
	public boolean etreFini() {
		if (!this.aUnEnvahisseur())
			return true;
		
		return partieTerminee;
	}

	public void tirerUnMissile(Dimension dimensionMissile, int vitesseMissile) {
		if ((vaisseau.hauteur()+ dimensionMissile.hauteur()) > this.hauteur )
			throw new MissileException("Pas assez de hauteur libre entre le vaisseau et le haut de l'espace jeu pour tirer le missile");
			
		Missile nouveauMissile = this.vaisseau.tirerUnMissile(dimensionMissile,vitesseMissile);
		
		boolean peutEtreTire = true;
		
		for (int i=0; i < missiles.size(); i++) {
			if (Collision.detecterCollision(missiles.get(i), nouveauMissile)) {
				peutEtreTire = false;
			}
		}
		
		if (peutEtreTire)
			this.missiles.add(nouveauMissile);
		
	}

	boolean aUnMissile() {
		return !missiles.isEmpty();
	}

	private boolean aUnMissileQuiOccupeLaPosition(int x, int y) {
		if (this.aUnMissile()) {
			for (int i=0; i < missiles.size(); i++) {
				if (missiles.get(i).occupeLaPosition(x, y))
					return true;
			}
		}
		
		return false;
	}
	
	public List<Missile> recupererLaListeDesMissiles() {
		return this.missiles;
	}
	
	public Missile recupererUnMissile(int index) {
		return this.missiles.get(index);
	}

	public void deplacerTousLesMissiles() {
		deplacerMissilesVaisseau();
		deplacerMissilesEnvahisseurs();
	}

	private void deplacerMissilesVaisseau() {
		if (this.aUnMissile()) {
			for (int i=0; i < missiles.size(); i++) { 
				missiles.get(i).deplacerVerticalementVers(Direction.HAUT_ECRAN);
				if (!estDansEspaceJeu(missiles.get(i).abscisseLaPlusADroite(), missiles.get(i).ordonneeLaPlusBasse())) {
					this.missiles.remove(i);
				}
			}
		}
	}
	
	
	boolean aUnEnvahisseur() {
		for (int i=0; i < tousLesEnvahisseurs.length; i++) {
			if (!tousLesEnvahisseurs[i].getAll().isEmpty())
				return true;
		}
		return false;
	}
	
	boolean aUnEnvahisseurSurLaLigne(int numLigne) {
		if (!tousLesEnvahisseurs[numLigne].getAll().isEmpty())
			return true;
		return false;
	}

	private boolean aUnEnvahisseurQuiOccupeLaPosition(int x, int y) {
		if (this.aUnEnvahisseur()) {
			for (int j=0; j < tousLesEnvahisseurs.length; j++) {
				for (int i=0; i < tousLesEnvahisseurs[j].getAll().size(); i++) {
					if (tousLesEnvahisseurs[j].recupererUnEnvahisseur(i).occupeLaPosition(x, y))
						return true;
				}
			}
		}
		return false;
	}
	
	public void positionnerUnNouvelEnvahisseur(Dimension dimension, Position position, int vitesse, int numLigne) {

		int x = position.abscisse();
		int y = position.ordonnee();

		if (!estDansEspaceJeu(x, y))
			throw new HorsEspaceJeuException("La position de l'envahisseur est en dehors de l'espace jeu");

		int longueurEnvahisseur = dimension.longueur(); 
		int hauteurEnvahisseur = dimension.hauteur();

		if (!estDansEspaceJeu(x + longueurEnvahisseur - 1, y))
			throw new DebordementEspaceJeuException(
					"L'envahisseur déborde de l'espace jeu vers la droite à cause de sa longueur");
		if (!estDansEspaceJeu(x, y - hauteurEnvahisseur + 1))
			throw new DebordementEspaceJeuException(
					"L'envahisseur déborde de l'espace jeu vers le bas à cause de sa hauteur");

		tousLesEnvahisseurs[numLigne].getAll().add(new Envahisseur(dimension,position,vitesse));
	}

	public LigneEnvahisseur[] recupererLaListeDeTousLesEnvahisseurs() {
		return this.tousLesEnvahisseurs;
	}
	
	public LigneEnvahisseur recupererUneLigneDEnvahisseur(int index) {
		return this.tousLesEnvahisseurs[index];
	}
	
	public void deplacementAutomatiqueDesEnvahisseurs() {
		boolean unEnvahisseurEstAuBord = false;
		if (this.aUnEnvahisseur()) {
			for (int j=0; j < tousLesEnvahisseurs.length; j++) {
				for (int i=0; i < tousLesEnvahisseurs[j].getAll().size(); i++) { 
					tousLesEnvahisseurs[j].recupererUnEnvahisseur(i).deplacerHorizontalementVers(
							tousLesEnvahisseurs[j].recupererUnEnvahisseur(i).direction());;
					if (!estDansEspaceJeu(tousLesEnvahisseurs[j].recupererUnEnvahisseur(i).abscisseLaPlusADroite(), 
							tousLesEnvahisseurs[j].recupererUnEnvahisseur(i).ordonneeLaPlusBasse())
							|| !estDansEspaceJeu(tousLesEnvahisseurs[j].recupererUnEnvahisseur(i).abscisseLaPlusAGauche(), 
									tousLesEnvahisseurs[j].recupererUnEnvahisseur(i).ordonneeLaPlusBasse())) {
						
						unEnvahisseurEstAuBord = true;
					}
				}
			}
		}
		if (unEnvahisseurEstAuBord) {
			for (int j=0; j < tousLesEnvahisseurs.length; j++) {
				for (int i=0; i < tousLesEnvahisseurs[j].getAll().size(); i++) {
					this.tousLesEnvahisseurs[j].recupererUnEnvahisseur(i).changerDirection();
					//this.tousLesEnvahisseurs[j].recupererUnEnvahisseur(i).faireDescendre();
				}
			}
		}
	}

	public void tirerMissileEnvahisseur(Dimension dimensionMissile, int vitesseMissile, int index) {
		Envahisseur tireur = tousLesEnvahisseurs[index].recupererUnEnvahisseur(
				tousLesEnvahisseurs[index].getAll().size() -1);
		if ((tireur.hauteur()+ dimensionMissile.hauteur()) > this.hauteur )
			throw new MissileException("Pas assez de hauteur libre entre l'envahisseur et le bas de l'espace jeu pour tirer le missile");
			
		Missile nouveauMissile = tireur.tirerUnMissile(dimensionMissile,vitesseMissile);
		
		boolean peutEtreTire = true;
		
		for (int i=0; i < missilesEnvahisseurs.size(); i++) {
			if (Collision.detecterCollision(missilesEnvahisseurs.get(i), nouveauMissile)) {
				peutEtreTire = false;
			}
		}
		
		if (peutEtreTire)
			this.missilesEnvahisseurs.add(nouveauMissile);
		
	}
	
	boolean aUnMissileEnvahisseur() {
		return !missilesEnvahisseurs.isEmpty();
	}

	private boolean aUnMissileEnvahisseurQuiOccupeLaPosition(int x, int y) {
		if (this.aUnEnvahisseur()) {
			for (int i=0; i < missilesEnvahisseurs.size(); i++) {
				if (missilesEnvahisseurs.get(i).occupeLaPosition(x, y))
					return true;
			}
		}
		return false;
	}
	
	public List<Missile> recupererLaListeDesMissilesEnvahisseurs() {
		return this.missilesEnvahisseurs;
	}
	
	public Missile recupererUnMissileEnvahisseur(int index) {
		return this.missilesEnvahisseurs.get(index);
	}

	public void deplacerMissilesEnvahisseurs() {
		if (this.aUnMissileEnvahisseur()) {
			for (int i=0; i < missilesEnvahisseurs.size(); i++) { 
				missilesEnvahisseurs.get(i).deplacerVerticalementVers(Direction.BAS_ECRAN);
				if (!estDansEspaceJeu(missilesEnvahisseurs.get(i).abscisseLaPlusADroite(), missilesEnvahisseurs.get(i).ordonneeLaPlusHaute())) {
					this.missilesEnvahisseurs.remove(i);
				}
			}
		}
	}

}
