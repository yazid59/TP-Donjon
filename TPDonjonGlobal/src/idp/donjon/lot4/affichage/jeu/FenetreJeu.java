package idp.donjon.lot4.affichage.jeu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import idp.donjon.lot4.donjon.Donjon;
import idp.donjon.lot4.objets.AbstractObjet;
import idp.donjon.lot4.personnages.AbstractPersonnage;
import idp.donjon.lot4.personnages.Joueur;
import idp.donjon.lot4.personnages.Monstre;
import idp.donjon.lot4.utils.BoutonsString;

public class FenetreJeu extends MyFenetre {

	public final int HEIGHT;
	public final int WIDTH;

	public static final int SCROLL_V_INCREMENT = 20;
	public static final int SCROLL_H_INCREMENT = 20;

	public FenetreCombat fCombat;
	public FenetreObjet fObjet;

	public final Donjon d;
	public final Joueur j;
	public final Image imagePlayer;
	public final Image imageMonster;
	public final Image imageObj;

	JPanel panelGlobal = new JPanel();

	public JPanel buttonsPanel;
	public BoutonJeu ecouteur;

	public MonPanneau mp;

	JPanel panelTxt;
	public JTextArea regarder;
	public JTextArea stats;

	JButton combat;
	JButton voirObjet;

	public FenetreJeu(int nSalles, String nomJoueur) {
		super();

		d = new Donjon(nSalles, nomJoueur, this);
		j = AbstractPersonnage.getJoueur(d);

		HEIGHT = 200 + d.lignes * 60;
		WIDTH = 200 + d.colonnes * 90;
		definirTaille();

		URL url = this.getClass().getResource("/idp/images/player.png");
		URL url2 = this.getClass().getResource("/idp/images/monster.png");
		URL url3 = this.getClass().getResource("/idp/images/objet.jpg");		
		
		ImageIcon player = new ImageIcon(url);
		ImageIcon monster = new ImageIcon(url2);
		ImageIcon obj = new ImageIcon(url3);

		imagePlayer = player.getImage();
		imageObj = obj.getImage();
		imageMonster = monster.getImage();

		setBounds(definirTaille());
		setTitle("Debut partie");

		panelGlobal.setLayout(new BorderLayout());

		panelTxt = new JPanel();
		panelTxt.setLayout(new GridLayout(2, 0));
		regarder = new JTextArea();
		stats = new JTextArea();

		dessin();

		affiche();
		regarder();
		setVisible(true);
		repaint();
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		add(panelGlobal);

	}

	private Rectangle definirTaille() {

		Rectangle r = new Rectangle();

		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle bounds = env.getMaximumWindowBounds();

		int width = Math.min(WIDTH, bounds.width);
		int height = Math.min(HEIGHT, bounds.height);
		r.setBounds(0, 0, width, height);

		return r;
	}

	private void dessin() {

		mp = new MonPanneau(this);

		mp.setPreferredSize(new Dimension(WIDTH - 200, HEIGHT - 100));
		this.addKeyListener(new ActionClavier(this));
		mp.setFocusCycleRoot(true);
		mp.dessin = true;

		JScrollPane scrollPane = new JScrollPane(mp);
		scrollPane.getVerticalScrollBar().setUnitIncrement(SCROLL_V_INCREMENT);
		scrollPane.getHorizontalScrollBar().setUnitIncrement(SCROLL_H_INCREMENT);
		panelGlobal.add(scrollPane, BorderLayout.CENTER);
	}

	public void combat() {
		if (buttonsPanel == null) {
			buttonsPanel = new JPanel(new GridLayout(1, 0));
			buttonsPanel.setSize(new Dimension(0, 200));
			this.panelGlobal.add(buttonsPanel, BorderLayout.NORTH);
		}

		if (combat == null) {
			combat = new JButton(BoutonsString.COMBAT);
			ecouteur = new BoutonJeu(combat, this);
			combat.addActionListener(ecouteur);
			combat.addKeyListener(new ActionClavier(this));
			buttonsPanel.add(combat);

			setVisible(true);
			buttonsPanel.updateUI();
		} else if (!combat.isEnabled()) {
			combat = new JButton(BoutonsString.COMBAT);
			ecouteur = new BoutonJeu(combat, this);
			combat.addActionListener(ecouteur);
			combat.addKeyListener(new ActionClavier(this));
			buttonsPanel.add(combat);

			setVisible(true);
			buttonsPanel.updateUI();
		}
	}

	public void voirObjet() {
		if (buttonsPanel == null) {
			buttonsPanel = new JPanel(new GridLayout(1, 0));
			this.panelGlobal.add(buttonsPanel, BorderLayout.NORTH);
		}
		if (voirObjet == null) {
			voirObjet = new JButton(BoutonsString.VOIR_OBJET);
			ecouteur = new BoutonJeu(voirObjet, this);
			voirObjet.addActionListener(ecouteur);
			voirObjet.addKeyListener(new ActionClavier(this));
			buttonsPanel.add(voirObjet);

			setVisible(true);
			buttonsPanel.updateUI();
		} else if (!voirObjet.isEnabled()) {
			voirObjet = new JButton(BoutonsString.VOIR_OBJET);
			ecouteur = new BoutonJeu(voirObjet, this);
			voirObjet.addActionListener(ecouteur);
			voirObjet.addKeyListener(new ActionClavier(this));
			buttonsPanel.add(voirObjet);

			setVisible(true);
			buttonsPanel.updateUI();
		}

	}

	public void cibleMort(JButton b) {
		if (j.getSalle().getListePersonnages().size() > 1) {
			combat();
		}
		if (fCombat.ennemis.size() > 0) {
			Monstre m = (Monstre) fCombat.ennemis.stream().filter(x -> ((Monstre) x).bouton == b).findFirst().get();
			fCombat.ennemis.remove(m);
			fCombat.getCibles();
		} else {
			fCombat.dispatchEvent(new WindowEvent(fCombat, WindowEvent.WINDOW_CLOSING));
			buttonsPanel.remove(combat);
			combat.setEnabled(false);

			buttonsPanel.repaint();
			buttonsPanel.updateUI();
		}

		affiche();
		repaint();
	}

	public void objetUtilise(JButton b) {
		if (j.getSalle().getListeObjet().size() > 1) {
			voirObjet();
		}
		if (fObjet.objets.size() > 0) {
			AbstractObjet o = fObjet.objets.stream().filter(x -> x.bouton == b).findFirst().get();
			fObjet.objets.remove(o);
			fObjet.getObjets();

		} else {
			fObjet.dispatchEvent(new WindowEvent(fObjet, WindowEvent.WINDOW_CLOSING));
			buttonsPanel.remove(voirObjet);
			voirObjet.setEnabled(false);

			buttonsPanel.repaint();
			buttonsPanel.updateUI();
		}

		affiche();
		repaint();

	}

	public void affiche() {


		
		panelTxt.removeAll();
		panelTxt.repaint();

		String regarderTxt = j.regarder().toString().replace(".", "\n");
		String statsTxt = j.apresAttaque().toString();

		regarder.setEditable(false);
		regarder.setText(regarderTxt);
		regarder.setFocusable(false);
		regarder.setBackground(Color.LIGHT_GRAY);
		regarder.setFont(Font.decode("Trebuchet MS"));

		stats.setEditable(false);
		stats.setText(statsTxt);
		stats.setFocusable(false);
		stats.setBackground(Color.LIGHT_GRAY);
		stats.setFont(Font.decode("Trebuchet MS"));

		panelTxt.add(regarder);
		panelTxt.add(stats);
		panelGlobal.add(panelTxt, BorderLayout.WEST);

	}

	public void regarder() {
		if (j != null) {
			if (j.listeActions.contains(Joueur.ATTAQUER_STRING)) {
				combat();
			}
			else {
				if (combat!=null) {
					buttonsPanel.remove(combat);
					combat.setEnabled(false);
				}
			}
			if (j.listeActions.contains(Joueur.UTILISER_STRING)) {
				voirObjet();
			}else {
				if (voirObjet!=null) {
					buttonsPanel.remove(voirObjet);
					voirObjet.setEnabled(false);
				}
			}
			panelGlobal.repaint();
			panelGlobal.updateUI();
		}
	}

	public void victoire() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		if (fCombat!=null) {
			fCombat.dispatchEvent(new WindowEvent(fCombat, WindowEvent.WINDOW_CLOSING));
		}
		if (fObjet!=null) {
			fObjet.dispatchEvent(new WindowEvent(fObjet, WindowEvent.WINDOW_CLOSING));
		}
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		new FenetreVictoire(this);

	}

	public void defaite() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		if (fCombat!=null) {
			fCombat.dispatchEvent(new WindowEvent(fCombat, WindowEvent.WINDOW_CLOSING));
		}
		if (fObjet!=null) {
			fObjet.dispatchEvent(new WindowEvent(fObjet, WindowEvent.WINDOW_CLOSING));
		}
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		new FenetreDefaite(this);

	}
}
