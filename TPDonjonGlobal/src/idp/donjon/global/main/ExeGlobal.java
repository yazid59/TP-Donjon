package idp.donjon.global.main;

import java.util.ArrayList;

import idp.donjon.lot1.donjon.Donjon;
import idp.donjon.lot1.main.ExecutableLot1;
import idp.donjon.lot1.utils.MyScanner;
import idp.donjon.lot2.main.ExecutableLot2;
import idp.donjon.lot3.main.ExecutableLot3;
import idp.donjon.lot4.main.ExecutableLot4;

public class ExeGlobal {

	public static void main(String[] args) {
		debutJeu();
	}

	public static void debutJeu() {
		
	System.out.println("Veuillez choisir le lot du Donjon :");

	String petit = "Lot1";
	String moyen = "Lot2";
	String grand = "Lot3";
	String immense = "Lot4";
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
		new ExecutableLot1().launcher();;
	} else if (listeActions.get(choix).endsWith(moyen)) {
		new ExecutableLot2().launcher();;
	} else if (listeActions.get(choix).endsWith(grand)) {
		new ExecutableLot3().launcher();;
	} else if (listeActions.get(choix).endsWith(immense)) {
		new ExecutableLot4().launcher();;
	} else {
		System.exit(0);
	}
	}

}
