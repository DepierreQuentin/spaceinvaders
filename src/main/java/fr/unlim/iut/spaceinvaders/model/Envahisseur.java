package fr.unlim.iut.spaceinvaders.model;

import fr.unlim.iut.spaceinvaders.utils.MissileException;

public class Envahisseur extends Sprite {
	
	private Direction direction = Direction.GAUCHE;

	public Envahisseur(Dimension dimension, Position origine, int vitesse) {
		super(dimension, origine, vitesse);
	}
	
	public Direction direction() {
		return this.direction;
	}
	
	public void changerDirection() {
		if (this.direction == Direction.GAUCHE)
			this.direction = Direction.DROITE;
		else 
			this.direction = Direction.GAUCHE;
	}

	public Missile tirerUnMissile(Dimension dimensionMissile, int vitesseMissile) {
		if (dimensionMissile.longueur()>this.longueur())
			throw new MissileException("Le missile est plus large que l'envahisseur");
		
		Position positionOrigineMissile = calculerLaPositionDeTirDuMissile(dimensionMissile);

		return new Missile(dimensionMissile, positionOrigineMissile, vitesseMissile);
	}
	
	private Position calculerLaPositionDeTirDuMissile(Dimension dimensionMissile) {
		int abscisseMilieuVaisseau = this.abscisseLaPlusAGauche() + (this.longueur() / 2);
		int abscisseOrigineMissile = abscisseMilieuVaisseau - (dimensionMissile.longueur() / 2);

		int ordonneeeOrigineMissile = this.ordonneeLaPlusHaute() + this.hauteur();
		Position positionOrigineMissile = new Position(abscisseOrigineMissile, ordonneeeOrigineMissile);
		return positionOrigineMissile;
	}
	
}
