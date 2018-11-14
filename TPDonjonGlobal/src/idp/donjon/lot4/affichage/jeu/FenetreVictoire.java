package idp.donjon.lot4.affichage.jeu;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

import idp.donjon.lot4.utils.BoutonsString;

public class FenetreVictoire extends MyFenetre {
	
	public FenetreVictoire(FenetreJeu fJeu) {
		super(fJeu);
		
	setBounds(420, 50, 400, 300);
	setTitle("VICTOIRE");
	getContentPane().setLayout(new BorderLayout());

	
	JButton valider = new JButton(BoutonsString.VICTOIRE);
	valider.addActionListener(new BoutonJeu());
	getContentPane().add(valider);
	

	setVisible(true);
}


}



