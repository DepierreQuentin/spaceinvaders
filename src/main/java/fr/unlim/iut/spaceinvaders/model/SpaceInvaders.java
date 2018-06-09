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
	private List<List<Envahisseur>> tousLesEnvahisseurs;
	private List<Missile> missilesEnvahisseurs;
	private int score;
	private boolean partieTerminee;
	private Random rand;

	public SpaceInvaders(int longueur, int hauteur) {
		this.longueur = longueur;
		this.hauteur = hauteur;
		this.missiles = new ArrayList<Missile>();
		
		this.tousLesEnvahisseurs = new ArrayList<List<Envahisseur>>();
		
		for(int i=0; i < Calculs.nbLignesEnvahisseurs; i++) {
			tousLesEnvahisseurs.add(new ArrayList<Envahisseur>());
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
		int nbLignes = Calculs.nbLignesEnvahisseurs;
		for (int numLigne=0; numLigne < nbLignes; numLigne++) {
			positionnerUneLigneEnvahisseurs(numLigne);
		}
	}
	
	private void positionnerUneLigneEnvahisseurs(int numLigne) {
		for(int i = 0 ; i < Calculs.nbEnvahisseursParLigne ; i++) {
			positionnerUnNouvelEnvahisseur(new Dimension(Constante.ENVAHISSEUR_LONGUEUR, Constante.ENVAHISSEUR_HAUTEUR),
					new Position(calculerAbscisseEnvahisseur(i), calculerOrdonneeEnvahisseur(numLigne)),
					Constante.ENVAHISSEUR_VITESSE, numLigne);
		}
	}
	
	private int calculerOrdonneeEnvahisseur(int j) {
		return (int) ( (float)j * ( ((float)Constante.ENVAHISSEUR_HAUTEUR) * 1.5 ) ) + Constante.ENVAHISSEUR_HAUTEUR;
	}

	private int calculerAbscisseEnvahisseur(int i) {
		return (int) ( (float)i * ( ((float)Constante.ENVAHISSEUR_LONGUEUR) * 1.5 ) );
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
		
		if(rand.nextFloat() < Constante.ENVAHISSEUR_PROBABILITE_TIR) {
			tirerMissileEnvahisseurAleatoirement(new Dimension(Constante.MISSILE_LONGUEUR, Constante.MISSILE_HAUTEUR), Constante.MISSILE_VITESSE);
		}
		
		this.deplacerTousLesMissiles();
		
		this.deplacerLesEnvahisseurs();
		
		
		this.controlerTousLesMissiles();
		
	}
	
	public void controlerTousLesMissiles() {
		this.controlerMissilesVaisseau();
		
		this.controlerMissilesEnvahisseurs();
	}
	
	public void tirerMissileEnvahisseurAleatoirement(Dimension dimensionMissile, int vitesseMissile) {
		if (this.aUnEnvahisseur()) {
			int indexAleatoire = rand.nextInt(tousLesEnvahisseurs.get(0).size());
			
			int numLigne = 0;
			/* Les tireurs sont ceux de la ligne la plus haute
			 * 
			boolean tireurTrouve = false;
			while ((numLigne < Calculs.nbLignesEnvahisseurs) && !tireurTrouve) {
				if (estLEnvahisseurLePlusBasDeSaColonne(numLigne, indexAleatoire))
					tireurTrouve = true;
				
				if (!tireurTrouve)
					numLigne++;
			}
			*/
		
			tirerMissileEnvahisseur(dimensionMissile, vitesseMissile, numLigne, indexAleatoire);
		}
	}
	
	public boolean estLEnvahisseurLePlusBasDeSaColonne(int numLigne, int index) {
		
		Envahisseur envahisseur = this.recupererUneLigneDEnvahisseur(numLigne).get(index);
		
		int ordonneeEnvahisseurPotentiellementEnDessous = envahisseur.ordonneeDuMilieu() + envahisseur.hauteur();
		
		return (this.aUnEnvahisseurQuiOccupeLaPosition(envahisseur.abscisseDuMilieu(), ordonneeEnvahisseurPotentiellementEnDessous));
		
	}
	
	public void controlerMissilesEnvahisseurs() {
		//for (int i=0; i < missilesEnvahisseurs.size(); i++) {
		for (Missile missileEnvahisseur : missilesEnvahisseurs) {
			if (Collision.detecterCollision(this.vaisseau, missileEnvahisseur)) {
				this.partieTerminee = true;
			}
		}
	}
	
	public int score() {
		return this.score;
	}
	
	public void controlerMissilesVaisseau() {
		for (int i=0; i < missiles.size(); i++) {
			for (int numLigne=0; numLigne < tousLesEnvahisseurs.size(); numLigne++) {
				for (int j=0; j < tousLesEnvahisseurs.get(numLigne).size(); j++) {
					if (this.aUnMissile() && this.aUnEnvahisseurSurLaLigne(numLigne)) {
						if (Collision.detecterCollision(tousLesEnvahisseurs.get(numLigne).get(j), missiles.get(i))) {
							tousLesEnvahisseurs.get(numLigne).remove(j);
							missiles.remove(i);
							this.MAJPoints();
						}
					}
				}
			}
			
			/* cas d'utilisation supprimé (annulation de 2 missiles qui se rencontrent
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
		if (!this.aUnEnvahisseur() || this.collisionEnvahisseursVaisseur())
			return true;
		
		return partieTerminee;
	}
	
	public boolean collisionEnvahisseursVaisseur() {
		for (List<Envahisseur> ligne : tousLesEnvahisseurs) {
			for (Envahisseur envahisseur : ligne) {
				if (Collision.detecterCollision(this.vaisseau, envahisseur))
					return true;
			}
		}
		
		return false;
	}

	public void tirerUnMissile(Dimension dimensionMissile, int vitesseMissile) {
		if ((vaisseau.hauteur()+ dimensionMissile.hauteur()) > this.hauteur )
			throw new MissileException("Pas assez de hauteur libre entre le vaisseau et le haut de l'espace jeu pour tirer le missile");
			
		Missile nouveauMissile = this.vaisseau.tirerUnMissile(dimensionMissile,vitesseMissile);
		
		boolean peutEtreTire = true;
		
		//for (int i=0; i < missiles.size(); i++) {
		for (Missile missile : missiles) {
			if (Collision.detecterCollision(missile, nouveauMissile)) {
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
			//for (int i=0; i < missiles.size(); i++) {
			for (Missile missile : missiles) {
				if (missile.occupeLaPosition(x, y))
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
				if (!estDansEspaceJeu(missiles.get(i).abscisseLaPlusADroite(), 
						missiles.get(i).ordonneeLaPlusBasse())) {
					this.missiles.remove(i);
				}
			}
		}
	}
	
	
	boolean aUnEnvahisseur() {
		//for (int i=0; i < tousLesEnvahisseurs.size(); i++) {
		for (List<Envahisseur> ligne : tousLesEnvahisseurs) {
			if (!ligne.isEmpty())
				return true;
		}
		return false;
	}
	
	boolean aUnEnvahisseurSurLaLigne(int numLigne) {
		if (!tousLesEnvahisseurs.get(numLigne).isEmpty())
			return true;
		return false;
	}

	private boolean aUnEnvahisseurQuiOccupeLaPosition(int x, int y) {
		if (this.aUnEnvahisseur()) {
			//for (int j=0; j < tousLesEnvahisseurs.size(); j++) {
			for (List<Envahisseur> ligne : tousLesEnvahisseurs) {
				//for (int i=0; i < tousLesEnvahisseurs.get(j).size(); i++) {
				for (Envahisseur envahisseur : ligne) {
					if (envahisseur.occupeLaPosition(x, y))
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

		tousLesEnvahisseurs.get(numLigne).add(new Envahisseur(dimension,position,vitesse));
	}

	public List<List<Envahisseur>> recupererLaListeDeTousLesEnvahisseurs() {
		return this.tousLesEnvahisseurs;
	}
	
	public List<Envahisseur> recupererUneLigneDEnvahisseur(int index) {
		return this.tousLesEnvahisseurs.get(index);
	}
	
	public void deplacerLesEnvahisseurs() {
		boolean unEnvahisseurEstAuBord = false;
		if (this.aUnEnvahisseur()) {
			//for (int j=0; j < tousLesEnvahisseurs.size(); j++) {
			for (List<Envahisseur> ligne : tousLesEnvahisseurs) {
				//for (int i=0; i < tousLesEnvahisseurs.get(j).size(); i++) { 
				for (Envahisseur envahisseur : ligne) {
					envahisseur.deplacerHorizontalementVers(envahisseur.direction());
					
					if (!estDansEspaceJeu(envahisseur.abscisseLaPlusADroite(), envahisseur.ordonneeLaPlusBasse())
							|| !estDansEspaceJeu(envahisseur.abscisseLaPlusAGauche(), envahisseur.ordonneeLaPlusBasse())) {
						
						unEnvahisseurEstAuBord = true;
					}
				}
			}
		}
		if (unEnvahisseurEstAuBord) {
			//for (int j=0; j < tousLesEnvahisseurs.size(); j++) {
			for (List<Envahisseur> ligne : tousLesEnvahisseurs) {
				//for (int i=0; i < tousLesEnvahisseurs.get(j).size(); i++) {
				for (Envahisseur envahisseur : ligne) {
					envahisseur.changerDirection();
					envahisseur.descendreAUneCertaineVitesse(Constante.ENVAHISSEUR_VITESSE_DESCENTE);
				}
			}
		}
	}

	public void tirerMissileEnvahisseur(Dimension dimensionMissile, int vitesseMissile, int numLigne, int index) {
		Envahisseur tireur = tousLesEnvahisseurs.get(numLigne).get(index);
		if ((tireur.hauteur()+ dimensionMissile.hauteur()) > this.hauteur )
			throw new MissileException("Pas assez de hauteur libre entre l'envahisseur et le bas de l'espace jeu pour tirer le missile");
			
		Missile nouveauMissile = tireur.tirerUnMissile(dimensionMissile,vitesseMissile);
		
		boolean peutEtreTire = true;
		
		//for (int i=0; i < missilesEnvahisseurs.size(); i++) {
		for (Missile missileEnvahisseur : missilesEnvahisseurs) {
			if (Collision.detecterCollision(missileEnvahisseur, nouveauMissile)) {
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
			//for (int i=0; i < missilesEnvahisseurs.size(); i++) {
			for (Missile missileEnvahisseur : missilesEnvahisseurs) {
				if (missileEnvahisseur.occupeLaPosition(x, y))
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
