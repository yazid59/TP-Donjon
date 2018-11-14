
/*	
 * Cette classe sert à définir le comportement du joueur.
 * Le joueur est caractérisé par la salle et le donjon qui le contiennent et son nom.
 */

package idp.donjon.lot2.personnages;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import idp.donjon.lot2.donjon.Couloir;
import idp.donjon.lot2.donjon.Donjon;
import idp.donjon.lot2.donjon.Salle;
import idp.donjon.lot2.utils.MyScanner;

public class Joueur extends AbstractPersonnage {

	public List<String> listeActions;
	public static final String REGARDER_STRING = "Regarder";
	public static final String ATTAQUER_STRING = "Attaquer";
	public static final String DEPLACER_STRING = "Se déplacer";
	public static final String UTILISER_STRING = "Utiliser";
	public static final String AFFICHE_STRING = "Afficher détails";
	public static final String QUITTER_STRING = "QUITTER";
	
	private final int INIT_PV_MAX=100;
	private final int INIT_FORCE=12;
	private final int INIT_OR=5;
	public Joueur(Salle salle, Donjon d, String nom) {
		super(salle, d);
		pvMax = INIT_PV_MAX;
		pv = pvMax;
		force = INIT_FORCE;
		or = INIT_OR;
		setNom(nom);
		salle.getListePersonnages().add(this);
		tour = true;

	}
	
	public void debutJeu() {

		do {
			action();
		} while (isTour());
	}

	public void action() {
		d.dessinCustom();


		ArrayList<String> listeActions = new ArrayList<>();
		listeActions.add(listeActions.size() + 1 + " - " + REGARDER_STRING);
		if (salle.getListePersonnages().size() > 1) {
			listeActions.add(listeActions.size() + 1 + " - " + ATTAQUER_STRING);
		}
		if (salle.getListePersonnages().size() == 1) {
			listeActions.add(listeActions.size() + 1 + " - " + DEPLACER_STRING);
		}
		if (!salle.getListeObjet().isEmpty()) {
			listeActions.add(listeActions.size() + 1 + " - " + UTILISER_STRING);
		}
		listeActions.add(listeActions.size() + 1 + " - " + AFFICHE_STRING);
		listeActions.add(listeActions.size() + 1 + " - " + QUITTER_STRING);

		int choix = -1;
		do {

			System.out.println("\n-------------------------------");
			listeActions.stream().forEach(System.out::println);
			choix = MyScanner.readInt();
			choix--;
		} while (choix < 0 || choix >= listeActions.size());

		if (listeActions.get(choix).endsWith(REGARDER_STRING)) {
			regarder();
		} else if (listeActions.get(choix).endsWith(ATTAQUER_STRING)) {
			attaquer(choixCible());
		} else if (listeActions.get(choix).endsWith(DEPLACER_STRING)) {
			seDeplacer();
		} else if (listeActions.get(choix).endsWith(UTILISER_STRING)) {
			utiliser();
		} else if (listeActions.get(choix).endsWith(AFFICHE_STRING)) {
			affiche();
		} else {
			quitter();
		}
	}

	public AbstractPersonnage choixCible() {

		System.out.println("\n---------ATTAQUER---------------");

		if (salle.getListePersonnages().size() > 2) {
			int choix = -1;

			do {
				System.out.println("Veuillez choisir une cible :");
				List<AbstractPersonnage> listeTemp = salle.getListePersonnages().stream().filter(x -> x != this)
						.collect(Collectors.toList());
				listeTemp.stream().forEach(x -> System.out.println((listeTemp.indexOf(x)) + 1 + " : " + x));

				choix = MyScanner.readInt() - 1;
			} while (choix < 0 || choix >= salle.getListePersonnages().size() - 1);
			if (choix == -1) {
				return null;
			}
			setCible(salle.getListePersonnages().get(choix));

		} else if (salle.getListePersonnages().size() == 2) {
			setCible(salle.getListePersonnages().stream().filter(x -> x != this).findFirst().get());
		} else {
			System.out.println("Aucune cible à attaquer");
			return null;
		}

		return getCible();
	}

	public void apresAttaque() {
		if (getPv() > 0) {
			System.out.println("Vous avez fait " + getForce() + " dégats.");
			if (getCible().getPv() > 0 && getPv() > 0) {
				System.out.println("il reste " + getCible().getPv() + " points de vie à ce monstre.");
			}
			System.out.println("\n---------ATTAQUE TERMINEE---------------");
		}
	}

	public void regarder() {

		System.out.println("\n---------REGARDER---------------");
		System.out.println(salle);
		salle.getCouloirs().stream().forEach(System.out::println);
		salle.getListeObjet().stream().forEach(System.out::println);
		salle.getListePersonnages().stream().filter(x -> x != this).forEach(System.out::println);
		System.out.println("\n---------REGARDER TERMINEE---------------");
	}

	public void seDeplacer() {
		if (salle.getListePersonnages().size() < 2) {
			System.out.println("\n---------DEPLACEMENT---------------");

			int choix = -1;
			if (salle.getCouloirs().size() > 1) {

				do {
					System.out.println("Veuillez choisir un couloir à emprunter :");
					salle.getCouloirs().stream()
							.forEach(x -> System.out.println((salle.getCouloirs().indexOf(x)) + 1 + " : " + x));
					choix = MyScanner.readInt() - 1;
				} while (choix < 0 || choix >= salle.getCouloirs().size());

			} else {
				choix = 0;
			}

			Couloir monCouloir = salle.getCouloirs().get(choix);

			salle.getListePersonnages().remove(this);
			setSalle(monCouloir.getSalle2());
			salle.getListePersonnages().add(this);
			declencherMovement();
			System.out.println("Vous êtes dans la " + salle + ".");

			System.out.println("\n---------DEPLACEMENT TERMINEE---------------");
		} else {
			System.out.println("Vous ne pouvez pas quitter la pièce avant de la nettoyer des monstres.");
		}

	}

	public void utiliser() {

		if (!salle.getListeObjet().isEmpty()) {

			System.out.println("\n---------UTILISATION---------------");

			int choix = -1;
			if (salle.getListeObjet().size() > 1) {
				do {
					System.out.println("Veuillez choisir un objet à utiliser :");
					salle.getListeObjet().stream()
							.forEach(x -> System.out.println((salle.getListeObjet().indexOf(x)) + 1 + " : " + x));
					choix = MyScanner.readInt() - 1;
				} while (choix < 0 || choix > salle.getCouloirs().size());
			} else {
				choix = 0;
			}

			salle.getListeObjet().get(choix).utiliser();

			System.out.println("\n---------UTILISATION TERMINEE---------------");
		} else {
			System.out.println("Aucun objet à utiliser.");
		}
	}

	public void affiche() {
		System.out.println("\n---------DETAILS---------------");
		System.out.println("Points de vie : " + getPv());
		System.out.println("Force : " + getForce());
		System.out.println("Pièces d'or : " + getOr());
		System.out.println(getSalle());
		System.out.println("\n---------DETAILS TERMINEE---------------");

	}

	public String toString() {
		return "Joueur " + nom;
	}

	public boolean equals(AbstractPersonnage p) {
		return hashCode() == p.hashCode();
	}

	@Override
	public int hashCode() {
		return getOr();
	}

}
