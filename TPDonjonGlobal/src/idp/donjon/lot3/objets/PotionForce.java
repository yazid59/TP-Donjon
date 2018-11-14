
/*	
 * Cette classe sert à définir le comportement de l'objet Potion de force
 */

package idp.donjon.lot3.objets;

import idp.donjon.lot3.donjon.Salle;
import idp.donjon.lot3.personnages.Joueur;

public class PotionForce extends AbstractObjet {
	private final int PUISSANCE_POT_FORCE = puissance / 5;

	public PotionForce(Salle salle) {
		super(salle);
	}

	public void utiliser() {
		Joueur j = Joueur.getJoueur(salle.getListePersonnages());

		j.setForce(j.getForce() + PUISSANCE_POT_FORCE);

		System.out.println("Vous avez utilisé " + this + ".");
		System.out.println("Vous avez maintenant " + j.getForce() + " force.");
		salle.removeObjet(this);
	}

	public String toString() {
		return "Potion de force";
	}
}
