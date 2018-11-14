package idp.donjon.lot4.affichage.fenetreprincipale;

import javax.swing.JButton;
import javax.swing.JFrame;

import idp.donjon.lot4.utils.BoutonsString;
import idp.donjon.lot4.utils.CloseActions;

public abstract class AbstractMyFenetreP extends JFrame{

	public JFrame fenetrePrincipale;
	public JButton fermer = new JButton(BoutonsString.FERMER);
	
	public AbstractMyFenetreP(JFrame fenetrePrincipale){
		this.fenetrePrincipale=fenetrePrincipale;
		
		setVisible(true);
		addWindowListener(new CloseActions(this));
		
		fermer.addActionListener(new BoutonsFenetrePrincipale(this));
	}
	
}
