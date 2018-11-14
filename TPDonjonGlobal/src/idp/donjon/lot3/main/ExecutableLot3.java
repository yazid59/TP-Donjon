
/*
 * Le main de ce projet sert � initialiser le jeu selon des param�tres rentr�s par l'utilisateur.
 */
package idp.donjon.lot3.main;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;

import idp.donjon.lot3.donjon.Donjon;
import idp.donjon.lot3.personnages.AbstractPersonnage;
import idp.donjon.lot3.utils.MyScanner;

public class ExecutableLot3 {

	public void launcher() {
		menu();
	}
	private static void debutJeu() {

		System.out.println("***LOT3***");
		System.out.println("LE DONJON");
		System.out.println("Veuillez choisir la taille du Donjon :");

		String petit = "Petit";
		String moyen = "Moyen";
		String grand = "Grand";
		String immense = "Immense";
		String quitterString = "QUITTER";

		ArrayList<String> listeActions = new ArrayList<>();
		listeActions.add(listeActions.size() + 1 + " - " + petit);
		listeActions.add(listeActions.size() + 1 + " - " + moyen);
		listeActions.add(listeActions.size() + 1 + " - " + grand);
		listeActions.add(listeActions.size() + 1 + " - " + immense);
		listeActions.add(listeActions.size() + 1 + " - " + quitterString);

		int choix = -1;
		do {

			System.out.println("\n-------------------------------");
			listeActions.stream().forEach(System.out::println);
			choix = MyScanner.readInt();
			choix--;
		} while (choix < 0 || choix >= listeActions.size());

		if (listeActions.get(choix).endsWith(petit)) {
			System.out.println("Petit joueur...");
			new Donjon(36);
		} else if (listeActions.get(choix).endsWith(moyen)) {
			System.out.println("Un d�fi � la hauteur...");
			new Donjon(100);
		} else if (listeActions.get(choix).endsWith(grand)) {
			System.out.println("Assumerez-vous vos choix...?");
			new Donjon(324);
		} else if (listeActions.get(choix).endsWith(immense)) {
			System.out.println("Vous n'�tes pas pr�t...");
			new Donjon(1024);
		} else {
			System.exit(0);
		}

	}

	private static void menu() {

		String nouvellePartie = "Nouvelle partie";
		String meilleursScores = "Meilleurs scores";
		String quitterString = "QUITTER";

		ArrayList<String> listeActions = new ArrayList<>();
		listeActions.add(listeActions.size() + 1 + " - " + nouvellePartie);
		listeActions.add(listeActions.size() + 1 + " - " + meilleursScores);
		listeActions.add(listeActions.size() + 1 + " - " + quitterString);

		int choix = -1;
		do {

			System.out.println("\n-------------------------------");
			listeActions.stream().forEach(System.out::println);
			choix = MyScanner.readInt();
			choix--;
		} while (choix < 0 || choix >= listeActions.size());

		if (listeActions.get(choix).endsWith(nouvellePartie)) {
			debutJeu();
		} else if (listeActions.get(choix).endsWith(meilleursScores)) {
			meilleursScores();
		} else {
			System.exit(0);
		}
	}

	private static void meilleursScores() {
		String scores = null;
		try {
			scores = AbstractPersonnage.affichageScores();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		if (scores != null) {
			System.out.println(scores);
		} else {
			System.out.println("Aucune partie enregistr�e !");
		}
		menu();
	}

}
