package idp.donjon.lot4.affichage.jeu;

import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import idp.donjon.lot4.donjon.Donjon;
import idp.donjon.lot4.objets.AbstractObjet;
import idp.donjon.lot4.personnages.Joueur;
import idp.donjon.lot4.utils.BoutonsString;

public class FenetreObjet extends MyFenetre {

	public List<AbstractObjet> objets;
	JPanel myPanel;

	public FenetreObjet(Joueur j, Donjon d, FenetreJeu fJeu) {
		super(fJeu);
		
		this.j = j;
		this.d = d;
		this.fJeu.fObjet = this;

		myPanel = new JPanel();
		myPanel.setLayout(new GridLayout());

		objets = j.getSalle().getListeObjet().stream().collect(Collectors.toList());

		setBounds(400, 50, 600, 300);
		setTitle("Objets");
		getContentPane().setLayout(new GridLayout());

		getObjets();

		setVisible(true);
		repaint();
	}

	public void getObjets() {

		myPanel.removeAll();
		myPanel.repaint();

		int x = 10, y = 10;
		for (int i = 0; i < objets.size(); i++) {
			AbstractObjet o = objets.get(i);

			JPanel objetPanel = new JPanel();
			objetPanel.setBounds(x, y, 40, 20);
			objetPanel.setLayout(new GridLayout(2, 1));

			JPanel txtPanel = new JPanel();
			txtPanel.setBounds(x, y, 40, 20);
			txtPanel.setLayout(new GridLayout());
			objetPanel.add(txtPanel, 0);

			JTextArea txt = new JTextArea();
			txt.setEditable(false);
			txt.setFocusable(false);
			txt.setText(o.toString());
			txtPanel.add(txt);

			y += 40;
			JPanel boutonPanel = new JPanel();
			boutonPanel.setBounds(x, y + 100, 40, 20);
			boutonPanel.setLayout(new GridLayout());
			objetPanel.add(boutonPanel, 1);

			JButton utiliser = new JButton(BoutonsString.UTILISER);
			utiliser.addActionListener(new BoutonJeu(o, this));
			boutonPanel.add(utiliser);
			myPanel.add(objetPanel, i);

			x += 40;
			myPanel.updateUI();
		}
		if (myPanel.getComponents().length == 0) {
			fJeu.objetUtilise(fJeu.voirObjet);
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}

		getContentPane().add(myPanel);

	}

}