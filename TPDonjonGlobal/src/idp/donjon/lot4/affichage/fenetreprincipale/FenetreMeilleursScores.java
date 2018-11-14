package idp.donjon.lot4.affichage.fenetreprincipale;

import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.simple.parser.ParseException;

import idp.donjon.lot4.personnages.AbstractPersonnage;

public class FenetreMeilleursScores extends AbstractMyFenetreP {

	private int height = 0;

	public FenetreMeilleursScores(MaFenetrePrincipale fenetrePrincipale) {
		super(fenetrePrincipale);
		this.fenetrePrincipale = fenetrePrincipale;

		meilleursScores();
		setBounds(700, 200, 300, height + 120);
		setTitle("Meilleurs scores");

		setLayout(null);

	}

	private void meilleursScores() {
		String scoresTemp = null;
		JPanel scoresPanel;
		try {
			scoresTemp = AbstractPersonnage.affichageScores();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		if (scoresTemp != null) {
			scoresTemp = scoresTemp.replace("*", "");
			String[] scores = scoresTemp.split("\n\n");

			scoresPanel = new JPanel(new GridLayout(scores.length, 0));

			JLabel titre = new JLabel(scores[0]);
			titre.setBounds(50, 20, 200, 20);
			add(titre);

			height = scores.length * 30;
			scoresPanel.setBounds(70, 60, 200, height);
			for (int i = 1; i < scores.length; i++) {
				JLabel txt = new JLabel(scores[i]);
				scoresPanel.add(txt);
			}
			scoresPanel.add(fermer);
			add(scoresPanel);

		} else {
			height = 100;
			scoresPanel = new JPanel(new GridLayout(2, 0));
			scoresPanel.setBounds(20, 60, 200, 100);

			JLabel titre = new JLabel("Aucune partie enregistrée !");

			scoresPanel.add(titre);
			scoresPanel.add(fermer);
			add(scoresPanel);
		}

	}

}
