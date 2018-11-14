
/*	
 * Cette classe sert à définir le comportement du joueur.
 * Le joueur est caractérisé par la salle et le donjon qui le contiennent et son nom.
 */

package idp.donjon.lot4.personnages;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import idp.donjon.lot4.donjon.Couloir;
import idp.donjon.lot4.donjon.Donjon;
import idp.donjon.lot4.donjon.Salle;

public class Joueur extends AbstractPersonnage {

	public List<String> listeActions;
	public static final String REGARDER_STRING = "Regarder";
	public static final String ATTAQUER_STRING = "Attaquer";
	public static final String DEPLACER_STRING = "Se déplacer";
	public static final String UTILISER_STRING = "Utiliser";
	public static final String AFFICHE_STRING = "Afficher détails";
	public static final String QUITTER_STRING = "QUITTER";
	
	private final int INIT_PV_MAX=100;
	private final int INIT_FORCE=12;
	private final int INIT_OR=5;
	
	public Joueur(Salle salle, Donjon d, String nom) {
		super(salle, d);
		pvMax = INIT_PV_MAX;
		pv = pvMax;
		force = INIT_FORCE;
		or = INIT_OR;
		setNom(nom);
		salle.getListePersonnages().add(this);
		tour = true;
		action();
	}

	public void debutJeu() {
		do {
			action();
		} while (isTour());
	}
	

	public void action() {
		
		listeActions = new ArrayList<>();

		listeActions.add(REGARDER_STRING);
		if (salle.getListePersonnages().size() > 1) {
			listeActions.add(ATTAQUER_STRING);
		}
		if (salle.getListePersonnages().size() == 1) {
			listeActions.add(DEPLACER_STRING);
		}
		if (!salle.getListeObjet().isEmpty()) {
			listeActions.add(UTILISER_STRING);
		}
		listeActions.add(AFFICHE_STRING);
		listeActions.add(QUITTER_STRING);

	}

	public StringBuilder regarder() {
		
		StringBuilder res=new StringBuilder();
		
		res.append(salle);
		res.append(System.getProperty("line.separator"));
		res.append(System.getProperty("line.separator"));
		
		salle.getListeObjet().stream().forEach(x->res.append(x+"."));
		res.append(System.getProperty("line.separator"));
		res.append(System.getProperty("line.separator"));
		
		salle.getListePersonnages().stream().filter(x -> x != this).forEach(x->res.append(x+"."));
		
		return res;
	}
	

	public boolean seDeplacer(String direction) {
		
		if (salle.getListePersonnages().size() < 2) {
			
		Optional<Couloir> dirAPrendre=null;
		switch(direction) {
		case "Gauche":
			dirAPrendre=salle.getCouloirs().stream().filter(x->
			x.getNomRelatif().equals("OUEST")).findFirst();
			break;
		case "Droite":
			dirAPrendre=salle.getCouloirs().stream().filter(x->
			x.getNomRelatif().equals("EST")).findFirst();
			break;
		case "Haut":
			dirAPrendre=salle.getCouloirs().stream().filter(x->
			x.getNomRelatif().equals("NORD")).findFirst();
			break;
		case "Bas":
			dirAPrendre=salle.getCouloirs().stream().filter(x->
			x.getNomRelatif().equals("SUD")).findFirst();
			break;
		}
		if (dirAPrendre.isPresent()) {
			salle.getListePersonnages().remove(this);
			setSalle(dirAPrendre.get().getSalle2());
			salle.getListePersonnages().add(this);
			declencherMovement();
			return true;
		}
		else return false;
		}
		else {
			return false;
		}
	}

	public String toString() {
		return "Joueur " + nom;
	}


	@Override
	public int hashCode() {
		return getOr();
	}

	@Override
	public AbstractPersonnage choixCible() {
		// TODO Auto-generated method stub
		return null;
	}

}
