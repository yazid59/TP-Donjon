package idp.donjon.lot4.affichage.fenetreprincipale;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class FenetreErreur extends AbstractMyFenetreP {

	private JPanel panelGlobal = new JPanel(new BorderLayout());
	private JLabel label = new JLabel("Votre nom doit comporter entre 3 et 8 caractères !");

	public FenetreErreur(FenetreNom fNom) {
		super(fNom);

		setBounds(700, 200, 400, 200);
		setTitle("Nom incorrect");
		setLayout(null);

		panelGlobal.setBounds(40, 40, 300, 70);
		panelGlobal.add(label, BorderLayout.CENTER);
		panelGlobal.add(fermer, BorderLayout.SOUTH);

		add(panelGlobal);
	}
}
