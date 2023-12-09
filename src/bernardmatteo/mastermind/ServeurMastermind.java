package bernardmatteo.mastermind;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * La classe ServeurMastermind représente le serveur du jeu Mastermind.
 */
public class ServeurMastermind extends Thread {
    final static int port = 7007;
    Socket s;

    /**
     * Initialise une nouvelle instance du serveur Mastermind avec le socket spécifié.
     *
     * @param s Le socket utilisé pour la communication avec le client.
     */
    ServeurMastermind(Socket s) {
        this.s = s;
    }

    /**
     * Exécute le thread du serveur, gérant la communication avec le client.
     */
    public void run() {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream());

            // Envoie les messages d'accueil au client
            out.println("!! Jeu du mastermind !!");
            out.println("Essayez de deviner le nombre de 6 chiffres que j’ai choisi ! \n");
            out.flush();

            // Initialise une nouvelle partie Mastermind
            MastermindGame game = new MastermindGame();
            String line = in.readLine();
            game.setNbEssaiMax(Integer.parseInt(line));

            System.out.println("Réponse : " + game.toString());

            // Continue la partie jusqu'à ce qu'elle se termine
            while(!game.finPartie(line)) {
                line = in.readLine();

                if (line == null)
                    break;
                if (line.equals("quit"))
                    break;

                try {
                    // Propose la réponse du client et gère la logique du jeu
                    game.proposeReponse(line);

                    System.out.println("Réponse du client : " + line + "\n");
                    if (game.timeout()) {
                        System.out.println("Fin de partie.");
                        out.println("Fin de partie ! Plus d'essais..");
                        out.flush();
                        break;
                    }
                    else if (game.bonneReponse(line)) {
                        System.out.println("Fin de partie.");
                        out.println("Fin de partie ! Bien joué !");
                        out.flush();
                        break;
                    }
                    else
                        out.println(game.renvoieReponse(line));
                } catch (Exception e) {
                    out.println("Réponse au mauvais format");
                }
                out.flush();
            }
            s.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Le point d'entrée principal pour le serveur Mastermind.
     *
     * @param args Les arguments de la ligne de commande (non utilisés dans cette implémentation).
     */
    public static void main(String[] args) {
        try {
            // Initialise le socket passif pour écouter les connexions entrantes
            ServerSocket passiveSocket = new ServerSocket(port);

            // Accepte les connexions entrantes et démarre un nouveau thread pour chaque client
            while (true) {
                Socket activeSocket = passiveSocket.accept();
                ServeurMastermind s = new ServeurMastermind(activeSocket);
                s.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
