package fr.unlim.iut.spaceinvaders.model;

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
	
}
