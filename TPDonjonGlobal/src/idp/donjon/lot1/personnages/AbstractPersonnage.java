
/*	
 * Cette classe abstraite sert à définir le comportement des personnages en général.
 * Un personnage est caractérisé par ses points de vie, sa force, l'or qu'il porte et 
 * le tour qui lui donne le droit ou non d'agir.
 */
package idp.donjon.lot1.personnages;

import java.util.Collection;

import idp.donjon.lot1.donjon.Donjon;
import idp.donjon.lot1.donjon.Salle;

public abstract class AbstractPersonnage {

	protected String nom="";
	protected int pv;
	protected int pvMax;
	protected int force;
	protected int or;
	protected boolean tour;

	protected Salle salle;
	protected Donjon d;

	private AbstractPersonnage cible;

	public AbstractPersonnage(Salle salle, Donjon d) {
		setSalle(salle);
		this.d = d;
	}

	public abstract void action();

	public abstract AbstractPersonnage choixCible();

	public abstract void apresAttaque();

	public void attaquer(AbstractPersonnage cible) {
		if (cible != null) {
			cible.setPv(cible.getPv() - getForce());
			if (getCible() != null) {
				apresAttaque();
				setTour(false);
				getCible().setTour(true);
			}
		}

	}

	public static Joueur getJoueur(Collection<AbstractPersonnage> listeP) {
		return (Joueur) listeP.stream().filter(x -> x.hashCode() == x.getOr()).findFirst().get();
	}

	public static boolean isPlayer(Collection<AbstractPersonnage> listeP) {
		return listeP.stream().anyMatch(x -> x.hashCode() == x.getOr());
	}

	public static boolean isMonster(Collection<AbstractPersonnage> listeP) {
		return listeP.stream().anyMatch(x -> x.hashCode() != x.getOr());
	}

	public static int nMonster(Collection<AbstractPersonnage> listeP) {
		return (int) listeP.stream().filter(x -> x.hashCode() != x.getOr()).count();

	}

	public abstract String toString();

	public void mort(AbstractPersonnage p) {
		Joueur j = getJoueur(salle.getListePersonnages());

		if (p != j) {
			System.out.println(p + " est mort.\n" + p + " a laché " + p.getOr() + " pièces d'or.");
			getJoueur(salle.getListePersonnages()).setOr(getJoueur(salle.getListePersonnages()).getOr() + p.getOr());
			salle.getListePersonnages().remove(p);
		} else {
			System.err.println("Vous êtes mort, le donjon vous a eu.");
			quitter();
		}
	}


	public void quitter() {
			
			System.exit(0);
	}


	public int getPv() {
		if (pv == 0) {
			mort(this);
		}
		return pv;
	}

	public void setPv(int pv) {
		if (pv > pvMax) {
			this.pv = pvMax;
		} else if (pv < 0) {
			this.pv = 0;
		} else {
			this.pv = pv;
		}
	}

	public int getPvMax() {
		return pvMax;
	}

	public void setPvMax(int pvMax) {
		this.pvMax = pvMax;
	}

	public int getForce() {
		return force;
	}

	public void setForce(int force) {
		this.force = force;
	}

	public int getOr() {
		return or;
	}

	public void setOr(int or) {
		this.or = or;
	}

	public boolean isTour() {
		return tour;
	}

	public void setTour(boolean tour) {
		this.tour = tour;
		if (tour) {
			action();
		}
	}

	public Salle getSalle() {
		return salle;
	}

	public void setSalle(Salle salle) {
		if (AbstractPersonnage.isPlayer(salle.getListePersonnages()) && salle.isGagnante()) {
			System.out.println("Félicitations, vous avez gagné !");
			quitter();

		}
		salle.nommerCouloirs();
		this.salle = salle;
	}

	public AbstractPersonnage getCible() {
		if (cible.getPv() == 0) {
			return null;
		}
		return cible;
	}

	public void setCible(AbstractPersonnage cible) {
		this.cible = cible;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom=Character.toUpperCase(nom.charAt(0)) + nom.substring(1);
	}
}
