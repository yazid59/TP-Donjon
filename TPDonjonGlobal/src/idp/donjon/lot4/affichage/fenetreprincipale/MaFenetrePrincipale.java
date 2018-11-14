package idp.donjon.lot4.affichage.fenetreprincipale;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import idp.donjon.lot4.utils.BoutonsString;


public class MaFenetrePrincipale extends JFrame {

	public MaFenetrePrincipale() {
		BoutonsFenetrePrincipale.setFenetrePrincipale(this);

		setBounds(400,10,800,600);
		setTitle("Fenêtre principale");
		setLayout(null);
		setVisible(true);
		
		
		JButton debutPartie=new JButton(BoutonsString.NOUVELLE_PARTIE);
		debutPartie.addActionListener(new BoutonsFenetrePrincipale());
		
		JButton meilleursScores=new JButton(BoutonsString.MEILLEURS_SCORES);
		meilleursScores.addActionListener(new BoutonsFenetrePrincipale());
		
		JButton quitter=new JButton(BoutonsString.QUITTER);
		quitter.addActionListener(new BoutonsFenetrePrincipale());
		
		JPanel panelBoutons = new JPanel(new GridLayout(3,0));
		panelBoutons.setBounds(200, 150, 400, 300);
		panelBoutons.add(debutPartie);
		panelBoutons.add(meilleursScores);
		panelBoutons.add(quitter);
		
		add(panelBoutons);
		
		
	}
}
