package batailleNavale;

import java.util.Scanner;

public class JoueurTexte extends JoueurAvecGrille {
	private Scanner sc;

	public JoueurTexte(GrilleNavale g, String nom) {
		super(g, nom);
		sc = new Scanner(System.in);
	}

	public JoueurTexte(GrilleNavale g) {
		super(g);
		sc = new Scanner(System.in);
	}

	protected void retourAttaque(Coordonnee c, int etat) {
		if (c == null)
			throw new IllegalArgumentException("coordonnée nulle");

		System.out.println("Vous avez attaqué " + c);
		switch (etat) {
		case Joueur.TOUCHE:
			System.out.println("Le navire de l'adversaire est touché !");
			break;
		case Joueur.COULE:
			System.out.println("Le navire de l'adversaire est  coulé !");
			break;
		case Joueur.A_L_EAU:
			System.out.println("Votre tir est tombé dans l'eau");
			break;
		case Joueur.GAMEOVER:
			System.out.println("Game over : votre adversaire est vaincu");
			break;
		default:
			throw new IllegalArgumentException("etat invalide");
		}
	}

	protected void retourDefense(Coordonnee c, int etat) {
		if (c == null)
			throw new IllegalArgumentException("coordonnée nulle");

		System.out.println("Votre adversaire a tiré dans " + c);
		switch (etat) {
		case Joueur.TOUCHE:
			System.out.println("Malheureusement, votre navire a été touché");
			System.out.println(this.getGrille().toString());
			break;
		case Joueur.COULE:
			System.out.println("Quel dommage ! Votre navire a coulé");
			System.out.println(this.getGrille().toString());
			break;
		case Joueur.A_L_EAU:
			System.out.println("ça a passé ! Le tir a atterri dans l'eau");
			System.out.println(this.getGrille().toString());
			break;
		case Joueur.GAMEOVER:
			System.out.println("game over : сette fois, vous avez perdu");
			System.out.println(this.getGrille().toString());
			break;
		default:
			throw new IllegalArgumentException("etat invalide");
		}
	}

	public Coordonnee choixAttaque() {
	    while (true) {
	        System.out.println("Entrez les coordonnées à attaquer (ex : D5) : ");
	        String saisie = sc.next().toUpperCase();

	        // Vérification longueur
	        if (saisie.length() < 2 || saisie.length() > 3) {
	            System.out.println("Coordonnée invalide !");
	            continue;
	        }

	        char colonneChar = saisie.charAt(0);

	        // Vérification lettre
	        if (colonneChar < 'A' || colonneChar > 'Z') {
	            System.out.println("Coordonnée invalide !");
	            continue;
	        }

	        int ligne;
	        try {
	            ligne = Integer.parseInt(saisie.substring(1)) - 1;
	        } catch (NumberFormatException e) {
	            System.out.println("Coordonnée invalide !");
	            continue;
	        }

	        int colonne = colonneChar - 'A';

	        // 🔴 Vérification limites de la grille
	        if (ligne < 0 || ligne >= getGrille().getTaille()
	                || colonne < 0 || colonne >= getGrille().getTaille()) {
	            System.out.println("Coordonnée hors de la grille !");
	            continue;
	        }

	        // ✅ Tout est valide
	        return new Coordonnee(ligne, colonne);
	    }
	}


}
