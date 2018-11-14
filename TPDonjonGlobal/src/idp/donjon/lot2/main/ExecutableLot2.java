package idp.donjon.lot2.main;

import java.util.ArrayList;

import idp.donjon.lot2.donjon.Donjon;
import idp.donjon.lot2.utils.MyScanner;

public class ExecutableLot2 {

	public void launcher() {
		menu();
	}
	private static void debutJeu() {

		System.out.println("***LOT2***");
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
			System.out.println("Un défi à la hauteur...");
			new Donjon(100);
		} else if (listeActions.get(choix).endsWith(grand)) {
			System.out.println("Assumerez-vous vos choix...?");
			new Donjon(324);
		} else if (listeActions.get(choix).endsWith(immense)) {
			System.out.println("Vous n'êtes pas prêt...");
			new Donjon(1024);
		} else {
			System.exit(0);
		}

	}

	private static void menu() {

		String nouvellePartie = "Nouvelle partie";
		String quitterString = "QUITTER";

		ArrayList<String> listeActions = new ArrayList<>();
		listeActions.add(listeActions.size() + 1 + " - " + nouvellePartie);
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
		} else {
			System.exit(0);
		}
	}


}
