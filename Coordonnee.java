package batailleNavale;

public class Coordonnee implements Comparable<Coordonnee> {
	private int ligne;
	private int colonne;

	public Coordonnee(int ligne, int colonne) { // coordonnées réelles des grilles en java
		if (ligne < 0 || ligne >= 26 || colonne < 0 || colonne >= 26) {
			throw new IllegalArgumentException("Coordonnée invalide");
		}
		this.ligne = ligne;
		this.colonne = colonne;
	}

	public Coordonnee(String s) { // coordonnées au format du système bataille navale (ex:B6)
		if (s == null || s.length() < 2 || s.length() > 3) {
			throw new IllegalArgumentException("Format invalide, exemple à saisir : B6");
		}
		char lettre = Character.toUpperCase(s.charAt(0));
		if (lettre < 'A' || lettre > 'Z') {
			throw new IllegalArgumentException("Lettre invalide");
		}
		int nombre;
		try {
			nombre = Integer.parseInt(s.substring(1));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Nombre invalide");
		}
		if (nombre < 1 || nombre > 26) {
			throw new IllegalArgumentException("Nombre hors limites");
		}
		ligne = nombre - 1;
		colonne = lettre - 'A';
	}

	public String toString() { // lire au format coordonnées bataille navale (ex:B6)
		String res = "";
		res = res + (char) (colonne + 'A') + (ligne + 1);
		return res;
	}

	public int getColonne() {
		return colonne;
	}

	public int getLigne() {
		return ligne;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof Coordonnee)) 
			return false;
		Coordonnee x = (Coordonnee) obj;
		if (this.colonne == x.colonne && this.ligne == x.ligne) {
			return true;
		}
		return false;
	}

	public boolean voisine(Coordonnee c) {
//		if (c == null)
//			throw new IllegalArgumentException("Coordonnee nulle interdite !");
		if (this.ligne == c.ligne) {
			if (this.colonne == c.colonne - 1 || this.colonne == c.colonne + 1)
				return true;
		}
		if (this.colonne == c.colonne) {
			if (this.ligne == c.ligne - 1 || this.ligne + 1 == c.ligne)
				return true;
		}
		return false;
	}

	public int compareTo(Coordonnee c) {
//		if (c == null)
//			throw new IllegalArgumentException("Coordonnee nulle interdite !");
		if (this.ligne < c.ligne)
			return -1;
		if (this.ligne > c.ligne)
			return 1;
		if (this.ligne == c.ligne) {
			if (this.colonne < c.colonne)
				return -1;
			if (this.colonne > c.colonne)
				return 1;
		}	
		return 0;
	}
}
