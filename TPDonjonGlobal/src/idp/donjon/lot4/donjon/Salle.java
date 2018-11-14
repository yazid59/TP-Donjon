
/*
 * Une salle représente une portion du donjon où se passent les actions liées au contenu de celle-ci.
 * Elle est caractérisée par une ID unique, des coordonnées X et Y de cette salle,
 * une liste de couloirs disponibles représentant les direction possibles,
 * une liste de personnages représentant son contenu en terme de personnages(joueurs, monstres),
 * une liste d'objets représenant son contenu en terme d'objets.
 * 
 * Une salle peut être gagnante auquel cas, seul le fait de s'y rendre provoque la victoire du joueur.
 */

package idp.donjon.lot4.donjon;

import java.util.ArrayList;
import java.util.List;

import idp.donjon.lot4.objets.*;
import idp.donjon.lot4.personnages.*;
import idp.donjon.lot4.utils.MyRandom;

public class Salle {

	public final int ID;
	public final int X;
	public final int Y;

	private Donjon donjon;
	private boolean gagnante;

	private List<Couloir> couloirs;
	private List<AbstractPersonnage> listePersonnages;
	private List<AbstractObjet> listeObjet;

	public Salle(Donjon d, int X, int Y, int ID) {
		this.ID = ID;
		this.X = X;
		this.Y = Y;

		gagnante = false;
		listePersonnages = new ArrayList<>();
		listeObjet = new ArrayList<>();
		donjon = d;

		couloirs = new ArrayList<>();
		generationObjets();
		generationMonstres();
	}

	public void nommerCouloirs() {
		for (Couloir c : couloirs) {
			if (c.getSalle2().X == X + 1 && c.getSalle2().Y == Y) {
				c.setNomRelatif("EST");
			} else if (c.getSalle2().X == X - 1 && c.getSalle2().Y == Y) {
				c.setNomRelatif("OUEST");
			} else if (c.getSalle2().X == X && c.getSalle2().Y == Y + 1) {
				c.setNomRelatif("NORD");
			} else {
				c.setNomRelatif("SUD");
			}
		}
	}

	public final void generationObjets() {
		final int CHANCE = 100;
		final int CHANCE_0 = 55;
		final int CHANCE_1 = 75;
		final int CHANCE_2 = 93;
		int chance = MyRandom.rnd.nextInt(CHANCE);

		int nObjets = 0;

		if (chance >= CHANCE_0 && chance < CHANCE_1) {
			nObjets = 1;
		} else if (chance >= CHANCE_1 && chance < CHANCE_2) {
			nObjets = 2;
		} else if (chance >= CHANCE_2) {
			nObjets = 3;
		}

		for (int i = 0; i < nObjets; i++) {

			AbstractObjet obj;

			final int N_CHANCES = 15;
			final int CHANCE_BOURSE = N_CHANCES / 5;
			final int CHANCE_POT_SOIN = CHANCE_BOURSE + N_CHANCES / 3;
			final int CHANCE_POT_FORCE = CHANCE_POT_SOIN * 2;
			int chanceTypeObjet = MyRandom.rnd.nextInt(N_CHANCES);

			if (chanceTypeObjet < CHANCE_BOURSE) {
				obj = new BourseDOr(this);
			} else if (chanceTypeObjet < CHANCE_POT_SOIN) {
				obj = new PotionSoin(this);
			} else if (chanceTypeObjet < CHANCE_POT_FORCE) {
				obj = new PotionForce(this);
			} else {
				obj = new BanditManchot(this);
			}

			listeObjet.add(obj);
		}
	}

	public final void generationMonstres() {
		final int CHANCE = 100;
		final int CHANCE_0 = 75;
		final int CHANCE_1 = 95;
		int chance = MyRandom.rnd.nextInt(CHANCE);

		listePersonnages = new ArrayList<>();
		int nMonstres = 0;
		Monstre m;

		if (chance > CHANCE_0 && chance < CHANCE_1) {
			nMonstres = 1;
		} else if (chance >= CHANCE_1 && chance < CHANCE) {
			nMonstres = 2;
		}

		for (int i = 0; i < nMonstres; i++) {

			final int CHANCE_M = 100;
			final int CHANCE_M_RARE = 70;
			final int CHANCE_M_ELITE = 95;
			int chanceM = MyRandom.rnd.nextInt(CHANCE_M);

			if (chanceM < CHANCE_M_RARE) {
				m = new Monstre(this, donjon);
			} else if (chanceM < CHANCE_M_ELITE) {
				m = new MonstreElite(this, donjon);
			} else {
				m = new MonstreRare(this, donjon);
			}

			listePersonnages.add(m);
		}
	}

	public void removeObjet(AbstractObjet obj) {
		listeObjet.remove(obj);
	}

	public String toString() {
		return "Salle " + ID;
	}

	public Donjon getDonjon() {
		return donjon;
	}

	public void setDonjon(Donjon donjon) {
		this.donjon = donjon;
	}

	public boolean isGagnante() {
		return gagnante;
	}

	public void setGagnante(boolean gagnante) {
		this.gagnante = gagnante;
	}

	public List<Couloir> getCouloirs() {
		return couloirs;
	}

	public void setCouloirs(List<Couloir> couloirs) {
		this.couloirs = couloirs;
	}

	public List<AbstractPersonnage> getListePersonnages() {
		return listePersonnages;
	}

	public void setListePersonnages(List<AbstractPersonnage> listePersonnages) {
		this.listePersonnages = listePersonnages;
	}

	public List<AbstractObjet> getListeObjet() {
		return listeObjet;
	}

	public void setListeObjet(List<AbstractObjet> listeObjet) {
		this.listeObjet = listeObjet;
	}

}
