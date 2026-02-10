package batailleNavale;

public class Navire {
	private Coordonnee debut;
	private Coordonnee fin;
	private Coordonnee[] partiesTouchees;
	private int nbTouchees;

	public Navire(Coordonnee debut, int longueur, boolean estVertical) {
//		if (debut == null)
//			throw new IllegalArgumentException("Coordonnée de début nulle");
//		if (longueur <= 0 || longueur > 26)
//			throw new IllegalArgumentException("Taille du navire invalide");

		this.debut = debut;
		// principe si on est disposé verticalement on change la ligne sinon on change
		// la colonne
		if (estVertical) {
			this.fin = new Coordonnee(debut.getLigne() + longueur - 1, debut.getColonne());
		} else {
			this.fin = new Coordonnee(debut.getLigne(), debut.getColonne() + longueur - 1);
		}
		this.partiesTouchees = new Coordonnee[longueur];
		this.nbTouchees = 0;
	}

	public String toString() {
		int longueur = 0;
		if (debut.getLigne() == fin.getLigne()) { // on est disposé horizontalement
			longueur = fin.getColonne() - debut.getColonne() + 1;
			return "Navire (" + debut + ", " + longueur + ", horizontal)";
		} else { // on est disposé verticalement
			longueur = fin.getLigne() - debut.getLigne() + 1;
			return "Navire (" + debut + ", " + longueur + ", vertical)";
		}
	}

	public Coordonnee getDebut() {
		return debut;
	}

	public Coordonnee getFin() {
		return fin;
	}

	public boolean contient(Coordonnee c) {
//		if (c == null) {
//			throw new IllegalArgumentException("La coordonnée ne peut pas être nulle");
//		}
		
		for (int i = debut.getLigne(); i <= fin.getLigne(); i++) { 
			for (int j = debut.getColonne(); j <= fin.getColonne(); j++ ) {
				Coordonnee cn = new Coordonnee(i, j);
				if (c.equals(cn))
					return true;
			}
		}
		return false;
		
//		return c.getLigne() >= debut.getLigne() && c.getLigne() <= fin.getLigne() &&
//		           c.getColonne() >= debut.getColonne() &&  c.getColonne() <= fin.getColonne();
	}

	public boolean touche(Navire n) {
//		if (n == null) {
//			throw new IllegalArgumentException("Navire nul interdit");
//		}
	    
		for (int i = debut.getLigne(); i <= fin.getLigne(); i++) {
			for (int j = debut.getColonne(); j <= fin.getColonne(); j++) {
				Coordonnee c = new Coordonnee (i, j);
				// parcours de toutes les coordonnées du navire
				for (int k = n.debut.getLigne(); k <= n.fin.getLigne(); k++) {
					for (int l = n.debut.getColonne(); l <= n.fin.getColonne(); l++) {
//						Coordonnee cn = new Coordonnee (k, l);
//						if (c.voisine(cn)) // ne marche pas car le test peux aboutir sur 
//							return true;	//des coordonnées en dehors de 0 et taille 
											//si le navire se trouve sur le bord
						
						// Calcul de la distance entre la case (i,j) et la case (k,l)
	                    int diffLigne = Math.abs(i - k);
	                    int diffCol = Math.abs(j - l);
	                    // Si la distance totale est de 1, ils sont voisins (côte à côte)
	                    if ((diffLigne == 1 && diffCol == 0) || (diffLigne == 0 && diffCol == 1))
	                        return true;
					}
				}
			}
		}		
		return false;
		
		//METHODE PLUS SIMPLE ET PERFORMANTE
		// Si ils se chevauchent, ils se "touchent" techniquement (selon ton code original)
//	    if (this.chevauche(n)) return true;
	 // Sinon, on vérifie si un navire est adjacent à l'autre
	    // On peut réutiliser la logique des bornes avec une marge de 1
//	    return !(n.fin.getLigne() < this.debut.getLigne() - 1 ||
//	             n.debut.getLigne() > this.fin.getLigne() + 1 ||
//	             n.fin.getColonne() < this.debut.getColonne() - 1 ||
//	             n.debut.getColonne() > this.fin.getColonne() + 1);
	}

	public boolean chevauche(Navire n) {
//		if (n == null)
//			throw new IllegalArgumentException("navire nul");
	
		for (int i = debut.getLigne(); i <= fin.getLigne(); i++) {
			for (int j = debut.getColonne(); j <= fin.getColonne(); j++) {
				Coordonnee c = new Coordonnee (i, j);
				if (n.contient(c))
					return true;
			}
		}
		return false;
		
		//METHODE PLUS SIMPLE ET PERFORMANTE
//		return !(n.fin.getLigne() < this.debut.getLigne() ||
//	             n.debut.getLigne() > this.fin.getLigne() ||
//	             n.fin.getColonne() < this.debut.getColonne() ||
//	             n.debut.getColonne() > this.fin.getColonne());
	}

	public boolean recoitTir(Coordonnee c) {
//		if (c == null)
//			throw new IllegalArgumentException("coordonnée nulle");
		
		if (this.contient(c)) {
			boolean dejaTire = false;
			for (int i = 0; i < nbTouchees; i++) {
				if (partiesTouchees[i].equals(c)) {
					dejaTire = true;
					break;
				}
			}
			if (!dejaTire) {
//			if (!estTouche(c))		
				partiesTouchees[nbTouchees++] = c;
			}
			return true;
		}
		
//		if (estTouche(c)) return false;
//        for (int i = debut.getLigne(); i <= fin.getLigne(); i++) {
//            for (int j = debut.getColonne(); j <= fin.getColonne(); j++) {
//                if (c.equals(new Coordonnee(i, j))) {
//                    partiesTouchees[nbTouchees++] = c;
//                    return true;
//                }
//            }
//        }
        
		return false;
//		if (nbTouchees >= partiesTouchees.length) {
//			throw new IllegalStateException("Toutes les parties du navire sont déjà touchées");
//		}
	}

	public boolean estTouche(Coordonnee c) {
//		if (c == null)
//			throw new IllegalArgumentException("coordonnée nulle");
		
		for (int i = 0; i < nbTouchees; i++) {
            if (partiesTouchees[i].equals(c)) return true;
        }
		
		return false;
	}

	public boolean estTouche() {
		return nbTouchees > 0;
	}

	public boolean estCoule() {
		return nbTouchees == partiesTouchees.length;
	}

	public int getNbTouchees() {
		return nbTouchees;
	}
}
