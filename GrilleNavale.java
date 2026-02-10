package batailleNavale;

public class GrilleNavale {
	private Navire[] navires;
	private int nbNavires;
	private int taille;
	private Coordonnee[] tirsRecus;
	private int nbTirsRecus;

	public GrilleNavale(int taille, int[] taillesNavires) {
		if (taille <= 0 || taille > 26)
			throw new IllegalArgumentException("taille hors limites !");
		if (taillesNavires == null || taillesNavires.length == 0)
			throw new IllegalArgumentException("aucun navire !");
		for (int i = 0; i < taillesNavires.length; i++) {
	        if (taillesNavires[i] <= 0) 
	        	throw new IllegalArgumentException("taille de navire <= 0 interdite !");
	        if (taillesNavires[i] > taille) 
	        	throw new IllegalArgumentException("taille de navire > taille grille !");
	    }

		this.taille = taille;
		this.navires = new Navire[taillesNavires.length];
		nbNavires = 0;
		tirsRecus = new Coordonnee[taille * taille];
		nbTirsRecus = 0;
//		placementAuto(taillesNavires);
	}

	public GrilleNavale(int taille, int nbNavires) {
		if (taille <= 0 || taille > 26) 
			throw new IllegalArgumentException("taille hors limites !");
		if (nbNavires < 0) 
			throw new IllegalArgumentException("nbNavires < 0 interdit !");
		if (nbNavires > taille * taille) 
			throw new IllegalArgumentException("trop de navires pour la grille !");
		this.taille = taille;
		navires = new Navire[nbNavires];
		this.nbNavires = 0;
		tirsRecus = new Coordonnee[taille * taille];
		nbTirsRecus = 0;
	}

	public String toString() {
		StringBuilder sB = new StringBuilder();
		sB.append("  ");
		for (int j = 0; j < taille; j++)
			sB.append(" ").append((char) (j + 'A'));
		sB.append('\n');
		for (int i = 0; i < taille; i++) {
			if (i < 9)
				sB.append(' ').append(i + 1);	
			else 
				sB.append(i + 1);
			for (int j = 0; j < taille; j++) {
				Coordonnee c = new Coordonnee(i, j);
				boolean tire = false;
				for (int t = 0; t < nbTirsRecus; t++) {
					if (tirsRecus[t].equals (c)) {
						tire = true;
						break;
					}
				}
				boolean occupe = false;
				boolean touche = false;
				for (int n = 0; n < nbNavires; n++) {
					if (navires[n].contient(c)) {
						occupe = true;
						if (navires[n].estTouche(c))
							touche = true;
						break;
					}
				}
				char symbole = '.';
				if (tire) {
					if (touche)
					    symbole = 'X';
					else
					    symbole = 'O';
				}
				else if (occupe)
				    symbole = '#';
				else
				    symbole = '.';
				sB.append(' ').append(symbole);
			}
			sB.append('\n');
		}
		return sB.toString();
	}

	public int getTaille() {
		return taille;
	}

//	public boolean getEstDansTirsRecus(Coordonnee c) {
//	    return estDansTirsRecus(c);
//	}

	public boolean ajouteNavire(Navire n) {
		if (n == null) 
//	        throw new IllegalArgumentException("Navire nul interdit");
			return false;
		if (nbNavires >= navires.length) 
			return false;
		// Vérification limites de this
		if (!estDansGrille(n.getDebut()) || !estDansGrille(n.getFin()))
			return false;
		// Vérification du chevauchement
		for (int i = 0; i < nbNavires; i++) {
			if (n.touche(navires[i]) || n.chevauche(navires[i]))
				return false;
		}
		// Ajout du navire
		navires[nbNavires++] = n;
		return true;
	}

	public void placementAuto(int[] taillesNavires) {
		if (taillesNavires == null) 
	        throw new IllegalArgumentException("tableau des tailles nul");
	
		for (int i = 0; i < taillesNavires.length; i++) {
			boolean place = false;
			int essais = 0;
			
			while (!place && essais < 1000) {
				int ligne; //ligne de debut du navire
				int colonne; //colonne de debut du navire
				boolean vertical = Math.random() < 0.5; //chance moitié de 1, donc horizontal si > 0.5
				if (vertical) {
					// pour que le navire tienne verticalement dans la grille
				    ligne = (int)(Math.random() * (taille - taillesNavires[i] + 1));
				    colonne = (int)(Math.random() * taille);
				} else {
					// pour que le navire tienne horizontalement dans la grille
				    ligne = (int)(Math.random() * taille);
				    colonne = (int)(Math.random() * (taille - taillesNavires[i] + 1));
				}
				Navire n = new Navire(new Coordonnee(ligne, colonne), taillesNavires[i], vertical);
				place = ajouteNavire(n);
				essais++;
			}
			if (!place) 
				return;
//				throw new IllegalStateException("Impossible de placer le navire de taille" + taillesNavires[i]);
		}
	}

	private boolean estDansGrille(Coordonnee c) {
		return c.getLigne() >= 0 && c.getLigne() < taille &&
				c.getColonne() >= 0 && c.getColonne() < taille;
	}

	private boolean estDansTirsRecus(Coordonnee c) {
//		if (c == null) 
//			return false;
		for (int i = 0; i < nbTirsRecus; i++) {
			if (tirsRecus[i].equals(c))
				return true;
		}
		return false;
	}

	private boolean ajouteDansTirsRecus(Coordonnee c) {
		if (c == null) 
//			throw new IllegalArgumentException("coordonnée nulle");
			return false;
		
		if (nbTirsRecus >= tirsRecus.length)
			return false;
		
		if (!estDansGrille(c) || estDansTirsRecus(c))
			return false;
		tirsRecus[nbTirsRecus++] = c;
		return true;
	}

	public boolean recoitTir(Coordonnee c) {
//		if (c == null) 
//			throw new IllegalArgumentException("coordonnée nulle");
		
		if (!estDansGrille(c))
			return false;
		
		ajouteDansTirsRecus(c);
		
		for (int i = 0; i < nbNavires; i++) {
			if (navires[i].recoitTir(c))
				return true;	
		}
		return false; // tir valide mais à l'eau
	}

	public boolean estTouche(Coordonnee c) {
		if (c == null) 
//			throw new IllegalArgumentException("coordonnée nulle");
			return false;
		
		for (int i = 0; i < nbNavires; i++) {
			if (navires[i].estTouche(c))
				return true;
		}
		return false;
	}

	public boolean estALEau(Coordonnee c) {
		if (c == null) 
			throw new IllegalArgumentException("coordonnée nulle");
		
//		for (int i = 0; i < nbTirsRecus; i++) {
//			if (tirsRecus[i].equals(c)) { // verifie si a ete tire
//				for (int j = 0; j < nbNavires; j++) {
////					if (navires[j].contient(c)) {
//					if (navires[j].estTouche(c))
//						return false; // c'est pas l'eau c'est un navire qui est touche
//				}
//				return true; // tire mais aucun navir .
//			}
//		}
//		return false; // c'est pas un tir recu
		if (!estDansTirsRecus(c))
			return false;
		return !estTouche(c);
	}

	public boolean estCoule(Coordonnee c) {
//		if (c == null) 
//			throw new IllegalArgumentException("coordonnée nulle");
		
		for (int i = 0; i < nbNavires; i++) {
//			if (navires[i].estTouche(c) && navires[i].estCoule()) // touche en c et coule
//				return true;
			if (navires[i].estTouche(c))
				return navires[i].estCoule();
		}
		return false;
	}

	public boolean perdu() {
		for (int i = 0; i < nbNavires; i++) {
			if (!navires[i].estCoule()) {
				return false;
			}
		}
		return true; // tous coules.
	}
}
