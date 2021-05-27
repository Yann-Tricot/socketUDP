package Concurrent;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class clientConcurrent {
    // Variables pour buffer d'envoi d'un message
    static byte[] buffer;
    final static int length = 1024;
    final static char CHAR_SEPARATOR = '@';
    final static String MESSAGE_END_CLIENT = "End connection";

    // Port de connexion au serveur
    static int port;

    public static void main(String[] args) throws IOException {
        InetAddress server = InetAddress.getByName("127.0.0.1");
        DatagramSocket socket = new DatagramSocket();
        port = serverConcurrent.PORT;
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
            DatagramPacket dataRecieved = new DatagramPacket(new byte[length], length);
            socket.receive(dataRecieved);

            // On traite le message receptionné pour l'afficher à l'utilisateur
            String msg = new String(dataRecieved.getData());
            String msgRecu = msg.split(""+ CHAR_SEPARATOR)[0];
            System.out.println("message_SERVER: " + msgRecu);
            port = dataRecieved.getPort();

            // Si le message receptionné est celui d'arrêt on arrête donc la connexion
            if(serverConcurrent.MESSAGE_END_SERVER.equals(msgRecu))
                onGoingCom = false;
        }
    }
}
