package batailleNavale;

public abstract class JoueurAvecGrille extends Joueur {
	private GrilleNavale grille;

	public JoueurAvecGrille(GrilleNavale g, String nom) {
		super(g.getTaille(), nom);
		this.grille = g;
	}

	public JoueurAvecGrille(GrilleNavale g) {
		super(g.getTaille());
		this.grille = g;
	}

	public int defendre(Coordonnee c) {
		if (c == null) {
	        throw new IllegalArgumentException("Coordonnée nulle");
	    }
	    
		if (!grille.recoitTir(c))
			return A_L_EAU;
		if (grille.estCoule(c)) {
	        if (grille.perdu()) {
	            return GAMEOVER;  // tous les navires sont coulés
	        }
	        return COULE;         // navire touché et coulé
	    }
	    return TOUCHE; // au cas où, par sécurité
	}

	public GrilleNavale getGrille() {
		return grille;
	}

}
