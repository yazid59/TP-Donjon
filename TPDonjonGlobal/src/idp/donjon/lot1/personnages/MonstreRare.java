
/*	
 * Cette classe sert à définir le comportement du Monstre rare.
 * Ce Monstre est caractérisé par la salle et le donjon qui le contiennent.
 */
package idp.donjon.lot1.personnages;

import idp.donjon.lot1.donjon.Donjon;
import idp.donjon.lot1.donjon.Salle;

public class MonstreRare extends Monstre {

	public MonstreRare(Salle salle, Donjon d) {
		super(salle, d);
		setOr(getOr() * 2);
		nom = getNom() + " rare";
	}
}
