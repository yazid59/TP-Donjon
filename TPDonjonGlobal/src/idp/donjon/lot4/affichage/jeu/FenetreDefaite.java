package idp.donjon.lot4.affichage.jeu;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

import idp.donjon.lot4.utils.BoutonsString;

public class FenetreDefaite extends MyFenetre {
	
	public FenetreDefaite(FenetreJeu fJeu) {
		super(fJeu);
		
	setBounds(420, 50, 400, 300);
	setTitle("DEFAITE");
	setLayout(new BorderLayout());

	
	JButton valider = new JButton(BoutonsString.DEFAITE);
	valider.addActionListener(new BoutonJeu());
	add(valider);
	

	setVisible(true);
}


}