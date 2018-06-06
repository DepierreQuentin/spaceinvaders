package fr.unlim.iut.spaceinvaders;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import fr.unlim.iut.spaceinvaders.model.Collision;
import fr.unlim.iut.spaceinvaders.model.Dimension;
import fr.unlim.iut.spaceinvaders.model.Position;
import fr.unlim.iut.spaceinvaders.model.SpaceInvaders;

public class CollisionTest {

	private SpaceInvaders spaceinvaders;
	
	@Before
    public void initialisation() {
	    spaceinvaders = new SpaceInvaders(15, 10);
    }
	
	@Test
	public void test_CollisionEntreDeuxSprite() throws Exception {
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 1);
		spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3,2), new Position(7,1), 1);
		
		spaceinvaders.tirerUnMissile(new Dimension(3,2),2);
		
		spaceinvaders.deplacerTousLesMissiles();
		
		assertEquals("" + 
			      ".......EEE.....\n" + 
			      ".......EEE.....\n" +
			      "...............\n" + 
			      "...............\n" + 
			      ".......MMM.....\n" + 
			      ".......MMM.....\n" + 
			      "...............\n" + 
			      "...............\n" + 
			      ".....VVVVVVV...\n" + 
			      ".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
		
		
		spaceinvaders.deplacerTousLesMissiles();
		
		assertEquals("" + 
			      ".......EEE.....\n" + 
			      ".......EEE.....\n" +
			      ".......MMM.....\n" + 
			      ".......MMM.....\n" + 
			      "...............\n" + 
			      "...............\n" + 
			      "...............\n" + 
			      "...............\n" + 
			      ".....VVVVVVV...\n" + 
			      ".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
		
		spaceinvaders.deplacerTousLesMissiles();
		
		assertEquals(true , Collision.detecterCollision(spaceinvaders.recupererUnMissile(0), spaceinvaders.recupererEnvahisseur()));
	}
	
	
}
