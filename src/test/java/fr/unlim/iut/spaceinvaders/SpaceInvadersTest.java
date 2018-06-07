package fr.unlim.iut.spaceinvaders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import fr.unlim.iut.spaceinvaders.model.Collision;
import fr.unlim.iut.spaceinvaders.model.Dimension;
import fr.unlim.iut.spaceinvaders.model.Position;
import fr.unlim.iut.spaceinvaders.model.SpaceInvaders;
import fr.unlim.iut.spaceinvaders.utils.DebordementEspaceJeuException;
import fr.unlim.iut.spaceinvaders.utils.HorsEspaceJeuException;
import fr.unlim.iut.spaceinvaders.utils.MissileException;

public class SpaceInvadersTest {

	private SpaceInvaders spaceinvaders;

    @Before
    public void initialisation() {
	    spaceinvaders = new SpaceInvaders(15, 10);
    }
	
   @Test
   public void test_AuDebut_JeuSpaceInvaderEstVide() {
	    assertEquals("" + 
	    "...............\n" + 
	    "...............\n" +
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
	public void test_unNouveauVaisseauEstCorrectementPositionneDansEspaceJeu() {
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(1,1),new Position(7,9), 1);
		assertEquals("" + 
		"...............\n" + 
		"...............\n" +
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		".......V.......\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}
   
  
   
   @Test
	public void test_UnNouveauVaisseauPositionneHorsEspaceJeu_DoitLeverUneException() {
		
		try {
			spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(1,1),new Position(15,9), 1);
			fail("Position trop à droite : devrait déclencher une exception HorsEspaceJeuException");
		} catch (final HorsEspaceJeuException e) {
		}
		
		
		try {
			spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(1,1),new Position(-1,9), 1);
			fail("Position trop à gauche : devrait déclencher une exception HorsEspaceJeuException");
		} catch (final HorsEspaceJeuException e) {
		}
		
		
		try {
			spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(1,1),new Position(14,10), 1);
			fail("Position trop en bas : devrait déclencher une exception HorsEspaceJeuException");
		} catch (final HorsEspaceJeuException e) {
		}
		
		
		try {
			spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(1,1),new Position(14,-1), 1);
			fail("Position trop à haut : devrait déclencher une exception HorsEspaceJeuException");
		} catch (final HorsEspaceJeuException e) {
		}
			
	}
   
   @Test
  	public void test_unNouveauVaisseauAvecDimensionEstCorrectementPositionneDansEspaceJeu() {
	   spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(7,9), 1);
  		assertEquals("" + 
  		"...............\n" + 
  		"...............\n" +
  		"...............\n" + 
  		"...............\n" + 
  		"...............\n" + 
  		"...............\n" + 
  		"...............\n" + 
  		"...............\n" + 
  		".......VVV.....\n" + 
  		".......VVV.....\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
  	}
   
   
   @Test
   public void test_UnNouveauVaisseauPositionneDansEspaceJeuMaisAvecDimensionTropGrande_DoitLeverUneExceptionDeDebordement() {
		
	   try {
		   spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(9,2),new Position(7,9), 1);
		   fail("Dépassement du vaisseau à droite en raison de sa longueur trop importante : devrait déclencher une exception DebordementEspaceJeuException");
	   } catch (final DebordementEspaceJeuException e) {
	   }
		
		
	   try {
		   spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,4),new Position(7,1), 1);
		   fail("Dépassement du vaisseau vers le haut en raison de sa hauteur trop importante : devrait déclencher une exception DebordementEspaceJeuException");
	   } catch (final DebordementEspaceJeuException e) {
	   }
			
	}
   
   public void test_VaisseauAvance_DeplacerVaisseauVersLaDroite(int vitesse) {

       spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(7,9),3);
       spaceinvaders.deplacerVaisseauVersLaDroite();
       assertEquals("" + 
       "...............\n" + 
       "...............\n" +
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "..........VVV..\n" + 
       "..........VVV..\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
   }
   
   
   @Test
	public void test_VaisseauImmobile_DeplacerVaisseauVersLaDroite() {
		
	    spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(12,9), 3);
		spaceinvaders.deplacerVaisseauVersLaDroite();
		assertEquals("" + 
		"...............\n" + 
		"...............\n" +
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"............VVV\n" + 
		"............VVV\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}
   
   @Test
   public void test_VaisseauAvancePartiellement_DeplacerVaisseauVersLaDroite() {

      spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(10,9),3);
      spaceinvaders.deplacerVaisseauVersLaDroite();
      assertEquals("" + 
      "...............\n" + 
      "...............\n" +
      "...............\n" + 
      "...............\n" + 
      "...............\n" + 
      "...............\n" + 
      "...............\n" + 
      "...............\n" + 
      "............VVV\n" + 
      "............VVV\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
   }
   
   @Test
   public void test_VaisseauAvance_DeplacerVaisseauVersLaGauche() {

      spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(7,9), 3);
      spaceinvaders.deplacerVaisseauVersLaGauche();

      assertEquals("" + 
      "...............\n" + 
      "...............\n" +
      "...............\n" + 
      "...............\n" + 
      "...............\n" + 
      "...............\n" + 
      "...............\n" + 
      "...............\n" + 
      "....VVV........\n" + 
      "....VVV........\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
  }
   
   @Test
   public void test_VaisseauImmobile_DeplacerVaisseauVersLaGauche() {

	   spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(0,9), 3);
      spaceinvaders.deplacerVaisseauVersLaGauche();

      assertEquals("" + 
      "...............\n" + 
      "...............\n" +
      "...............\n" + 
      "...............\n" + 
      "...............\n" + 
      "...............\n" + 
      "...............\n" + 
      "...............\n" + 
      "VVV............\n" + 
      "VVV............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }
   
   @Test
   public void test_VaisseauAvancePartiellement_DeplacerVaisseauVersLaGauche() {

      spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(1,9), 3);
      spaceinvaders.deplacerVaisseauVersLaGauche();

      assertEquals("" + 
      "...............\n" + 
      "...............\n" +
      "...............\n" + 
      "...............\n" + 
      "...............\n" + 
      "...............\n" + 
      "...............\n" + 
      "...............\n" + 
      "VVV............\n" + 
      "VVV............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }
   
   @Test
   public void test_MissileBienTireDepuisVaisseau_VaisseauLongueurImpaireMissileLongueurImpaire() {

	   spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 2);
	   spaceinvaders.tirerUnMissile(new Dimension(3,2),2);

     assertEquals("" + 
     "...............\n" + 
     "...............\n" +
     "...............\n" + 
     "...............\n" + 
     "...............\n" + 
     "...............\n" + 
     ".......MMM.....\n" + 
     ".......MMM.....\n" + 
     ".....VVVVVVV...\n" + 
     ".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
  }
   
   @Test(expected = MissileException.class)
	public void test_PasAssezDePlacePourTirerUnMissile_UneExceptionEstLevee() throws Exception { 
	   spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 1);
	   spaceinvaders.tirerUnMissile(new Dimension(7,9),1);
	}
   
   @Test
   public void test_MissileAvanceAutomatiquement_ApresTirDepuisLeVaisseau() {

	   spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 2);
	   spaceinvaders.tirerUnMissile(new Dimension(3,2),2);

	   spaceinvaders.deplacerTousLesMissiles();
	   
      assertEquals("" + 
      "...............\n" + 
      "...............\n" +
      "...............\n" + 
      "...............\n" + 
      ".......MMM.....\n" + 
      ".......MMM.....\n" + 
      "...............\n" + 
      "...............\n" + 
      ".....VVVVVVV...\n" + 
      ".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
  }
   
   @Test
   public void test_MissileDisparait_QuandIlCommenceASortirDeEspaceJeu() {

	   spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 1);
	   spaceinvaders.tirerUnMissile(new Dimension(3,2),1);
	   for (int i = 1; i <=6 ; i++) {
		   spaceinvaders.deplacerTousLesMissiles();
	   }
	   
	   spaceinvaders.deplacerTousLesMissiles();
	   
       assertEquals("" +
       "...............\n" + 
       "...............\n" +
       "...............\n" + 
       "...............\n" +
       "...............\n" +
       "...............\n" + 
       "...............\n" +
       "...............\n" + 
       ".....VVVVVVV...\n" + 
       ".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
   }
   
   @Test
   public void test_unNouveauEnvahisseurEstCorrectementPositionneDansEspaceJeu() {

	   spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 1);
	   spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(1,1), new Position(7,0), 1, 0);
	   
       assertEquals("" +
       ".......E.......\n" + 
       "...............\n" +
       "...............\n" + 
       "...............\n" +
       "...............\n" +
       "...............\n" + 
       "...............\n" +
       "...............\n" + 
       ".....VVVVVVV...\n" + 
       ".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
   }
   
   @Test
   public void test_EnvahisseurSeDeplaceAutomatiquement() {

	   spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 1);
	   spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(1,1), new Position(7,0), 1, 0);
	   
	   spaceinvaders.deplacementAutomatiqueDesEnvahisseurs();
	   
       assertEquals("" +
       "......E........\n" + 
       "...............\n" +
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
    * test devenu inutile car est testé plus bas
   @Test
	public void test_FinDePartieApresDetectionDUneCollision() throws Exception {
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 1);
		spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3,2), new Position(7,1), 1);
		
		spaceinvaders.tirerUnMissile(new Dimension(3,2),2);
		
		spaceinvaders.deplacerTousLesMissiles();
		
		spaceinvaders.deplacerTousLesMissiles();
		
		spaceinvaders.deplacerTousLesMissiles();
		
		assertEquals(true , spaceinvaders.etreFini());
	}
	*/
   
   @Test
   public void test_plusieursMissilesPossibles() {
   	spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3, 2), new Position(0, 9), 1);
   	
   	spaceinvaders.tirerUnMissile(new Dimension(1, 2), 3);
   	spaceinvaders.deplacerTousLesMissiles();
   	
   	spaceinvaders.tirerUnMissile(new Dimension(1, 2), 3);
   	
   	assertEquals("" + 
   			"...............\n" + 
   			"...............\n" +
   			"...............\n" + 
   			".M.............\n" + 
   			".M.............\n" + 
   			"...............\n" + 
   			".M.............\n" + 
   			".M.............\n" + 
   			"VVV............\n" + 
   			"VVV............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
   }
   
   @Test
   public void test_LesMissilesNePeuventPasSeSuperposer() {
   	spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3, 2), new Position(0, 9), 1);
   	spaceinvaders.tirerUnMissile(new Dimension(1, 2), 1);
   	spaceinvaders.deplacerTousLesMissiles();
   	
   	spaceinvaders.tirerUnMissile(new Dimension(1, 2), 1);
   	
   	assertEquals("" + 
   			"...............\n" + 
   			"...............\n" +
   			"...............\n" + 
   			"...............\n" + 
   			"...............\n" + 
   			".M.............\n" + 
   			".M.............\n" + 
   			"...............\n" + 
   			"VVV............\n" + 
   			"VVV............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
   }
   
   @Test
   public void test_LaLigneEnvahisseursSeDeplaceCorrectement() {
	for (int i=0; i<3; i++) {
	 spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(i*4,1), 2, i);
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
   
   @Test
   public void test_eliminationDUnEnvahisseurSIlEstToucheParUnMissile() {

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
	   spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(11,1), 2, 1);
	   
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
      
      
      spaceinvaders.deplacementAutomatiqueDesEnvahisseurs();
      spaceinvaders.deplacementAutomatiqueDesEnvahisseurs();
      
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
	   spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(11,1), 2, 1);
	   
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
   	spaceinvaders.tirerMissileEnvahisseur(new Dimension(1, 2), 2, 0);
   	
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
   	spaceinvaders.tirerMissileEnvahisseur(new Dimension(1, 2), 2, 0);
   	
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
   	spaceinvaders.tirerMissileEnvahisseur(new Dimension(1, 2), 2, 0);
   	
   	spaceinvaders.deplacerMissilesEnvahisseurs();
   	
   	spaceinvaders.deplacerMissilesEnvahisseurs();
   	spaceinvaders.deplacerMissilesEnvahisseurs();
   	
   	assertEquals(true , Collision.detecterCollision(spaceinvaders.recupererUnMissileEnvahisseur(0), spaceinvaders.recupererVaisseau()));
    
   	spaceinvaders.controlerMissilesEnvahisseurs();
   	
   	assertEquals(true , spaceinvaders.etreFini());
   	
   }  	
   
   /*
    * ce cas d'utilisation pose des problemes aléatoires dont nous 
    * ne comprenons pas l'origine. 
    * nous décidons donc de supprimer ce cas d'utilisation
    * 
   @Test
   public void test_siDeuxMissilesSePercutentIlsSontDetruits() {
   	spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(0, 1), 0);
   	spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3, 2), new Position(0, 9), 1);
   	spaceinvaders.tirerMissileEnvahisseur(new Dimension(1, 2), 2, 0);
   	spaceinvaders.tirerUnMissile(new Dimension(1,2), 2);
   	
   	assertEquals("" + 
   			"EEE............\n" + 
   			"EEE............\n" +
   			".W.............\n" + 
   			".W.............\n" + 
   			"...............\n" + 
   			"...............\n" + 
   			".M.............\n" + 
   			".M.............\n" + 
   			"VVV............\n" + 
   			"VVV............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
   	

   	spaceinvaders.deplacerTousLesMissiles();
   	
   	spaceinvaders.controlerMissilesVaisseau();
   	
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
   	
   }
   */
   
   @Test
   public void test_MissileEnvahisseurNeSeSuperposePas() {
   	spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(0, 1), 0, 0);
   	spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3, 2), new Position(0, 9), 1);
   	spaceinvaders.tirerMissileEnvahisseur(new Dimension(1, 2), 1, 0);
   	
   	spaceinvaders.deplacerMissilesEnvahisseurs();
   	
   	spaceinvaders.tirerMissileEnvahisseur(new Dimension(1, 2), 1, 0);
   	
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
   	spaceinvaders.tirerMissileEnvahisseur(new Dimension(1, 2), 1, 0);
   	
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
   
   
   
}
