
/*
 * Le donjon représente l'univers dans lequel se déroule le jeu et où tout est géré. Il est caractérisé
 *  par le nombre de salles.
 *  Le donjon génère un labyrinthe composé de salles et de couloirs
 */

package idp.donjon.lot4.donjon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import idp.donjon.lot4.affichage.jeu.FenetreJeu;
import idp.donjon.lot4.objets.AbstractObjet;
import idp.donjon.lot4.personnages.AbstractPersonnage;
import idp.donjon.lot4.personnages.Joueur;
import idp.donjon.lot4.utils.MyRandom;

public class Donjon {
	private final int nSalles;
	private List<Salle> listeSalles;
	public int lignes;
	public int colonnes;
	private static final int BIT_DIRECTION_OUEST = 8;
	private static final int BIT_DIRECTION_NORD = 1;
	public final int[][] maze;
	public Joueur joueur;
	private final String NOM;

	public final FenetreJeu frame;

	public Donjon(int nSalles, String nom, FenetreJeu frame) {
		this.frame = frame;

		this.NOM = nom;
		this.nSalles = nSalles;
		lignes = (int) Math.round(Math.sqrt((double) nSalles));
		colonnes = nSalles / lignes;

		maze = new int[this.colonnes + 1][this.lignes + 1];
		genererSalles();
		genererCouloirs();
		listeSalles.stream().forEach(Salle::nommerCouloirs);

		generateMaze(0, 0);
		traduireMaze();

		placementJoueur();
		rendreSallesGagnantes();

	}

	public List<Salle> getListeSalles() {
		return listeSalles;
	}

	public void setListeSalles(List<Salle> listeSalles) {
		this.listeSalles = listeSalles;
	}

	private void genererSalles() {

		listeSalles = new ArrayList<>();

		int nom = 1;
		for (int y = 0; y <= colonnes; y++) {
			for (int x = 0; x <= lignes; x++) {
				listeSalles.add(new Salle(this, x + 1, y + 1, nom));
				nom++;
			}
		}

	}

	public StringBuilder dessinCustom() {
		StringBuilder dessin = new StringBuilder();
		String sJoueur = "  J  ";
		String sMonstre = "M";
		String sObjet = "  O  ";
		String sSortie = "  S  ";

		// dessin du mur extérieur NORD
		dessin.append("7~~~~~");
		for (int i = 1; i <= colonnes; i++) {
			dessin.append("+~~~~~");
		}
		dessin.append("6");
		dessin.append(System.getProperty("line.separator"));
		// dessin des murs NORD
		int lignesTemp = 0;
		int limPlus = listeSalles.size() - (lignesTemp * colonnes);
		int limMoins = limPlus - colonnes - 1;
		for (int i = 0; i <= lignes; i++) {
			if (i != 0) {

				for (int k = limMoins; k < limPlus; k++) {
					Salle s = listeSalles.get(k);
					Optional<Couloir> coulNord = s.getCouloirs().stream().filter(x -> x.getNomRelatif().equals("NORD"))
							.findFirst();
					if (!coulNord.isPresent()) {
						dessin.append("+-----");
					} else {
						dessin.append("+     ");
					}

				}
				dessin.append("+");
				dessin.append(System.getProperty("line.separator"));

			}

			// dessin des murs OUEST
			for (int k = limMoins; k < limPlus; k++) {
				Salle s = listeSalles.get(k);
				boolean estSortie = s.isGagnante();
				boolean estJoueur = AbstractPersonnage.isPlayer(s.getListePersonnages());
				boolean estMonstre = AbstractPersonnage.isMonster(s.getListePersonnages());
				boolean estObjet = AbstractObjet.isObjet(s.getListeObjet());
				String sCase = "     ";
				if (estSortie) {
					sCase = sSortie;
				} else if (estJoueur) {
					sCase = sJoueur;
				} else if (estMonstre) {
					int n = AbstractPersonnage.nMonster(s.getListePersonnages());
					if (n == 1) {
						sCase = "  " + sMonstre + "  ";
					} else {
						sCase = " " + n + sMonstre + "  ";
					}
				}
				else if (estObjet) {
					sCase = sObjet;
				}

				Optional<Couloir> coulOuest = s.getCouloirs().stream().filter(x -> x.getNomRelatif().equals("OUEST"))
						.findFirst();

				if (k == limPlus - 1) {
					if (!coulOuest.isPresent()) {
						dessin.append("|" + sCase);
					} else {
						dessin.append(" " + sCase);
					}
					break;
				} else if (k == limMoins) {
					// dessin du mur extérieur OUEST
					dessin.append("{" + sCase);
				} else {
					if (!coulOuest.isPresent()) {
						dessin.append("|" + sCase);
					} else {
						dessin.append(" " + sCase);
					}
				}
			}
			// dessin du mur extérieur EST
			dessin.append("}");
			dessin.append(System.getProperty("line.separator"));
			lignesTemp++;
			limPlus = limPlus - colonnes - 1;
			limMoins = limPlus - colonnes - 1;
		}

		// dessin du mur extérieur SUD
		dessin.append("8~~~~~");
		for (int i = 1; i <= colonnes; i++) {
			dessin.append("+~~~~~");
		}
		dessin.append("9");
		dessin.append(System.getProperty("line.separator"));

		return dessin;
	}

	private void generateMaze(int cx, int cy) {
		DIR[] dirs = DIR.values();
		Collections.shuffle(Arrays.asList(dirs));
		for (DIR dir : dirs) {
			int nx = cx + dir.dx;
			int ny = cy + dir.dy;
			if (between(nx, colonnes + 1) && between(ny, lignes + 1) && (maze[nx][ny] == 0)) {
				maze[cx][cy] += dir.bit;
				maze[nx][ny] += dir.opposite.bit;
				generateMaze(nx, ny);
			}
		}
	}

	private void traduireMaze() {
		for (int i = 0; i <= colonnes; i++) {
			for (int j = 0; j <= lignes; j++) {
				int tradJ = j + 1;
				int tradI = lignes - i + 1;
				Salle s = listeSalles.stream().filter(x -> x.X == tradJ && x.Y == tradI).findAny().get();
				Optional<Couloir> coulNord = s.getCouloirs().stream().filter(x -> x.getNomRelatif().equals("NORD"))
						.findFirst();
				Optional<Couloir> coulOuest = s.getCouloirs().stream().filter(x -> x.getNomRelatif().equals("OUEST"))
						.findFirst();

				if ((maze[j][i] & BIT_DIRECTION_NORD) == 0) {
					if (coulNord.isPresent()) {
						Optional<Couloir> toDestroyBis = coulNord.get().getSalle2().getCouloirs().stream()
								.filter(x -> x.getSalle2() == s).findFirst();
						if (toDestroyBis.isPresent()) {
							coulNord.get().getSalle2().getCouloirs().remove(toDestroyBis.get());
							s.getCouloirs().remove(coulNord.get());
						}
					}
				}
				if ((maze[j][i] & BIT_DIRECTION_OUEST) == 0) {
					if (coulOuest.isPresent()) {
						Optional<Couloir> toDestroyBis = coulOuest.get().getSalle2().getCouloirs().stream()
								.filter(x -> x.getSalle2() == s).findFirst();
						if (toDestroyBis.isPresent()) {
							coulOuest.get().getSalle2().getCouloirs().remove(toDestroyBis.get());
							s.getCouloirs().remove(coulOuest.get());
						}
					}
				}
			}
		}

	}


	public final void genererCouloirs() {
		for (Salle salle : listeSalles) {

			Optional<Salle> xPlus = listeSalles.stream().filter(x -> x.X == salle.X + 1 && x.Y == salle.Y).findFirst();
			Optional<Salle> xMoins = listeSalles.stream().filter(x -> x.X == salle.X - 1 && x.Y == salle.Y).findFirst();
			Optional<Salle> yPlus = listeSalles.stream().filter(x -> x.X == salle.X && x.Y == salle.Y + 1).findFirst();
			Optional<Salle> yMoins = listeSalles.stream().filter(x -> x.X == salle.X && x.Y == salle.Y - +1)
					.findFirst();

			if (xPlus.isPresent()) {
				salle.getCouloirs().add(new Couloir(salle, xPlus.get()));
			}
			if (xMoins.isPresent()) {
				salle.getCouloirs().add(new Couloir(salle, xMoins.get()));
			}
			if (yPlus.isPresent()) {
				salle.getCouloirs().add(new Couloir(salle, yPlus.get()));
			}
			if (yMoins.isPresent()) {
				salle.getCouloirs().add(new Couloir(salle, yMoins.get()));
			}
		}

	}

	private void rendreSallesGagnantes() {
		final double POURCENTAGE_SALLES_GAGNANTES = 0.005;

		int nSallesGagnantes = (int) (nSalles * POURCENTAGE_SALLES_GAGNANTES);
		if (nSallesGagnantes == 0) {
			nSallesGagnantes = 1;
		}

		final int DISTANCE_MINIMUM_TO_FINISH = 10;

		for (int i = 0; i < nSallesGagnantes; i++) {
			int indexSalleGagnante;
			ArrayList<Couloir> monChemin;
			do {
				indexSalleGagnante = MyRandom.rnd.nextInt(nSalles - 1);
				monChemin = (trouverChemin(listeSalles.get(indexSalleGagnante), new ArrayList<Couloir>()));
			} while (monChemin.size() < DISTANCE_MINIMUM_TO_FINISH || listeSalles.get(indexSalleGagnante).isGagnante());

			listeSalles.get(indexSalleGagnante).setGagnante(true);
		}
	}

	private ArrayList<Couloir> trouverChemin(Salle salleTemp, ArrayList<Couloir> couloir) {

		ArrayList<Couloir> cheminPris = couloir;
		ArrayList<Couloir> bonChemin = new ArrayList<>();

		for (Couloir c : salleTemp.getCouloirs()) {
			if (salleTemp.getListePersonnages().contains(joueur)) {
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

	private void placementJoueur() {
		Salle salleDepart;

		do {
			int indexDepart = MyRandom.rnd.nextInt(listeSalles.size());
			salleDepart = listeSalles.get(indexDepart);
		} while (salleDepart.isGagnante() || salleDepart.getListePersonnages().size() > 0);

		joueur = new Joueur(salleDepart, this, NOM);

	}

	private static boolean between(int lower, int upper) {
		return (lower >= 0) && (lower < upper);
	}

	private enum DIR {
		N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);
		private final int bit;
		private final int dx;
		private final int dy;
		private DIR opposite;

		// use the static initializer to resolve forward references
		static {
			N.opposite = S;
			S.opposite = N;
			E.opposite = W;
			W.opposite = E;
		}

		DIR(int bit, int dx, int dy) {
			this.bit = bit;
			this.dx = dx;
			this.dy = dy;
		}
	}
}
