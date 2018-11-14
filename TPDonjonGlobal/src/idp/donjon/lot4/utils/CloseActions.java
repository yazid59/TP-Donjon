package idp.donjon.lot4.utils;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import idp.donjon.lot4.affichage.fenetreprincipale.AbstractMyFenetreP;
import idp.donjon.lot4.affichage.jeu.MyFenetre;

public class CloseActions implements WindowListener {

	MyFenetre fenetre;
	AbstractMyFenetreP fenetreP;
	JFrame fenetreParent;
	public CloseActions(AbstractMyFenetreP f) {
		fenetreP=f;
		fenetreParent=f.fenetrePrincipale;
	}
	public CloseActions(MyFenetre f) {
		fenetre=f;
		fenetreParent=fenetre.fJeu;
	}
	@Override
	public void windowOpened(WindowEvent e) {
		if (fenetreParent!=null)
		fenetreParent.setFocusableWindowState(false);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		if (fenetreParent!=null) {
			fenetreParent.requestFocus();
			fenetreParent.setFocusableWindowState(true);
		}
//		fenetre.fJeu.requestFocus();
//		fenetre.fJeu.setFocusableWindowState(true);
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
