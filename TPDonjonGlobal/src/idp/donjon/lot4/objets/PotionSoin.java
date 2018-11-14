
/*	
 * Cette classe sert à définir le comportement de l'objet Potion de soins
 */

package idp.donjon.lot4.objets;

import idp.donjon.lot4.donjon.Salle;
import idp.donjon.lot4.personnages.Joueur;

public class PotionSoin extends AbstractObjet {

	public PotionSoin(Salle salle) {
		super(salle);
	}

	public void utiliser() {

		Joueur j = Joueur.getJoueur(salle.getListePersonnages());
		if (j.getPv() == j.getPvMax()) {
		} else {
			j.setPv(j.getPv() + puissance);

		}
		apresUtilisation();
	}

	public String toString() {
		return "Potion de soins";
	}

}
