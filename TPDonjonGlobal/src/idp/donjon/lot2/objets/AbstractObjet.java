
/*	
 * Cette classe abstraite sert à définir un objet en général.
 * Un objet est caractérisé par la salle qui le contient et sa puissance d'action.
 */
package idp.donjon.lot2.objets;

import java.util.Collection;

import idp.donjon.lot2.donjon.Salle;
import idp.donjon.lot2.utils.MyRandom;

public abstract class AbstractObjet {
	protected static final int N_TYPES_OBJET = 4;
	protected int puissance;
	protected Salle salle;

	public AbstractObjet(Salle salle) {

		this.salle = salle;

		final int PUISSANCE_MAX = 15;
		puissance = 1 + (1 * MyRandom.rnd.nextInt(PUISSANCE_MAX));

	}

	public abstract void utiliser();

	public static boolean isObjet(Collection<AbstractObjet> listeO) {
		return listeO.size()>0;
	}
	
	public int getPuissance() {
		return puissance;
	}

	public void setPuissance(int puissance) {
		this.puissance = puissance;
	}

	public Salle getSalle() {
		return salle;
	}

	public void setSalle(Salle salle) {
		this.salle = salle;
	}

	public abstract String toString();
}
