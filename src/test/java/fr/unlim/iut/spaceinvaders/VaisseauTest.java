package fr.unlim.iut.spaceinvaders;

import org.junit.Test;

import fr.unlim.iut.spaceinvaders.model.Dimension;
import fr.unlim.iut.spaceinvaders.model.Position;
import fr.unlim.iut.spaceinvaders.model.Vaisseau;
import fr.unlim.iut.spaceinvaders.utils.MissileException;

public class VaisseauTest {
	
	@Test(expected = MissileException.class)
	public void test_LongueurMissileSuperieureALongueurVaisseau_UneExceptionEstLevee() throws Exception {
		Vaisseau vaisseau = new Vaisseau(new Dimension(5,2),new Position(5,9), 1);
		vaisseau.tirerUnMissile(new Dimension(7,2),1);
	}
	
	
	
}
