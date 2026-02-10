package batailleNavale;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class JoueurGraphique extends JoueurAvecGrille {
	private GrilleGraphique grilleTirs;

	public JoueurGraphique(GrilleNavaleGraphique grilleDefense, GrilleGraphique grilleTirs, String nom) {
		super(grilleDefense, nom);
		// Vérifier la taille
		if (grilleDefense == null || grilleTirs == null) {
			throw new IllegalArgumentException(
					"Erreur : Les grilles ne peuvent pas être nulles pour un JoueurGraphique.");
		}

		this.grilleTirs = grilleTirs;
	}

	public JoueurGraphique(GrilleNavaleGraphique grilleDefense, GrilleGraphique grilleTirs) {
		this(grilleDefense, grilleTirs, "Joueur");
	}

	public Coordonnee choixAttaque() {
//		return this.grilleTirs.getCoordonneeSelectionnee();
		Coordonnee c = this.grilleTirs.getCoordonneeSelectionnee();
		if (c == null)
			return null;

		// Vérifie que c est dans la grille via la taille de la grille de défense
		int taille = this.getGrille().getTaille(); // grille est l'attribut de JoueurAvecGrille
		if (c.getLigne() < 0 || c.getLigne() >= taille || c.getColonne() < 0 || c.getColonne() >= taille) {
			System.err.println("Coordonnée choisie hors grille : " + c);
			return null;
		}

		return c;
	}

	protected void retourDefense(Coordonnee c, int etat) {
//		Color couleur = etat == A_L_EAU ? Color.BLUE : Color.RED; 
//		grilleTirs.colorie(c, couleur);
		switch (etat) {
		case TOUCHE:
			JOptionPane.showMessageDialog(grilleTirs, "Aïe ! Votre navire a été touché en " + c);
			break;
		case COULE:
			JOptionPane.showMessageDialog(grilleTirs, "Catastrophe ! Votre navire a été coulé en " + c);
			break;
		case GAMEOVER:
			JOptionPane.showMessageDialog(grilleTirs, "Perdu ! Toute votre flotte a été coulée.");
			break;
		}
	}

	protected void retourAttaque(Coordonnee c, int etat) {
		int taille = getGrille().getTaille(); // taille de la grille logique
		if (c.getLigne() < 0 || c.getLigne() >= taille || c.getColonne() < 0 || c.getColonne() >= taille) {
			System.err.println("Coordonnée hors grille : " + c);
			return; // ignore le tir
		}

		Color couleur = etat == A_L_EAU ? Color.BLUE : Color.RED;
		grilleTirs.colorie(c, couleur);
		switch (etat) {
		case TOUCHE:
			JOptionPane.showMessageDialog(grilleTirs, "Vous avez touché un navire en " + c);
			break;
		case COULE:
			JOptionPane.showMessageDialog(grilleTirs, "Vous avez coulé un navire en " + c);
			break;
		case GAMEOVER:
			JOptionPane.showMessageDialog(grilleTirs, "Vous avez gagné!!!");
		}
	}

}
