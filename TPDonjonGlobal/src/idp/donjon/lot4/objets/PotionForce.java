
/*	
 * Cette classe sert à définir le comportement de l'objet Potion de force
 */

package idp.donjon.lot4.objets;

import idp.donjon.lot4.donjon.Salle;
import idp.donjon.lot4.personnages.Joueur;

public class PotionForce extends AbstractObjet {
	private final int PUISSANCE_POT_FORCE = puissance / 5;

	public PotionForce(Salle salle) {
		super(salle);
	}

	public void utiliser() {
		Joueur j = Joueur.getJoueur(salle.getListePersonnages());

		j.setForce(j.getForce() + PUISSANCE_POT_FORCE);

		apresUtilisation();
	}

	public String toString() {
		return "Potion de force";
	}
}
