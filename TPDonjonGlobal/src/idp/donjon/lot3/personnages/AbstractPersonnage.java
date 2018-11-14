
/*	
 * Cette classe abstraite sert à définir le comportement des personnages en général.
 * Un personnage est caractérisé par ses points de vie, sa force, l'or qu'il porte et 
 * le tour qui lui donne le droit ou non d'agir.
 */
package idp.donjon.lot3.personnages;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import idp.donjon.lot3.donjon.Couloir;
import idp.donjon.lot3.donjon.Donjon;
import idp.donjon.lot3.donjon.Salle;
import idp.donjon.lot3.utils.MyFile;
import idp.donjon.lot3.utils.MyRandom;

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

	public void sauvegardePartie() throws IOException {

		File f = MyFile.f;

		FileWriter fWrite = new FileWriter(f, true);

		Joueur player = getJoueur(salle.getListePersonnages());

		JSONObject j = new JSONObject();

		j.put("Pv", player.pv);
		j.put("Force", player.force);
		j.put("Or", player.force);
		j.put("Nom", player.nom);

		if (!f.exists()) {
			f.createNewFile();
		}
		fWrite.write(j.toJSONString() + "\n");
		fWrite.close();

	}

	public static String affichageScores() throws IOException, ParseException {

		File f = MyFile.f;
		FileReader fRead = new FileReader(f);

		int c = 0;
		StringBuilder s = new StringBuilder();
		while (c != -1) {
			if (c != 0) {
				s.append((char) c);
			}
			c = fRead.read();
		}

		if (s.length() > 0) {
			ArrayList<String> scoresList = new ArrayList<>(Arrays.asList(s.toString().split("\\n")));
			final int MAX_SCORES_ENREGISTRES = 10;
			while (scoresList.size() > MAX_SCORES_ENREGISTRES) {
				scoresList.remove(scoresList.get(0));
			}

			Comparator<String> mc = (String x, String y) -> {
				JSONObject o1 = null;
				JSONObject o2 = null;
				try {
					o1 = (JSONObject) new JSONParser().parse(x);
					o2 = (JSONObject) new JSONParser().parse(y);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if ((Long) o1.get("Or") > (Long) o2.get("Or")) {
					return 1;
				} else if ((Long) o1.get("Or") < (Long) o2.get("Or")) {
					return -1;
				} else {
					return 0;
				}
			};
			scoresList.sort(mc.reversed());

			StringBuilder str = new StringBuilder(scoresList.size() + " scores enregistrés :\n****************\n");
			for (int i = 0; i < scoresList.size(); i++) {
				JSONObject o = (JSONObject) new JSONParser().parse(scoresList.get(i));
				str.append(i + 1 + " : " + o.get("Nom") + " - Or : " + o.get("Or")
						+ "\n****************\n");
			}

			fRead.close();
			return str.toString();
		} else {
			fRead.close();
			return null;
		}
	}

	public void quitter() {
		try {
			sauvegardePartie();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void declencherMovement() {
		List<Salle> listeS = d.getListeSalles().stream()
				.filter(x -> AbstractPersonnage.isMonster(x.getListePersonnages())
						&& !AbstractPersonnage.isPlayer(x.getListePersonnages()))
				.collect(Collectors.toList());

		List<List<AbstractPersonnage>> listeListeP = listeS.stream().map(Salle::getListePersonnages)
				.collect(Collectors.toList());

		for (int i = 0; i < listeListeP.size(); i++) {
			for (int j = 0; j < listeListeP.get(i).size(); j++) {
				listeListeP.get(i).get(j).mouvement();
			}
		}
	}

	public void mouvement() {

		int choix = MyRandom.rnd.nextInt(salle.getCouloirs().size());
		Couloir monCouloir = salle.getCouloirs().get(choix);

		salle.getListePersonnages().remove(this);
		setSalle(monCouloir.getSalle2());
		salle.getListePersonnages().add(this);

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
