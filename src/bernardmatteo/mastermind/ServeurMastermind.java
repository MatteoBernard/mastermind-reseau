package bernardmatteo.mastermind;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class ServeurMastermind extends Thread {
    final static int port = 7007;

    private static Random RAND = new Random();

    Socket s;

    ServeurMastermind(Socket s) {
        this.s = s;
    }

    public static ArrayList<Integer> genereReponse(int nbDigit) {
        ArrayList<Integer> ret = new ArrayList<Integer>();
        for (int i = 0 ; i < nbDigit ; i ++) {
            ret.add(RAND.nextInt(0, 10));
        }
        return ret;
    }

    public static String ecrisReponse(ArrayList<Integer> reponse, String essai) {
        StringBuffer s=new StringBuffer();
        String[] tabEssai = essai.split("");
        for (int i = 0 ; i < reponse.size() ; i ++) {
            if (Integer.parseInt(tabEssai[i]) == reponse.get(i))
                s.append(reponse.get(i));
            else if (reponse.contains(Integer.parseInt(tabEssai[i])))
                s.append('X');
            else
                s.append('-');
        }
        return s.toString();
    }

    public static boolean bonneReponse(ArrayList<Integer> reponse, String essai) {
        String[] tabEssai = essai.split("");
        for(int i = 0 ; i < tabEssai.length ; i ++) {
            if (Integer.parseInt(tabEssai[i]) != reponse.get(i))
                return false;
        }
        return true;
    }


    public void run() {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream());
            out.println("!! Jeu du mastermind !!");
            out.println("Essayez de deviner le nombre de 6 chiffres que j’ai choisi ! \n");
            out.flush();

            String line = in.readLine();
            final int nbEssaisMax = Integer.parseInt(line);
            int nbEssais = 0;

            ArrayList<Integer> reponse = genereReponse(6);
            System.out.println("Réponse : " + reponse);

            do {
                line = in.readLine();
                nbEssais ++;

                if (line == null)
                    break;
                if (line.equals("quit"))
                    break;
                try {
                    System.out.println("Réponse du client : " + line + "\n");
                    if (bonneReponse(reponse, line)) {
                        System.out.println("Fin de partie.");
                        out.println("Fin de partie ! Bien joué !");
                        out.flush();
                        break;
                    }
                    else
                        out.println(ecrisReponse(reponse, line));
                } catch (Exception e) {
                    out.println("Réponse au mauvais format");
                }
                out.flush();
            } while(nbEssais <= nbEssaisMax);
            s.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            ServerSocket passiveSocket = new ServerSocket(port);
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

