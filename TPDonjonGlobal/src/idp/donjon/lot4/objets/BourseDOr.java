
/*	
 * Cette classe sert à définir le comportement de l'objet Bourse D'or
 */

package idp.donjon.lot4.objets;

import idp.donjon.lot4.donjon.Salle;
import idp.donjon.lot4.personnages.Joueur;

public class BourseDOr extends AbstractObjet {

	public BourseDOr(Salle salle) {
		super(salle);
	}

	public void utiliser() {
		Joueur j = Joueur.getJoueur(salle.getListePersonnages());
		j.setOr(j.getOr() + puissance);

		apresUtilisation();
	}

	public String toString() {
		return "Bourse d'or";
	}
}
