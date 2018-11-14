package idp.donjon.lot4.affichage.jeu;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;

import javax.swing.JPanel;

import idp.donjon.lot4.donjon.Salle;
import idp.donjon.lot4.personnages.AbstractPersonnage;

public class MonPanneau extends JPanel {

	public boolean dessin = false;
	FenetreJeu maFenetre;
	final int PAS_VERTICAL = 25;
	final int PAS_HORIZONTAL = 10;

	public MonPanneau(FenetreJeu maFenetre) {
		this.maFenetre = maFenetre;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		maFenetre.requestFocusInWindow();
		if (dessin) {
			String dessin = maFenetre.d.dessinCustom().toString();
			String[] dessinTab = dessin.split(System.getProperty("line.separator"));
			
			int y = 0;
			int x = 0;
			for (int i = 0; i < dessinTab.length; i++) {
				for (int j = 0; j < dessinTab[i].length(); j++) {
					x = 100 + (j * PAS_HORIZONTAL);
					y = 100 + (i * PAS_VERTICAL);

					if (dessinTab[i].charAt(j) == '+') {
						HashMap<String, Character> autourPlus = new HashMap<>();
						if (j > 0)
							autourPlus.put("xPlus", dessinTab[i].charAt(j - 1));
						if (j < dessinTab[i].length() - 1)
							autourPlus.put("Plusx", dessinTab[i].charAt(j + 1));
						if (i > 0)
							autourPlus.put("yPlus", dessinTab[i - 1].charAt(j));
						if (i < dessinTab.length - 1)
							autourPlus.put("Plusy", dessinTab[i + 1].charAt(j));
						dessinPlus(g, x, y, autourPlus);
					} else
						traductionChar(dessinTab[i].charAt(j), g, x, y);
				}
			}
		}
	}

	private void dessinPlus(Graphics g, int x, int y, HashMap<String, Character> map) {
		for (String s : map.keySet()) {
			Character c = map.get(s);
			if (s.equals("Plusx")) {
				if (c == '-' || c == '~') {
					g.drawLine(x, y, x + PAS_HORIZONTAL, y);
				}
			}
			if (s.equals("xPlus")) {
				if (c == '-' || c == '~') {
					g.drawLine(x, y, x - PAS_HORIZONTAL, y);
				}
			}

			if (s.equals("Plusy")) {
				if (c == '|' || c == '}' || c == '{') {
					g.drawLine(x, y, x, y + PAS_VERTICAL);
				}
			}
			if (s.equals("yPlus")) {
				if (c == '|' || c == '{' || c == '}') {
					g.drawLine(x, y, x, y - PAS_VERTICAL);
				}
			}

		}

	}

	private void traductionChar(char c, Graphics g, int x1, int y1) {
		switch (c) {
		case '~':
			g.drawLine(x1, y1, x1 + PAS_HORIZONTAL, y1);
			break;
		case '9':											//extrémité bas droite
			g.drawLine(x1, y1, x1, y1 - PAS_VERTICAL);		
			break;
		case '8':											//extrémité bas gauche
			g.drawLine(x1, y1, x1 + PAS_HORIZONTAL, y1);
			break;
		case '7':											//extrémité haut gauche
			g.drawLine(x1, y1, x1 + PAS_HORIZONTAL, y1);
			g.drawLine(x1, y1, x1, y1 + PAS_VERTICAL);
			break;
		case '6':											//extrémité haut droite
			g.drawLine(x1, y1, x1, y1 + PAS_VERTICAL);
			break;
		case '{':
			g.drawLine(x1, y1, x1, y1 + PAS_VERTICAL);
			break;
		case '}':
			g.drawLine(x1, y1, x1, y1 + PAS_VERTICAL);
			break;
		case '-':
			g.drawLine(x1, y1, x1 + PAS_HORIZONTAL, y1);
			break;
		case '|':
			g.drawLine(x1, y1, x1, y1 + PAS_VERTICAL);
			break;
		case 'J':
			if (isAlone()) {
				g.drawImage(maFenetre.imagePlayer, x1-10, y1-10, x1+22, y1+22,
						0, 0, 32, 32, null);
			}
			else {
				g.drawImage(maFenetre.imagePlayer, x1-10, y1-10, Color.RED, null);
			}
			break;
		case 'O':
			g.drawImage(maFenetre.imageObj, x1-15, y1, x1+24, y1+35,
					0, 0, 39, 52, null);
			break;
		case 'M':
			g.drawImage(maFenetre.imageMonster, x1-15, y1-25, x1+24, y1+27,
					0, 0, 39, 52, null);
			break;
		case 'S':
			g.fill3DRect(x1, y1, PAS_HORIZONTAL*2, PAS_VERTICAL,true);
			break;
		default:
			break;
		}
	}
	private boolean isAlone() {
		Salle s = maFenetre.d.joueur.getSalle();
		return !AbstractPersonnage.isMonster(s.getListePersonnages());
	}

}