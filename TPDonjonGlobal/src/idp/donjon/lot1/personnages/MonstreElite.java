
/*	
 * Cette classe sert � d�finir le comportement du Monstre �lite.
 * Ce Monstre est caract�ris� par la salle et le donjon qui le contiennent.
 */

package idp.donjon.lot1.personnages;


import idp.donjon.lot1.donjon.Donjon;
import idp.donjon.lot1.donjon.Salle;

public class MonstreElite extends Monstre {


	public MonstreElite(Salle salle, Donjon d) {
		super(salle, d);
		pvMax = getPvMax() * 2;
		setPvInitial();
		nom = getNom() + " elite";
	}

}
