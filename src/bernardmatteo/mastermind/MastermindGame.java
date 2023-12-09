package bernardmatteo.mastermind;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * La classe MastermindGame représente le jeu Mastermind.
 */
public class MastermindGame {

    private List<Integer> combinaison;
    private int nbEssai;
    private int nbEssaiMax;

    /**
     * Initialise une nouvelle instance du jeu Mastermind avec une combinaison générée aléatoirement.
     */
    public MastermindGame() {
        this.combinaison = genereReponse();
        this.nbEssai = 0;
        this.nbEssaiMax = -1;
    }

    /**
     * Définit le nombre maximum d'essais autorisés.
     *
     * @param nbEssaiMax Le nombre maximum d'essais autorisés.
     */
    public void setNbEssaiMax(int nbEssaiMax) {
        this.nbEssaiMax = nbEssaiMax;
    }

    /**
     * Renvoie une représentation sous forme de chaîne de la combinaison secrète.
     *
     * @return La combinaison secrète sous forme de chaîne.
     */
    @Override
    public String toString() {
        StringBuffer s = new StringBuffer();
        for (Integer n : combinaison) {
            s.append(n);
        }
        return s.toString();
    }

    // Gestion des réponses envoyées par le client

    /**
     * Génère une combinaison secrète aléatoire.
     *
     * @return Une liste d'entiers représentant la combinaison secrète.
     */
    private static ArrayList<Integer> genereReponse() {
        final Random RAND = new Random();
        ArrayList<Integer> ret = new ArrayList<Integer>();
        for (int i = 0; i < 6; i++) {
            ret.add(RAND.nextInt(10));
        }
        return ret;
    }

    /**
     * Vérifie si la proposition du joueur est correcte.
     *
     * @param essai La proposition du joueur sous forme de chaîne.
     * @return true si la proposition est correcte, false sinon.
     */
    public boolean bonneReponse(String essai) {
        try {
            String[] tabEssai = essai.split("");
            for (int i = 0; i < tabEssai.length; i++) {
                if (Integer.parseInt(tabEssai[i]) != combinaison.get(i))
                    return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Renvoie un indice sur la proposition du joueur.
     *
     * @param essai La proposition du joueur sous forme de chaîne.
     * @return Le feedback sous forme de chaîne.
     */
    public String renvoieReponse(String essai) {
        StringBuffer s = new StringBuffer();
        String[] tabEssai = essai.split("");
        for (int i = 0; i < combinaison.size(); i++) {
            if (Integer.parseInt(tabEssai[i]) == combinaison.get(i))
                s.append(combinaison.get(i));
            else if (combinaison.contains(Integer.parseInt(tabEssai[i])))
                s.append('X');
            else
                s.append('-');
        }
        return s.toString();
    }

    /**
     * Propose une réponse et incrémente le nombre d'essais si la proposition est valide.
     *
     * @param line La proposition du joueur sous forme de chaîne.
     */
    public void proposeReponse(String line) {
        try {
            Integer.parseInt(line);
            if (line.length() == 6)
                nbEssai++;
        } catch (NumberFormatException e) {
            System.out.println("Proposition non validée.");
        }
    }

    /**
     * Vérifie si le nombre d'essais a atteint le maximum autorisé.
     *
     * @return true si le nombre d'essais maximum est atteint, false sinon.
     */
    public boolean timeout() {
        return nbEssai == nbEssaiMax;
    }

    /**
     * Vérifie si la partie est terminée (soit par un timeout, soit par une réponse correcte).
     *
     * @param line La proposition du joueur sous forme de chaîne.
     * @return true si la partie est terminée, false sinon.
     */
    public boolean finPartie(String line) {
        return timeout() || bonneReponse(line);
    }
}

