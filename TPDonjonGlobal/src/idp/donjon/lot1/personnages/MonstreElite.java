
/*	
 * Cette classe sert à définir le comportement du Monstre élite.
 * Ce Monstre est caractérisé par la salle et le donjon qui le contiennent.
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
