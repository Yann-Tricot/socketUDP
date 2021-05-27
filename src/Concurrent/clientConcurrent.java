package Concurrent;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class clientConcurrent {
    final static int length = 1024;
    static byte[] buffer;
    final static char CHAR_SEPARATOR = 'µ';
    final static String MESSAGE_END_CLIENT = "End connection";
    static int port;

    public static void main(String[] args) throws IOException {
        InetAddress server = InetAddress.getByName("127.0.0.1");
        DatagramSocket socket = new DatagramSocket();
        port = serverConcurrent.PORT;
        boolean onGoingCom = true;
        while (onGoingCom) {

            Scanner scanner = new Scanner(System.in);
            String message = scanner.nextLine();
            message += CHAR_SEPARATOR;     // création du message et ajout du char separateur

            buffer = message.getBytes();
            DatagramPacket dataSent = new DatagramPacket(buffer, buffer.length, server, port);
            socket.send(dataSent);          // Envoie du message

            DatagramPacket dataReceived = new DatagramPacket(new byte[length], length);
            socket.receive(dataReceived);   // reception du message

            String msg = new String(dataReceived.getData());
            String msgRecu = msg.split(""+ CHAR_SEPARATOR)[0];
            System.out.println("message : " + msgRecu);       // traitement et affichage du message
            port = dataReceived.getPort();
            if(serverConcurrent.MESSAGE_END_SERVER.equals(msgRecu)){
                onGoingCom = false;
            }
        }
    }
}
