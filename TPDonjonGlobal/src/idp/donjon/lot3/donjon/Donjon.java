
/*
 * Le donjon représente l'univers dans lequel se déroule le jeu et où tout est géré. Il est caractérisé
 *  par le nombre de salles.
 *  Le donjon génère un labyrinthe composé de salles et de couloirs
 */

package idp.donjon.lot3.donjon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import idp.donjon.lot3.objets.AbstractObjet;
import idp.donjon.lot3.personnages.AbstractPersonnage;
import idp.donjon.lot3.personnages.Joueur;
import idp.donjon.lot3.utils.MyRandom;
import idp.donjon.lot3.utils.MyScanner;

public class Donjon {
	private final int nSalles;
	private List<Salle> listeSalles;
	private int lignes;
	private int colonnes;
	private static final int BIT_DIRECTION_OUEST = 8;
	private static final int BIT_DIRECTION_NORD = 1;
	public final int[][] maze;
	private Joueur joueur;

	public Donjon(int nSalles) {
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
		joueur.debutJeu();

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

	public void dessinCustom() {

		String sJoueur = "  J  ";
		String sMonstre = "M";
		String sObjet = "  O  ";
		String sSortie = "  S  ";
		// dessin du mur extérieur NORD
		for (int i = 0; i <= colonnes; i++) {
			System.out.print("+~~~~~");
		}
		System.out.println("+");
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
						System.out.print("+-----");
					} else {
						System.out.print("+     ");
					}

				}
				System.out.println("+");
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
						System.out.print("|" + sCase);
					} else {
						System.out.print(" " + sCase);
					}
					break;
				} else if (k == limMoins) {
					// dessin du mur extérieur OUEST
					System.out.print("{" + sCase);
				} else {
					if (!coulOuest.isPresent()) {
						System.out.print("|" + sCase);
					} else {
						System.out.print(" " + sCase);
					}
				}
			}
			// dessin du mur extérieur EST
			System.out.println("}");
			lignesTemp++;
			limPlus = limPlus - colonnes - 1;
			limMoins = limPlus - colonnes - 1;
		}
		// dessin du mur extérieur SUD
		for (int i = 0; i <= colonnes; i++) {
			System.out.print("+~~~~~");
		}
		System.out.println("+");
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

	public void dessin() {
		for (int i = 0; i <= lignes; i++) {
			// draw the north edge
			for (int j = 0; j <= colonnes; j++) {
				if ((maze[j][i] & BIT_DIRECTION_NORD) == 0) {
					System.out.print("+---");
				} else {
					System.out.print("+   ");
				}
			}
			System.out.println("+");
			// draw the west edge
			for (int j = 0; j <= colonnes; j++) {
				if ((maze[j][i] & BIT_DIRECTION_OUEST) == 0) {
					System.out.print("|   ");
				} else {
					System.out.print("    ");
				}
			}
			System.out.println("|");
		}
		// draw the bottom line
		for (int j = 0; j <= colonnes; j++) {
			System.out.print("+---");
		}
		System.out.println("+");
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
		final double POURCENTAGE_SALLES_GAGNANTES = 0.01;

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
		System.out.println("Bienvenue dans le donjon.\n" + "Saurez-vous surmonter ses défis ?\n"
				+ "Ou vous laisserez-vous submerger...");

		do {
			int indexDepart = MyRandom.rnd.nextInt(listeSalles.size());

			salleDepart = listeSalles.get(indexDepart);
		} while (salleDepart.isGagnante());

		final int NOM_MIN_SIZE = 3;
		final int NOM_MAX_SIZE = 8;

		System.out.println("Veuillez saisir votre nom :");
		String nom= MyScanner.readString();
		
		while (nom.length() < NOM_MIN_SIZE || nom.length() > NOM_MAX_SIZE) {
			System.out.println("ERREUR : Votre nom doit contenir entre 3 et 8 caractères.");
			System.out.println("Veuillez saisir votre nom :");
			nom = MyScanner.readString();
		}

		joueur = new Joueur(salleDepart, this, nom);

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
