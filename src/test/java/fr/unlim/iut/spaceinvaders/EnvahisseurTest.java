package fr.unlim.iut.spaceinvaders;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import fr.unlim.iut.spaceinvaders.model.Collision;
import fr.unlim.iut.spaceinvaders.model.Dimension;
import fr.unlim.iut.spaceinvaders.model.Position;
import fr.unlim.iut.spaceinvaders.model.SpaceInvaders;

public class EnvahisseurTest {
	
	private SpaceInvaders spaceinvaders;

    @Before
    public void initialisation() {
	    spaceinvaders = new SpaceInvaders(15, 10);
    }

	/*
	    * ce test change car les envahisseurs descendent maintenant
	    * 
	   @Test
	   public void test_LaLigneEnvahisseursSeDeplaceCorrectement() {
		for (int i=0; i<3; i++) {
		 spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(i*4,1), 2, 0);
		}
	   
	   	
	   	assertEquals("" + 
	   			"EEE.EEE.EEE....\n" + 
	   			"EEE.EEE.EEE....\n" +
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	   	
	   	spaceinvaders.deplacementAutomatiqueDesEnvahisseurs();
	   	spaceinvaders.deplacementAutomatiqueDesEnvahisseurs();
	   	spaceinvaders.deplacementAutomatiqueDesEnvahisseurs();
	   	
	   	assertEquals("" + 
	   			"..EEE.EEE.EEE..\n" + 
	   			"..EEE.EEE.EEE..\n" +
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	   	
	   }
	   */
	   
	   @Test
	   public void test_eliminationDUnEnvahisseurSIlEstToucheParUnMissile() {

		   spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 2);
		   for (int i=0; i<3; i++) {
			   spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(i*4,1), 2, 0);
		   }
		   
		   spaceinvaders.tirerUnMissile(new Dimension(1,2),2);
		   
		   spaceinvaders.deplacerTousLesMissiles();
		   
	      assertEquals("" + 
	      "EEE.EEE.EEE....\n" + 
		  "EEE.EEE.EEE....\n" +
	      "...............\n" + 
	      "...............\n" + 
	      "........M......\n" + 
	      "........M......\n" + 
	      "...............\n" + 
	      "...............\n" + 
	      ".....VVVVVVV...\n" + 
	      ".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	      
	      spaceinvaders.deplacerTousLesMissiles();
	      spaceinvaders.deplacerTousLesMissiles();
	      
	      assertEquals(true , Collision.detecterCollision(spaceinvaders.recupererUneLigneDEnvahisseur(0).get(2), spaceinvaders.recupererUnMissile(0)));
	      
	      spaceinvaders.controlerMissilesVaisseau();
	      
	      assertEquals("" + 
	    	      "EEE.EEE........\n" + 
	    		  "EEE.EEE........\n" +
	    	      "...............\n" + 
	    	      "...............\n" + 
	    	      "...............\n" + 
	    	      "...............\n" + 
	    	      "...............\n" + 
	    	      "...............\n" + 
	    	      ".....VVVVVVV...\n" + 
	    	      ".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	      
	  }
	   
	   /*
	    * test devenu inutile car testé dans le test suivant
	   @Test
	   public void test_partieNonTermineeSIlResteAuMoinsUnEnvahisseur() {

		   spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 2);
		   for (int i=0; i<3; i++) {
			   spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(i*4,1), 2, i);
		   }
		   
		   spaceinvaders.tirerUnMissile(new Dimension(1,2),2);
		   
		   spaceinvaders.deplacerTousLesMissiles();
		   
	      assertEquals("" + 
	      "EEE.EEE.EEE....\n" + 
		  "EEE.EEE.EEE....\n" +
	      "...............\n" + 
	      "...............\n" + 
	      "........M......\n" + 
	      "........M......\n" + 
	      "...............\n" + 
	      "...............\n" + 
	      ".....VVVVVVV...\n" + 
	      ".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	      
	      spaceinvaders.deplacerTousLesMissiles();
	      spaceinvaders.deplacerTousLesMissiles();
	      
	      assertEquals(true , Collision.detecterCollision(spaceinvaders.recupererUnEnvahisseur(2), spaceinvaders.recupererUnMissile(0)));
	      
	      assertEquals(false , spaceinvaders.etreFini());
	      
	  }
	   */
	   
	   @Test
	   public void test_partieFinieSeulementApresDestructionDeTousLesEnvahisseurs() {

		   spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 2);

		   spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(7,1), 2, 0);
		   spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(11,1), 2, 0);
		   
		   spaceinvaders.tirerUnMissile(new Dimension(1,2),2);
		   
		   spaceinvaders.deplacerTousLesMissiles();
		   
	      assertEquals("" + 
	      ".......EEE.EEE.\n" + 
		  ".......EEE.EEE.\n" +
	      "...............\n" + 
	      "...............\n" + 
	      "........M......\n" + 
	      "........M......\n" + 
	      "...............\n" + 
	      "...............\n" + 
	      ".....VVVVVVV...\n" + 
	      ".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	      
	      spaceinvaders.deplacerTousLesMissiles();
	      spaceinvaders.deplacerTousLesMissiles();
	      
	      assertEquals(true , Collision.detecterCollision(spaceinvaders.recupererUneLigneDEnvahisseur(0).get(0), spaceinvaders.recupererUnMissile(0)));
	      
	      spaceinvaders.controlerMissilesVaisseau();
	      
	      assertEquals(false , spaceinvaders.etreFini());
	      
	      
	      spaceinvaders.deplacerLesEnvahisseurs();
	      spaceinvaders.deplacerLesEnvahisseurs();
	      
	      spaceinvaders.tirerUnMissile(new Dimension(1,2),2);
	      
	      spaceinvaders.deplacerTousLesMissiles();
	      
	      
	      assertEquals("" + 
	    	      ".......EEE.....\n" + 
	    		  ".......EEE.....\n" +
	    	      "...............\n" + 
	    	      "...............\n" + 
	    	      "........M......\n" + 
	    	      "........M......\n" + 
	    	      "...............\n" + 
	    	      "...............\n" + 
	    	      ".....VVVVVVV...\n" + 
	    	      ".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	      
	      
	      spaceinvaders.deplacerTousLesMissiles();
	      spaceinvaders.deplacerTousLesMissiles();
	      
	      assertEquals(true , Collision.detecterCollision(spaceinvaders.recupererUneLigneDEnvahisseur(0).get(0), spaceinvaders.recupererUnMissile(0)));
	      
	      spaceinvaders.controlerMissilesVaisseau();
	      
	      assertEquals(true , spaceinvaders.etreFini());
	      
	      
	  }
	   
	   
	   @Test
	   public void test_detruireUnEnvahisseurAugmenteLesPoints() {

		   spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 2);

		   spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(7,1), 2, 0);
		   spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(11,1), 2, 0);
		   
		   spaceinvaders.tirerUnMissile(new Dimension(1,2),2);
		   
		   spaceinvaders.deplacerTousLesMissiles();
		   
	      assertEquals("" + 
	      ".......EEE.EEE.\n" + 
		  ".......EEE.EEE.\n" +
	      "...............\n" + 
	      "...............\n" + 
	      "........M......\n" + 
	      "........M......\n" + 
	      "...............\n" + 
	      "...............\n" + 
	      ".....VVVVVVV...\n" + 
	      ".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	      
	      
	      assertEquals(0 , spaceinvaders.score());
	      
	      spaceinvaders.deplacerTousLesMissiles();
	      spaceinvaders.deplacerTousLesMissiles();
	      
	      assertEquals(true , Collision.detecterCollision(spaceinvaders.recupererUneLigneDEnvahisseur(0).get(0), spaceinvaders.recupererUnMissile(0)));
	      
	      spaceinvaders.controlerMissilesVaisseau();
	      
	      assertEquals(100 , spaceinvaders.score());
	      
	   }
	   
	   @Test
	   public void test_EnvahisseurPeutTirerMissile() {
	   	spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(0, 1), 0, 0);
	   	spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3, 2), new Position(0, 9), 1);
	   	spaceinvaders.tirerMissileEnvahisseur(new Dimension(1, 2), 2, 0, 0);
	   	
	   	assertEquals("" + 
	   			"EEE............\n" + 
	   			"EEE............\n" +
	   			".W.............\n" + 
	   			".W.............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"VVV............\n" + 
	   			"VVV............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	   }
	   
	   @Test
	   public void test_MissileEnvahisseurSeDeplaceVersLeBas() {
	   	spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(0, 1), 0, 0);
	   	spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3, 2), new Position(0, 9), 1);
	   	spaceinvaders.tirerMissileEnvahisseur(new Dimension(1, 2), 2, 0, 0);
	   	
	   	spaceinvaders.deplacerMissilesEnvahisseurs();
	   	
	   	assertEquals("" + 
	   			"EEE............\n" + 
	   			"EEE............\n" +
	   			"...............\n" + 
	   			"...............\n" + 
	   			".W.............\n" + 
	   			".W.............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"VVV............\n" + 
	   			"VVV............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	   }
	   
	   @Test
	   public void test_finPartieSiUnMissileEnvahisseurToucheLeVaisseau() {
	   	spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(0, 1), 0, 0);
	   	spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3, 2), new Position(0, 9), 1);
	   	spaceinvaders.tirerMissileEnvahisseur(new Dimension(1, 2), 2, 0, 0);
	   	
	   	spaceinvaders.deplacerMissilesEnvahisseurs();
	   	
	   	spaceinvaders.deplacerMissilesEnvahisseurs();
	   	spaceinvaders.deplacerMissilesEnvahisseurs();
	   	
	   	assertEquals(true , Collision.detecterCollision(spaceinvaders.recupererUnMissileEnvahisseur(0), spaceinvaders.recupererVaisseau()));
	    
	   	spaceinvaders.controlerMissilesEnvahisseurs();
	   	
	   	assertEquals(true , spaceinvaders.etreFini());
	   	
	   }
	   
	   @Test
	   public void test_MissileEnvahisseurNeSeSuperposePas() {
	   	spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(0, 1), 0, 0);
	   	spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3, 2), new Position(0, 9), 1);
	   	spaceinvaders.tirerMissileEnvahisseur(new Dimension(1, 2), 1, 0, 0);
	   	
	   	spaceinvaders.deplacerMissilesEnvahisseurs();
	   	
	   	spaceinvaders.tirerMissileEnvahisseur(new Dimension(1, 2), 1, 0, 0);
	   	
	   	assertEquals("" + 
	   			"EEE............\n" + 
	   			"EEE............\n" +
	   			"...............\n" + 
	   			".W.............\n" + 
	   			".W.............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"VVV............\n" + 
	   			"VVV............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	   	
	   }
	   
	   @Test
	   public void test_MissileEnvahisseurDisparaitUneFoisArriveEnBas() {
	   	spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(0, 1), 0, 0);
	   	spaceinvaders.tirerMissileEnvahisseur(new Dimension(1, 2), 1, 0, 0);
	   	
	   	for (int i=0; i < 7; i++)
	   		spaceinvaders.deplacerMissilesEnvahisseurs();
	   	
	   	assertEquals("" + 
	   			"EEE............\n" + 
	   			"EEE............\n" +
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	   	
	   }
	   
	   @Test
	   public void test_EnvahisseurPeutDescendre() {
	   	spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(0, 1), 0, 0);
	   	spaceinvaders.tirerMissileEnvahisseur(new Dimension(1, 2), 1, 0, 0);
	   	
	   	for (int i=0; i < 7; i++)
	   		spaceinvaders.deplacerMissilesEnvahisseurs();
	   	
	   	assertEquals("" + 
	   			"EEE............\n" + 
	   			"EEE............\n" +
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	   	
	   	spaceinvaders.recupererUneLigneDEnvahisseur(0).get(0).descendreAUneCertaineVitesse(1);
	   	
	   	assertEquals("" + 
	   			"...............\n" + 
	   			"EEE............\n" +
	   			"EEE............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	   	
	   }
	   
	   @Test
	   public void test_LaLigneEnvahisseursPeutDescendre() {
		for (int i=0; i<3; i++) {
		 spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(i*4,1), 2, 0);
		}
	   
	   	
	   	assertEquals("" + 
	   			"EEE.EEE.EEE....\n" + 
	   			"EEE.EEE.EEE....\n" +
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	   	
	   	for (int i=0; i < 3; i++)
	   		spaceinvaders.recupererUneLigneDEnvahisseur(0).get(i).descendreAUneCertaineVitesse(1);
	   	
	   	assertEquals("" + 
	   			"...............\n" + 
	   			"EEE.EEE.EEE....\n" +
	   			"EEE.EEE.EEE....\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" + 
	   			"...............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	   
	   }
	   
	   @Test
	   public void test_FinDeLaPartieQuandLesEnvahisseursTouchentLeVaisseau() {   	
		spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(0, 1), 0, 0);
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3, 2), new Position(0, 9), 1);
	   
	   	
		assertEquals("" + 
				"EEE............\n" + 
				"EEE............\n" +
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"VVV............\n" + 
				"VVV............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	   	
		
	   	spaceinvaders.recupererUneLigneDEnvahisseur(0).get(0).descendreAUneCertaineVitesse(6);
	   	
	   	assertEquals("" + 
				"...............\n" + 
				"...............\n" +
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"EEE............\n" + 
				"EEE............\n" + 
				"VVV............\n" + 
				"VVV............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	   	
	   	
	   	spaceinvaders.recupererUneLigneDEnvahisseur(0).get(0).descendreAUneCertaineVitesse(1);
	   	
	   	assertEquals(true, Collision.detecterCollision(spaceinvaders.recupererVaisseau(), spaceinvaders.recupererUneLigneDEnvahisseur(0).get(0)));
	   	
	   	assertEquals(true, spaceinvaders.etreFini());
	   	
	   	
	   }
	   

}
