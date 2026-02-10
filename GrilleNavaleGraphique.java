package batailleNavale;

import java.awt.*;

public class GrilleNavaleGraphique extends GrilleNavale {
	private GrilleGraphique grille;

	// Constructeur
	public GrilleNavaleGraphique(int taille) throws IllegalArgumentException {
		super(taille, 6);
		this.grille = new GrilleGraphique(taille);
	}

	public GrilleGraphique getGrilleGraphique() {
		return this.grille;
	}

	public boolean ajouteNavire(Navire n) throws IllegalArgumentException {
		boolean resultat = super.ajouteNavire(n);
		if (resultat) {
			if (n.getDebut().getLigne() < getTaille() && n.getDebut().getColonne() < getTaille() &&
					n.getFin().getLigne() < getTaille() && n.getFin().getColonne() < getTaille())
				grille.colorie(n.getDebut(), n.getFin(), Color.GREEN);
			return true;
		}
		return false;
	}

	public boolean recoitTir(Coordonnee c) {
		boolean touche = super.recoitTir(c);
		if (c.getLigne() >= 0 && c.getLigne() < getTaille() && 
				c.getColonne() >= 0 && c.getColonne() < getTaille()) {
			if (touche) {
				grille.colorie(c, Color.RED);
			} else {
				grille.colorie(c, Color.BLUE);
			}
		} else {
			System.out.println("Coordonnée hors grille : " + c);
		}
		return touche;
	}
}
