
/*	
 * Cette classe sert � d�finir le comportement de l'objet Bourse D'or
 */

package idp.donjon.lot2.objets;

import idp.donjon.lot2.donjon.Salle;
import idp.donjon.lot2.personnages.Joueur;

public class BourseDOr extends AbstractObjet {

	public BourseDOr(Salle salle) {
		super(salle);
	}

	public void utiliser() {
		Joueur j = Joueur.getJoueur(salle.getListePersonnages());
		j.setOr(j.getOr() + puissance);

		salle.removeObjet(this);
		System.out.println("Vous avez utilis� " + this + ".");
		System.out.println("Vous avez maintenant " + j.getOr() + " pi�ces d'or.");
	}

	public String toString() {
		return "Bourse d'or";
	}
}
