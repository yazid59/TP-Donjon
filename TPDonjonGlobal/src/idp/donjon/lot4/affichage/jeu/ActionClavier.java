package idp.donjon.lot4.affichage.jeu;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ActionClavier implements KeyListener {
	FenetreJeu f;
	MonPanneau panneau;

	public ActionClavier() {
	}

	public ActionClavier(FenetreJeu f) {
		this.f = f;
		this.panneau = f.mp;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		boolean movement;
		
		if (code == KeyEvent.VK_LEFT) {
			movement = f.j.seDeplacer("Gauche");
		} else if (code == KeyEvent.VK_RIGHT) {
			movement = f.j.seDeplacer("Droite");
		} else if (code == KeyEvent.VK_UP) {
			movement = f.j.seDeplacer("Haut");
		} else if (code == KeyEvent.VK_DOWN) {
			movement = f.j.seDeplacer("Bas");
		} else {
			movement = false;
		}
		if (movement) {
			f.j.action();
			f.regarder();
			f.affiche();
			if (panneau == null) {
				panneau = f.mp;
			}
			panneau.repaint();
		}

	}

}
