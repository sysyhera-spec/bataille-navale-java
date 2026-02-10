package batailleNavale;

public class JoueurAuto extends JoueurAvecGrille {
	private Coordonnee[] tirs; // tableau des coups déjà joués
	private int nbTirs; // compteur de coups joués

	// Constructeur
	public JoueurAuto(GrilleNavale g, String nom) {
		super(g, nom);
		if (g == null)
			throw new IllegalArgumentException("La grille ne peut pas être nulle");
		tirs = new Coordonnee[getTailleGrille() * getTailleGrille()]; // taille max de la grille
		nbTirs = 0;
	}

	// Costructeur pour joueur autom
	public JoueurAuto(GrilleNavale g) {
//	        super(g);
		this(g, "Ordinateur");
	}

	protected void retourAttaque(Coordonnee c, int etat) {
		if (c == null)
			throw new IllegalArgumentException("coordonnée nulle");

		switch (etat) {
		case Joueur.TOUCHE:
			break;
		case Joueur.COULE:
			break;
		case Joueur.A_L_EAU:
			break;
		case Joueur.GAMEOVER:
			break;
		default:
			throw new IllegalArgumentException("etat invalide");
		}
	}

	protected void retourDefense(Coordonnee c, int etat) {

	}

	public Coordonnee choixAttaque() { // Choisi ses tirs de maniere alea
//	        int ligne = (int) (Math.random() * getTailleGrille());
//	        int colonne = (int) (Math.random() * getTailleGrille());
//	        return new Coordonnee(ligne, colonne);

		Coordonnee c;
		boolean dejaTire;

		do {
			int ligne = (int) (Math.random() * getTailleGrille());
			int colonne = (int) (Math.random() * getTailleGrille());

			if (ligne < 0 || ligne >= 26 || colonne < 0 || colonne >= 26)
				throw new IllegalArgumentException("Coordonnée générée hors limites");

			c = new Coordonnee(ligne, colonne);

			dejaTire = false;
			for (int i = 0; i < nbTirs; i++) {
				if (tirs[i].equals(c)) {
					dejaTire = true;
					break;
				}
			}
		} while (dejaTire);

		tirs[nbTirs++] = c;
		return c;
	}
}
