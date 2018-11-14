
/*	
 * Cette classe sert à définir le comportement du Monstre élite.
 * Ce Monstre est caractérisé par la salle et le donjon qui le contiennent.
 */

package idp.donjon.lot3.personnages;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import idp.donjon.lot3.donjon.Couloir;
import idp.donjon.lot3.donjon.Donjon;
import idp.donjon.lot3.donjon.Salle;
import idp.donjon.lot3.utils.MyRandom;

public class MonstreElite extends Monstre {

	private boolean mouvementSpecial;

	public MonstreElite(Salle salle, Donjon d) {
		super(salle, d);
		pvMax = getPvMax() * 2;
		setPvInitial();
		nom = getNom() + " elite";
	}

	@Override
	public void mouvement() {
		if (mouvementSpecial) {
			mouvementElite();
			mouvementSpecial = false;
		} else {
			mouvementNormal();
			mouvementSpecial = true;
		}
	}

	public void mouvementElite() {

		Salle futureSalle = trouverMeilleurChemin();
		if (futureSalle == null) {
			mouvementNormal();
		} else {

			salle.getListePersonnages().remove(this);
			setSalle(futureSalle);
			salle.getListePersonnages().add(this);
		}
	}

	public void mouvementNormal() {

		int choix = MyRandom.rnd.nextInt(salle.getCouloirs().size());
		Couloir monCouloir = salle.getCouloirs().get(choix);

		salle.getListePersonnages().remove(this);
		setSalle(monCouloir.getSalle2());
		salle.getListePersonnages().add(this);

	}

	private Salle trouverMeilleurChemin() {

		Salle salleJ = d.getListeSalles().stream().filter(x -> AbstractPersonnage.isPlayer(x.getListePersonnages()))
				.findFirst().get();

		ArrayList<Couloir> myListCouloir = (trouverChemin(salleJ, new ArrayList<Couloir>()));

		if (myListCouloir == null) {
			return null;
		} else {
			return myListCouloir.get(myListCouloir.size() - 1).getSalle1();
		}
	}

	private ArrayList<Couloir> trouverChemin(Salle salleTemp, ArrayList<Couloir> couloir) {

		ArrayList<Couloir> cheminPris = couloir;
		ArrayList<Couloir> bonChemin = new ArrayList<>();

		for (Couloir c : salleTemp.getCouloirs()) {
			if (salleTemp.getListePersonnages().contains(this)) {
				return cheminPris;
			} else {
				salleTemp = c.getSalle2();
				final Salle salleComp = salleTemp;
				Optional<Couloir> dejaPasse = cheminPris.stream().filter(x -> x.getSalle1() == salleComp).findFirst();
				if (!dejaPasse.isPresent()) {
					cheminPris.add(c);
					List<Couloir> l = trouverChemin(salleTemp, cheminPris);
					if (l != null) {
						bonChemin.addAll(l);
						return bonChemin;
					}
					cheminPris.remove(c);
				}
			}
		}
		return null;

	}

}
