
/*	
 * Cette classe sert à définir le comportement du Monstre classique.
 * Ce Monstre est caractérisé par la salle et le donjon qui le contiennent.
 */
package idp.donjon.lot1.personnages;


import idp.donjon.lot1.donjon.Donjon;
import idp.donjon.lot1.donjon.Salle;
import idp.donjon.lot1.utils.MyRandom;

public class Monstre extends AbstractPersonnage{

	private static final int FORCE_MAX=10;
	private static final int STAT_INIT_MINIMUM=70;
	private static final int STAT_INIT_MAXIMUM=100;

	public Monstre(Salle salle, Donjon d) {
		super(salle, d);
		pvMax=25;
		setPvInitial();
		setForceInitial();
		or=8;
		
		randNamePerso();


		tour=false;
	}

	
	public void randNamePerso() {
		String debutNom="al ar ca ce do du di da fi fa fe ga lo li ra ta";
		String milieuNom="ch g gh mm nn ph qu r s sh t z";
		String finNom="an ar es ie ur us uth ";
		String titre="la.terreur le.destructeur l'immortel le.sanguinaire le.cruel le.sauvage";
		//replace points par espace
		
		String[] debutNoms=debutNom.split(" ");
		String[] milieuNoms=milieuNom.split(" ");
		String[] finNoms=finNom.split(" ");
		String[] titres=titre.split(" ");
		
		int chance;
		chance = MyRandom.rnd.nextInt(debutNoms.length);
		nom+=debutNoms[chance];
		chance = MyRandom.rnd.nextInt(milieuNoms.length);
		nom+=milieuNoms[chance];
		chance = MyRandom.rnd.nextInt(finNoms.length);
		nom+=finNoms[chance];
		
		chance = MyRandom.rnd.nextInt(titres.length);
		nom+=" "+titres[chance].replace(".", " ");
		setNom(nom);
	}

	public void action() {
		attaquer(choixCible());
	}

	public AbstractPersonnage choixCible() {
		setCible(Joueur.getJoueur(salle.getListePersonnages()));
		return getCible();
	}

	public void setPvInitial() {
		int x = (STAT_INIT_MINIMUM+MyRandom.rnd.nextInt(STAT_INIT_MAXIMUM-STAT_INIT_MINIMUM));
		setPv((int)((0.01*x)*getPvMax()));
	}
	public void setForceInitial() {
		int x = (STAT_INIT_MINIMUM+MyRandom.rnd.nextInt(STAT_INIT_MAXIMUM-STAT_INIT_MINIMUM));
		setForce((int)((0.01*x)*FORCE_MAX));
	}

	public String toString() {
		return getNom();
	}



	@Override
	public void apresAttaque() {
		System.out.println(this +" vous attaque pour "+force+" dégats.");
		System.out.println("Il vous reste  "+getCible().getPv()+" points de vie.");
	}



}
