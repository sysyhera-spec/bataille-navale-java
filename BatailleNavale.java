package batailleNavale;

import javax.swing.*;
import java.awt.*;

public class BatailleNavale extends JFrame {

    // Attributs logiques
    private Joueur joueur1, joueur2;
    private int tailleGrille;

    // Attributs graphiques pour la configuration
    private JTextField champTaille;
    private JTextField champNomJ1, champNomJ2;
    private JRadioButton radGraphiqueJ1, radTexteJ1, radAutoJ1;
    private JRadioButton radGraphiqueJ2, radTexteJ2, radAutoJ2;

    public BatailleNavale() {
        super("Bataille Navale");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Conteneur principal avec BoxLayout vertical
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        // --- ZONE 1 : Taille de la grille ---
        JPanel panelTaille = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTaille.setBorder(BorderFactory.createTitledBorder("Paramètres"));
        panelTaille.add(new JLabel("Taille de la grille :"));
        champTaille = new JTextField("10", 5);
        panelTaille.add(champTaille);
        contentPane.add(panelTaille);

        // --- ZONE 2 : Joueur 1 ---
        JPanel panelJ1 = new JPanel();
        panelJ1.setLayout(new BoxLayout(panelJ1, BoxLayout.Y_AXIS));
        panelJ1.setBorder(BorderFactory.createTitledBorder("Joueur 1"));
        champNomJ1 = new JTextField("Joueur 1", 15);
        JPanel pNom1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pNom1.add(new JLabel("Nom :"));
        pNom1.add(champNomJ1);
        panelJ1.add(pNom1);

        radGraphiqueJ1 = new JRadioButton("Joueur graphique", true);
        radTexteJ1 = new JRadioButton("Joueur Texte");
        radAutoJ1 = new JRadioButton("Joueur Auto");
        ButtonGroup groupeJ1 = new ButtonGroup();
        groupeJ1.add(radGraphiqueJ1);
        groupeJ1.add(radTexteJ1);
        groupeJ1.add(radAutoJ1);
        panelJ1.add(radGraphiqueJ1);
        panelJ1.add(radTexteJ1);
        panelJ1.add(radAutoJ1);
        contentPane.add(panelJ1);

        // --- ZONE 3 : Joueur 2 ---
        JPanel panelJ2 = new JPanel();
        panelJ2.setLayout(new BoxLayout(panelJ2, BoxLayout.Y_AXIS));
        panelJ2.setBorder(BorderFactory.createTitledBorder("Joueur 2"));
        champNomJ2 = new JTextField("Joueur 2", 15);
        JPanel pNom2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pNom2.add(new JLabel("Nom :"));
        pNom2.add(champNomJ2);
        panelJ2.add(pNom2);

        radGraphiqueJ2 = new JRadioButton("Joueur graphique");
        radTexteJ2 = new JRadioButton("Joueur Texte");
        radAutoJ2 = new JRadioButton("Joueur Auto", true);
        ButtonGroup groupeJ2 = new ButtonGroup();
        groupeJ2.add(radGraphiqueJ2);
        groupeJ2.add(radTexteJ2);
        groupeJ2.add(radAutoJ2);
        panelJ2.add(radGraphiqueJ2);
        panelJ2.add(radTexteJ2);
        panelJ2.add(radAutoJ2);
        contentPane.add(panelJ2);

        // --- ZONE 4 : Bouton Lancer ---
        JPanel panelBouton = new JPanel();
        JButton btnLancer = new JButton("Lancer la partie");
        btnLancer.addActionListener(e -> lancerPartie());
        panelBouton.add(btnLancer);
        contentPane.add(panelBouton);

        // Finalisation de la fenêtre
        this.setContentPane(contentPane);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void lancerPartie() {
        try {
            this.tailleGrille = Integer.parseInt(champTaille.getText());
            if (tailleGrille <= 0) throw new NumberFormatException();

            // Création des joueurs
            this.joueur1 = creerJoueur(champNomJ1.getText(), radGraphiqueJ1, radTexteJ1, radAutoJ1);
            this.joueur2 = creerJoueur(champNomJ2.getText(), radGraphiqueJ2, radTexteJ2, radAutoJ2);

            if (joueur1 == null || joueur2 == null) {
                JOptionPane.showMessageDialog(this, "Erreur lors de la création des joueurs.");
                return;
            }

            // Lancement du jeu
            this.setVisible(false);
            demarrerPartie();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La taille de la grille doit être un entier positif.");
        } catch (IllegalArgumentException e) {
        	JOptionPane.showMessageDialog(this, 
                    "Impossible de lancer la partie : " + e.getMessage(), 
                    "Problème de configuration", 
                    JOptionPane.WARNING_MESSAGE);
        	this.setVisible(true);
        }
    }

    private Joueur creerJoueur(String nom, JRadioButton radGraph, JRadioButton radTexte, JRadioButton radAuto) {
        if (nom == null || nom.isBlank()) nom = "Joueur";
        int[] taillesNavires = {5, 4, 3, 3, 2};

        if (radGraph.isSelected()) {
            FenetreJoueur fen = new FenetreJoueur(nom, tailleGrille);
            return new JoueurGraphique(fen.getGrilleDefense(), fen.getGrilleTirs(), nom);
        } else if (radTexte.isSelected()) {
            GrilleNavale g = new GrilleNavale(tailleGrille, taillesNavires);
            g.placementAuto(taillesNavires);
            return new JoueurTexte(g, nom);
        } else if (radAuto.isSelected()) {
            GrilleNavale g = new GrilleNavale(tailleGrille, taillesNavires);
            g.placementAuto(taillesNavires);
            return new JoueurAuto(g, nom);
        }

        return null;
    }

    /** Méthode conforme au sujet */
    private void demarrerPartie() {
        new Thread() {
            public void run() {
                joueur1.jouerAvec(joueur2);
            }
        }.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BatailleNavale());
    }
}
