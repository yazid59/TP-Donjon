package idp.donjon.lot4.affichage.fenetreprincipale;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import idp.donjon.lot4.utils.BoutonsString;

public class FenetreDebutJeu extends AbstractMyFenetreP {

	public FenetreDebutJeu(MaFenetrePrincipale fenetrePrincipale) {
		super(fenetrePrincipale);

		setBounds(700, 200, 400, 300);
		setTitle("Debut partie");
		setLayout(null);

		debutJeu();
	}

	private void debutJeu() {

		JLabel titre = new JLabel("Veuillez choisir la taille du Donjon :");
		titre.setBounds(100, 20, 200, 20);
		this.getContentPane().add(titre);

		JButton petit = new JButton(BoutonsString.PETIT);
		JButton moyen = new JButton(BoutonsString.MOYEN);
		JButton grand = new JButton(BoutonsString.GRAND);
		JButton immense = new JButton(BoutonsString.IMMENSE);
		JButton fermer = new JButton(BoutonsString.FERMER);

		petit.addActionListener(new BoutonsFenetrePrincipale(this));
		moyen.addActionListener(new BoutonsFenetrePrincipale(this));
		grand.addActionListener(new BoutonsFenetrePrincipale(this));
		immense.addActionListener(new BoutonsFenetrePrincipale(this));
		fermer.addActionListener(new BoutonsFenetrePrincipale(this));

		JPanel boutonsPanel = new JPanel(new GridLayout(5, 0));
		boutonsPanel.setBounds(100, 75, 200, 150);
		boutonsPanel.add(petit);
		boutonsPanel.add(moyen);
		boutonsPanel.add(grand);
		boutonsPanel.add(immense);
		boutonsPanel.add(fermer);
		this.getContentPane().add(boutonsPanel);

	}

}
