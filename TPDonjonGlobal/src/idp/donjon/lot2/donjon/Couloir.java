
/* 
 * Un couloir représente une entité virtuelle de transition entre deux salles. il est caractérisé
 *  par son nom(NORD,SUD,EST,OUEST) et par les salles qu'il relie.
 */

package idp.donjon.lot2.donjon;

public class Couloir {

	private Salle salle1;
	private Salle salle2;
	private String nomRelatif;

	public Couloir(Salle salle1, Salle salle2) {
		if (salle1 == null) {
			salle1 = salle2;
		} else {
			this.salle1 = salle1;
		}
		this.salle2 = salle2;
	}
	
	public String toString() {
		return "Couloir "+nomRelatif;
	}

	public Salle getSalle1() {
		return salle1;
	}

	public void setSalle1(Salle salle1) {
		this.salle1 = salle1;
	}

	public Salle getSalle2() {
		return salle2;
	}

	public void setSalle2(Salle salle2) {
		this.salle2 = salle2;
	}

	public String getNomRelatif() {
		return nomRelatif;
	}

	public void setNomRelatif(String nomRelatif) {
		this.nomRelatif = nomRelatif;
	}
	
}
