package fr.unlim.iut.spaceinvaders.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import fr.unlim.iut.spaceinvaders.moteurjeu.DessinJeu;

public class DessinSpaceInvaders implements DessinJeu {

	private SpaceInvaders jeu;

	public DessinSpaceInvaders(SpaceInvaders spaceInvaders) {
		this.jeu = spaceInvaders;
	}

	@Override
	public void dessiner(BufferedImage im) {
		
		this.dessinBackground(im);
		
		if (this.jeu.aUnVaisseau()) {
			Vaisseau vaisseau = this.jeu.recupererVaisseau();
			this.dessinerUnVaisseau(vaisseau, im);
		}
		if (this.jeu.aUnMissile()) {
			List<Missile> missiles = this.jeu.recupererLaListeDesMissiles();
			//for (int i=0; i < missiles.size(); i++) {
			for (Missile missile : missiles) {
				this.dessinerUnMissile(missile, im);
			}
		}
		if (this.jeu.aUnEnvahisseur()) {
			List<List<Envahisseur>> envahisseurs = this.jeu.recupererLaListeDeTousLesEnvahisseurs();
			
			//for (int j=0; j < envahisseurs.size(); j++) {
			for (List<Envahisseur> ligne : envahisseurs) {
				//for (int i=0; i < envahisseurs.get(j).size(); i++) {
				for (Envahisseur envahisseur : ligne) {
					this.dessinerUnEnvahisseur(envahisseur, im);
				}
			}
		}
		
		if (this.jeu.aUnMissileEnvahisseur()) {
			List<Missile> missilesEnvahisseurs = this.jeu.recupererLaListeDesMissilesEnvahisseurs();
			
			//for(int i=0; i < missilesEnvahisseurs.size(); i++) {
			for (Missile missileEnvahisseur : missilesEnvahisseurs) {
				this.dessinerUnMissileEnvahisseur(missileEnvahisseur, im);
			}
		}
		
		this.dessinerScore(im);
	}
	
	private void dessinBackground(BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();

		crayon.setColor(Color.black);
		crayon.fillRect(0, 0, Constante.ESPACEJEU_LONGUEUR, Constante.ESPACEJEU_HAUTEUR);			
	}

	private void dessinerUnVaisseau(Vaisseau vaisseau, BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();

		crayon.setColor(Color.gray);
		crayon.fillRect(vaisseau.abscisseLaPlusAGauche(), vaisseau.ordonneeLaPlusBasse(), vaisseau.longueur(),
				vaisseau.hauteur());

	}
	
	private void dessinerUnMissile(Missile missile, BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();

		crayon.setColor(Color.blue);
		crayon.fillRect(missile.abscisseLaPlusAGauche(), missile.ordonneeLaPlusBasse(), missile.longueur(),
				missile.hauteur());

	}
	
	private void dessinerUnEnvahisseur(Envahisseur envahisseur, BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();

		crayon.setColor(Color.red);
		crayon.fillRect(envahisseur.abscisseLaPlusAGauche(), envahisseur.ordonneeLaPlusBasse(), envahisseur.longueur(),
				envahisseur.hauteur());

	}
	
	private void dessinerScore(BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();

		crayon.setColor(Color.black);
		crayon.setFont(new Font("Arial", Font.BOLD, 18));
		crayon.drawString("Score : " + String.valueOf(jeu.score()), Constante.SCORE_ABSCISSE, Constante.SCORE_ORDONNEE);
	}
	
	private void dessinerUnMissileEnvahisseur(Missile missile, BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();

		crayon.setColor(Color.green);
		crayon.fillRect(missile.abscisseLaPlusAGauche(), missile.ordonneeLaPlusHaute(), missile.longueur(),
				missile.hauteur());	
		
	}

}
