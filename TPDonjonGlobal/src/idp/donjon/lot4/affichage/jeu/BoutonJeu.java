package idp.donjon.lot4.affichage.jeu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import idp.donjon.lot4.donjon.Donjon;
import idp.donjon.lot4.objets.AbstractObjet;
import idp.donjon.lot4.personnages.Monstre;
import idp.donjon.lot4.utils.BoutonsString;

public class BoutonJeu implements ActionListener {

	public Donjon d;
	MyFenetre fenetre;
	public JPanel panel;

	JButton b;
	Monstre m;
	AbstractObjet o;
	List<JButton> bList;

	public BoutonJeu() {

	}

	public BoutonJeu(FenetreJeu fenetre) {
		this.fenetre = fenetre;
		this.d = fenetre.d;
	}

	public BoutonJeu(Monstre m, FenetreCombat fenetre) {
		this.m = m;
		this.fenetre = fenetre;
		this.d = fenetre.d;
	}

	public BoutonJeu(AbstractObjet o, FenetreObjet fenetre) {
		this.o = o;
		this.fenetre = fenetre;
		this.d = fenetre.d;
	}

	public BoutonJeu(JButton b, FenetreJeu fenetre) {
		this.b = b;
		this.fenetre = fenetre;
		this.d = fenetre.d;
		panel = new JPanel();
		bList = new ArrayList<>();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommande = e.getActionCommand();

		switch (actionCommande) {
		case BoutonsString.VICTOIRE:
			System.exit(0);
			break;
		case BoutonsString.DEFAITE:
			System.exit(0);
			break;
		case BoutonsString.COMBAT:
			new FenetreCombat(d.joueur, d, (FenetreJeu) fenetre);
			break;

		case BoutonsString.ATTAQUER:
			d.joueur.attaquer(m);
			((FenetreCombat) fenetre).fJeu.affiche();
			((FenetreCombat) fenetre).getCibles();
			break;
		case BoutonsString.VOIR_OBJET:
			new FenetreObjet(d.joueur, d, (FenetreJeu) fenetre);
			break;
		case BoutonsString.UTILISER:
			o.utiliser();
			((FenetreObjet) fenetre).getObjets();
			break;
		default:
			break;
		}

	}
}