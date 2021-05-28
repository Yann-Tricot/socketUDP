package Concurrent.Client;

import Concurrent.Serveur.serverConcurrent;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class clientConcurrent {
    // Variables pour buffer d'envoi d'un message
    private static byte[] buffer;
    private final static int length = 1024;
    private final static char CHAR_SEPARATOR = '@';
    private final static String MESSAGE_END_CLIENT = "End connection";

    // Port de connexion au serveur
    static int port;


    public static String getMessageEndClient() {
        return MESSAGE_END_CLIENT;
    }

    public static void main(String[] args) throws IOException {
        InetAddress server = InetAddress.getByName("192.168.1.20");
        DatagramSocket socket = new DatagramSocket();
        port = serverConcurrent.getPORT();
        boolean onGoingCom = true;
        while (onGoingCom) {
            // On récupère le message saisi par le client dans la console
            // Et on met en forme ce message avec CHAR_SEPARATOR
            Scanner scanner = new Scanner(System.in);
            String message = scanner.nextLine();
            message += CHAR_SEPARATOR;

            // On envoie le message
            buffer = message.getBytes();
            DatagramPacket dataSent = new DatagramPacket(buffer, buffer.length, server, port);
            socket.send(dataSent);

            // On réceptionne un message
            DatagramPacket dataReceived = new DatagramPacket(new byte[length], length);
            socket.receive(dataReceived);

            // On traite le message réceptionné pour l'afficher à l'utilisateur
            String msg = new String(dataReceived.getData());
            String msgRecu = msg.split(""+ CHAR_SEPARATOR)[0];
            System.out.println("message_SERVER: " + msgRecu);
            port = dataReceived.getPort();

            // Si le message réceptionné est celui d'arrêt on arrête donc la connexion
            if(serverConcurrent.getMessageEndServer().equals(msgRecu))
                onGoingCom = false;
        }
    }
}
