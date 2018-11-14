
/*	
 * Cette classe sert à définir le comportement de l'objet Potion de soins
 */

package idp.donjon.lot3.objets;

import idp.donjon.lot3.donjon.Salle;
import idp.donjon.lot3.personnages.Joueur;

public class PotionSoin extends AbstractObjet {

	public PotionSoin(Salle salle) {
		super(salle);
	}

	public void utiliser() {

		Joueur j = Joueur.getJoueur(salle.getListePersonnages());
		if (j.getPv() == j.getPvMax()) {
			System.out.println("les PV sont déjà au max !");
		} else {
			j.setPv(j.getPv() + puissance);

			System.out.println("Vous avez utilisé " + this + ".");
			System.out.println("Vous avez maintenant " + j.getPv() + " points de vie.");
		}

		salle.removeObjet(this);
	}

	public String toString() {
		return "Potion de soins";
	}

}
