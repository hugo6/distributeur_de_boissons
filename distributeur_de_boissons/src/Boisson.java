
public class Boisson {
/**
 * Classe représentant une boisson dans un distributeur
 * @libelle : libellé de la boisson
 * @prix : prix de la boisson en centime d'euro
 */
	private String libelle;
	private int prix;
	
	public Boisson (String libelle, int prix) {
		this.libelle=libelle;
		this.prix=prix;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public int getPrix() {
		return prix;
	}

	public void setPrix(int prix) {
		this.prix = prix;
	}
	
}
