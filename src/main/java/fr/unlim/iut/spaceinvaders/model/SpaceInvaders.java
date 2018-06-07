package fr.unlim.iut.spaceinvaders.model;

import java.util.ArrayList;
import java.util.List;

import fr.unlim.iut.spaceinvaders.moteurjeu.Commande;
import fr.unlim.iut.spaceinvaders.moteurjeu.Jeu;
import fr.unlim.iut.spaceinvaders.utils.DebordementEspaceJeuException;
import fr.unlim.iut.spaceinvaders.utils.HorsEspaceJeuException;
import fr.unlim.iut.spaceinvaders.utils.MissileException;

public class SpaceInvaders implements Jeu {

	int longueur;
	int hauteur;
	Vaisseau vaisseau;
	List<Missile> missiles;
	List<Envahisseur> envahisseurs;
	private List<Missile> missilesEnvahisseurs;
	int score;
	boolean partieTerminee;

	public SpaceInvaders(int longueur, int hauteur) {
		this.longueur = longueur;
		this.hauteur = hauteur;
		this.missiles = new ArrayList<Missile>();
		this.envahisseurs = new ArrayList<Envahisseur>();
		this.missilesEnvahisseurs = new ArrayList<Missile>();
		this.score = 0;
		
		this.partieTerminee = false;
	}

	public void initialiserJeu() {
		// vaisseau
		Position positionVaisseau = new Position(this.longueur/2,this.hauteur-1);
		Dimension dimensionVaisseau = new Dimension(Constante.VAISSEAU_LONGUEUR, Constante.VAISSEAU_HAUTEUR);
		positionnerUnNouveauVaisseau(dimensionVaisseau, positionVaisseau, Constante.VAISSEAU_VITESSE);
		
		// envahisseurs
		positionnerUneLigneEnvahisseurs();
		
		
	}
	
	
	private void positionnerUneLigneEnvahisseurs() {
		int nbEnvahisseur = calculerNbEnvahisseurs();

		for(int i = 0 ; i < nbEnvahisseur ; i++) {
			positionnerUnNouvelEnvahisseur(	new Dimension(Constante.ENVAHISSEUR_LONGUEUR, Constante.ENVAHISSEUR_HAUTEUR),
					new Position(calculerAbscisseEnvahisseur(i), Constante.ENVAHISSEUR_HAUTEUR*2),
					Constante.ENVAHISSEUR_VITESSE);
		}
	}

	private int calculerAbscisseEnvahisseur(int i) {
		return (int) ( (float)i * ( ((float)Constante.ENVAHISSEUR_LONGUEUR) * 1.5 ) );
	}

	private int calculerNbEnvahisseurs() {
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
		
		this.deplacerTousLesMissiles();
		
		this.deplacementAutomatiqueDesEnvahisseurs();
		
		this.eliminerEnvahisseurSiTouche();
		
		this.controlerMissilesEnvahisseurs();
		
	}
	
	public void controlerMissilesEnvahisseurs() {
		for (int i=0; i < missilesEnvahisseurs.size(); i++) {
			if (Collision.detecterCollision(this.vaisseau, missilesEnvahisseurs.get(i))) {
				this.partieTerminee = true;
			} else {
				for (int j=0; j < missiles.size(); j++) {
					if (this.aUnMissile() && this.aUnEnvahisseur()) {
						if (Collision.detecterCollision(missiles.get(j), missilesEnvahisseurs.get(i))) {
							missiles.remove(j);
							missilesEnvahisseurs.remove(i);
						}
					}
				}
			}
		}
	}
	
	public int score() {
		return this.score;
	}
	
	public void eliminerEnvahisseurSiTouche() {
		for (int i=0; i < missiles.size(); i++) {
			for (int j=0; j < envahisseurs.size(); j++) {
				if (this.aUnMissile() && this.aUnEnvahisseur()) {
					if (Collision.detecterCollision(envahisseurs.get(j), missiles.get(i))) {
						envahisseurs.remove(j);
						missiles.remove(i);
						this.MAJPoints();
					}
				}
			}
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
		return !envahisseurs.isEmpty();
	}

	private boolean aUnEnvahisseurQuiOccupeLaPosition(int x, int y) {
		if (this.aUnEnvahisseur()) {
			for (int i=0; i < envahisseurs.size(); i++) {
				if (envahisseurs.get(i).occupeLaPosition(x, y))
					return true;
			}
		}
		return false;
	}
	
	public void positionnerUnNouvelEnvahisseur(Dimension dimension, Position position, int vitesse) {

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

		envahisseurs.add(new Envahisseur(dimension,position,vitesse));
	}

	public List<Envahisseur> recupererLaListeDesEnvahisseurs() {
		return this.envahisseurs;
	}
	
	public Envahisseur recupererUnEnvahisseur(int index) {
		return this.envahisseurs.get(index);
	}
	
	public void deplacementAutomatiqueDesEnvahisseurs() {
		boolean unEnvahisseurEstAuBord = false;
		if (this.aUnEnvahisseur()) {
			for (int i=0; i < envahisseurs.size(); i++) { 
				envahisseurs.get(i).deplacerHorizontalementVers(envahisseurs.get(i).direction());;
				if (!estDansEspaceJeu(envahisseurs.get(i).abscisseLaPlusADroite(), envahisseurs.get(i).ordonneeLaPlusBasse())
						|| !estDansEspaceJeu(envahisseurs.get(i).abscisseLaPlusAGauche(), envahisseurs.get(i).ordonneeLaPlusBasse())) {
					unEnvahisseurEstAuBord = true;
				}
			}
		}
		if (unEnvahisseurEstAuBord) {
			for (int j=0; j < envahisseurs.size(); j++)
				this.envahisseurs.get(j).changerDirection();
		}
	}

	public void tirerMissileEnvahisseur(Dimension dimensionMissile, int vitesseMissile, int index) {
		if ((envahisseurs.get(index).hauteur()+ dimensionMissile.hauteur()) > this.hauteur )
			throw new MissileException("Pas assez de hauteur libre entre l'envahisseur et le bas de l'espace jeu pour tirer le missile");
			
		Missile nouveauMissile = envahisseurs.get(index).tirerUnMissile(dimensionMissile,vitesseMissile);
		
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
