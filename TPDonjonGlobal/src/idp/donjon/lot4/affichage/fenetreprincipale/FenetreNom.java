package idp.donjon.lot4.affichage.fenetreprincipale;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import idp.donjon.lot4.utils.BoutonsString;


public class FenetreNom extends AbstractMyFenetreP {

	private JPanel panelGlobal = new JPanel(new BorderLayout());
	public static JTextField txtField;

	public FenetreNom(MaFenetrePrincipale fenetrePrincipale) {
		super(fenetrePrincipale);

		setBounds(700, 200, 300, 200);
		setTitle("Nom du joueur");
		setLayout(null);

		JLabel txt = new JLabel("Veuillez saisir votre nom :");

		JPanel buttonsPanel = new JPanel(new GridLayout());

		JButton valider = new JButton(BoutonsString.VALIDER);
		valider.addActionListener(new BoutonsFenetrePrincipale(this));

		buttonsPanel.add(valider);
		buttonsPanel.add(fermer);

		txtField = new JTextField();
		txtField.requestFocus();
		panelGlobal.setBounds(40, 40, 200, 70);
		panelGlobal.add(txt, BorderLayout.NORTH);
		panelGlobal.add(txtField, BorderLayout.CENTER);
		panelGlobal.add(buttonsPanel, BorderLayout.SOUTH);

		add(panelGlobal);
	}
}
