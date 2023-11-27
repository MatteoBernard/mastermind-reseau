package bernardmatteo.mastermind;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientMastermind {

    final static Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            Socket s = new Socket("localhost", 7007);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream());
            System.out.println(in.readLine());
            System.out.println(in.readLine());
            System.out.println(in.readLine());
            String line;

            System.out.println("Entrez nombre d'essai maximum :");
            line=SCANNER.nextLine();
            out.println(line);
            out.flush();

            do {
                System.out.println("Entrez votre réponse :");
                line=SCANNER.nextLine();
                out.println(line);
                out.flush();
                line=in.readLine();
                System.out.println("Indice : " + line);
                if (line==null)
                    break;

            } while (!(line.equals("Fin de partie ! Bien joué !")));
            s.close();

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
