package bernardmatteo.mastermind;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * La classe ClientMastermind représente le client du jeu Mastermind.
 */
public class ClientMastermind {

    final static Scanner SCANNER = new Scanner(System.in);

    /**
     * Le point d'entrée principal pour le client Mastermind.
     *
     * @param args Les arguments de la ligne de commande (non utilisés dans cette implémentation).
     */
    public static void main(String[] args) {

        try {
            // Établit une connexion avec le serveur Mastermind sur le port 7007
            Socket s = new Socket("localhost", 7007);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream());

            // Affiche les messages d'accueil du serveur
            System.out.println(in.readLine());
            System.out.println(in.readLine());
            System.out.println(in.readLine());
            String line;

            // Demande à l'utilisateur d'entrer le nombre d'essais maximum
            System.out.println("Entrez le nombre d'essais maximum :");
            line = SCANNER.nextLine();
            System.out.println();
            out.println(line);
            out.flush();

            // Continue le jeu jusqu'à la fin de la partie
            while (!line.equals("Fin de partie ! Bien joué !") && !line.equals("Fin de partie ! Plus d'essais..")) {
                // Demande à l'utilisateur d'entrer sa réponse
                System.out.println("Entrez votre réponse :");
                line = SCANNER.nextLine();
                out.println(line);
                out.flush();
                System.out.println();

                // Affiche l'indice fourni par le serveur
                line = in.readLine();
                System.out.println("Indice : " + line);

                if (line == null)
                    break;
            }
            s.close();

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}

