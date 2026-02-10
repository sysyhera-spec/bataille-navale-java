package batailleNavale;

import javax.swing.*;
import java.awt.*;

public class FenetreJoueur extends JFrame {

    private GrilleGraphique grilleTirs;
    private GrilleNavaleGraphique grilleDefense;

    // Constructeur par défaut
    public FenetreJoueur() {
        this("Nom du joueur", 10);
    }

    // Constructeur principal
    public FenetreJoueur(String nom, int taille) {
        super(nom);

        // Fermer la fenêtre correctement
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Conteneur principal : 1 ligne, 2 colonnes
        JPanel contentPane = new JPanel(new GridLayout(1, 2));

        // --- Grille de tirs (gauche) ---
        grilleTirs = new GrilleGraphique(taille);
        grilleTirs.setBorder(BorderFactory.createTitledBorder("Grille de tir"));
        contentPane.add(grilleTirs);

        // --- Grille de défense (droite) ---
        grilleDefense = new GrilleNavaleGraphique(taille);

        // Placement automatique des navires
        int[] taillesNavires = {5, 4, 3, 3, 2};
        grilleDefense.placementAuto(taillesNavires);

        // Ajout de la partie graphique de la grille de défense
        grilleDefense.getGrilleGraphique().setBorder(BorderFactory.createTitledBorder("Grille de défense"));
        contentPane.add(grilleDefense.getGrilleGraphique());

        // Finalisation
        this.setContentPane(contentPane);
        this.pack();
        this.setLocationRelativeTo(null); // Centrer à l'écran
        this.setVisible(true);
    }

    // Accesseurs
    public GrilleGraphique getGrilleTirs() {
        return grilleTirs;
    }

    public GrilleNavaleGraphique getGrilleDefense() {
        return grilleDefense;
    }
}
