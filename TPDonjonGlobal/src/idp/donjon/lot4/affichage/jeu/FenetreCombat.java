package idp.donjon.lot4.affichage.jeu;

import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import idp.donjon.lot4.donjon.Donjon;
import idp.donjon.lot4.personnages.AbstractPersonnage;
import idp.donjon.lot4.personnages.Joueur;
import idp.donjon.lot4.personnages.Monstre;
import idp.donjon.lot4.utils.BoutonsString;

public class FenetreCombat extends MyFenetre {

	public List<AbstractPersonnage> ennemis;
	JPanel myPanel;

	public FenetreCombat(Joueur j, Donjon d, FenetreJeu fJeu) {
		super(fJeu);
		this.j = j;
		this.d = d;
		this.fJeu.fCombat = this;

		myPanel=new JPanel();
		myPanel.setLayout(new GridLayout());
		
		
		ennemis = j.getSalle().getListePersonnages().stream().filter(x -> !x.equals(j)).collect(Collectors.toList());

		setBounds(400, 50, 600, 300);
		setTitle("COMBAT");
		getContentPane().setLayout(new GridLayout());

		getCibles();

		setVisible(true);
		repaint();
		
	}

	public void getCibles() {
		
		List<StringBuilder> sList= ennemis.stream().map(x->((Monstre)x).stats).collect(Collectors.toList());
		
		myPanel.removeAll();
		myPanel.repaint();
		
		int x = 10, y = 10;
		for(int i=0;i<ennemis.size();i++) {
			Monstre m = (Monstre) ennemis.get(i);
			
				JPanel monstrePanel = new JPanel();
				monstrePanel.setBounds(x, y, 40, 20);
				monstrePanel.setLayout(new GridLayout(2, 1));

				JPanel txtPanel = new JPanel();
				txtPanel.setBounds(x, y, 40, 20);
				txtPanel.setLayout(new GridLayout());
				monstrePanel.add(txtPanel, 0);

				
				JTextArea txt = new JTextArea();
				txt.setEditable(false);
				txt.setFocusable(false);
				txt.setText(m.apresAttaque().toString());
				txtPanel.add(txt);

				y += 40;
				JPanel boutonPanel = new JPanel();
				boutonPanel.setBounds(x, y + 100, 40, 20);
				boutonPanel.setLayout(new GridLayout());
				monstrePanel.add(boutonPanel, 1);

				JButton attaquer = new JButton(BoutonsString.ATTAQUER);
				attaquer.addActionListener(new BoutonJeu(m, this));
				boutonPanel.add(attaquer);

				myPanel.add(monstrePanel, i);

				x += 40;
				myPanel.updateUI();
				sList.add(i, m.stats);
			}
		if (myPanel.getComponents().length==0) {
			fJeu.cibleMort(fJeu.combat);
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}

		getContentPane().add(myPanel);

		

	}

}
