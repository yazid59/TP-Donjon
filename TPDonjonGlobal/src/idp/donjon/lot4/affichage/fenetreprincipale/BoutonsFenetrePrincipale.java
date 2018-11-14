package idp.donjon.lot4.affichage.fenetreprincipale;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import idp.donjon.lot4.affichage.jeu.FenetreJeu;
import idp.donjon.lot4.utils.BoutonsString;

public class BoutonsFenetrePrincipale implements ActionListener {

	private static MaFenetrePrincipale fenetrePrincipale;
	private static final int DONJON_PETIT = 36;
	private static final int DONJON_MOYEN = 100;
	private static final int DONJON_GRAND = 324;
	private static final int DONJON_IMMENSE = 1024;

	private static final int NAME_MIN_LENGTH = 3;
	private static final int NAME_MAX_LENGTH = 8;

	private AbstractMyFenetreP frame;

	private static String nomJoueur = "";

	public BoutonsFenetrePrincipale() {
	}

	public BoutonsFenetrePrincipale(AbstractMyFenetreP frame) {
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommande = e.getActionCommand();
		switch (actionCommande) {
		case BoutonsString.NOUVELLE_PARTIE:
			new FenetreNom(fenetrePrincipale);
			break;
		case BoutonsString.MEILLEURS_SCORES:
			new FenetreMeilleursScores(fenetrePrincipale);
			break;
		case BoutonsString.VALIDER:
			if (FenetreNom.txtField.getText().length() >= NAME_MIN_LENGTH
					&& FenetreNom.txtField.getText().length() <= NAME_MAX_LENGTH) {
				nomJoueur = FenetreNom.txtField.getText();
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				new FenetreDebutJeu(fenetrePrincipale);
			} else {
				new FenetreErreur(((FenetreNom) frame));
			}
			break;
		case BoutonsString.PETIT:
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			frame.fenetrePrincipale.dispatchEvent(new WindowEvent(fenetrePrincipale, WindowEvent.WINDOW_CLOSING));
			new FenetreJeu(DONJON_PETIT, nomJoueur);
			break;
		case BoutonsString.MOYEN:
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			frame.fenetrePrincipale.dispatchEvent(new WindowEvent(fenetrePrincipale, WindowEvent.WINDOW_CLOSING));
			new FenetreJeu(DONJON_MOYEN, nomJoueur);
			break;
		case BoutonsString.GRAND:
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			frame.fenetrePrincipale.dispatchEvent(new WindowEvent(fenetrePrincipale, WindowEvent.WINDOW_CLOSING));
			new FenetreJeu(DONJON_GRAND, nomJoueur);
			break;
		case BoutonsString.IMMENSE:
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			frame.fenetrePrincipale.dispatchEvent(new WindowEvent(fenetrePrincipale, WindowEvent.WINDOW_CLOSING));
			new FenetreJeu(DONJON_IMMENSE, nomJoueur);
			break;
		case BoutonsString.FERMER:
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			frame.fenetrePrincipale.requestFocus();
			break;
		case BoutonsString.QUITTER:
			System.exit(0);
			break;
		default:
			break;
		}
	}

	public static MaFenetrePrincipale getFenetrePrincipale() {
		return fenetrePrincipale;
	}

	public static void setFenetrePrincipale(MaFenetrePrincipale fenetrePrincipale) {
		BoutonsFenetrePrincipale.fenetrePrincipale = fenetrePrincipale;
	}

}
