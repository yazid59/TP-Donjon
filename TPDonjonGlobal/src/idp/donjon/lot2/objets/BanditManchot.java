
/*	
 * Cette classe sert à définir le comportement de l'objet Bandit Manchot
 */

package idp.donjon.lot2.objets;

import idp.donjon.lot2.donjon.Salle;
import idp.donjon.lot2.personnages.Joueur;
import idp.donjon.lot2.utils.MyRandom;

public class BanditManchot extends AbstractObjet {

	public BanditManchot(Salle salle) {
		super(salle);
	}

	public void utiliser() {
		Joueur j = Joueur.getJoueur(salle.getListePersonnages());

		if (j.getOr() >= puissance) {
			AbstractObjet obj;
			int chanceTypeObjet = MyRandom.rnd.nextInt(N_TYPES_OBJET - 1);

			if (chanceTypeObjet == 0) {
				obj = new BourseDOr(salle);
			} else if (chanceTypeObjet == 1) {
				obj = new PotionForce(salle);
			} else {
				obj = new PotionSoin(salle);
			}

			j.setOr(j.getOr() - puissance);
			System.out.println("Vous avez payé " + puissance + " pièces d'or pour utiliser ce " + this + ".");
			System.out.println("L'objet généré est " + obj + ".");

			obj.utiliser();

		} else {
			System.out.println("Vous n'avez pas assez d'or pour utiliser ce " + this + ", il s'est enfui !");
		}
		salle.getListeObjet().remove(this);
	}

	public String toString() {
		return "Bandit manchot";
	}

}
