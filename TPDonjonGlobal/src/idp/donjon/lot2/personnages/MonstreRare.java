
/*	
 * Cette classe sert � d�finir le comportement du Monstre rare.
 * Ce Monstre est caract�ris� par la salle et le donjon qui le contiennent.
 */
package idp.donjon.lot2.personnages;

import idp.donjon.lot2.donjon.Donjon;
import idp.donjon.lot2.donjon.Salle;

public class MonstreRare extends Monstre {

	public MonstreRare(Salle salle, Donjon d) {
		super(salle, d);
		setOr(getOr() * 2);
		nom = getNom() + " rare";
	}
}
