package idp.donjon.lot4.affichage.jeu;

import javax.swing.JFrame;

import idp.donjon.lot4.donjon.Donjon;
import idp.donjon.lot4.personnages.Joueur;
import idp.donjon.lot4.utils.CloseActions;

public abstract class MyFenetre extends JFrame{

	public  Donjon d;
	public  Joueur j;
	public FenetreJeu fJeu;
	
	public MyFenetre(FenetreJeu fJeu) {
		 this.fJeu=fJeu;
		addWindowListener(new CloseActions(this));
	}
	public MyFenetre() {
		addWindowListener(new CloseActions(this));
	}
	
}
