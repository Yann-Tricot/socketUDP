package Concurrent;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class serverConcurrent {
    // Variables message de discussion du serveur
    final static String MESSAGE_END_SERVER = "OK connection ended";
    final static String MESSAGE_SERVER = "OK";
    final static String MESSAGE_CONNECTION = "Connection";
    final static String MESSAGE_CONNECTION_DENIED = "Connection denied ...";

    final static int PORT = 8532;
    DatagramSocket socket;

    // Variables pour buffer de réception
    final static int length = 1024;
    static byte[] buffer = new byte[length];
    final static char CHAR_SEPARATOR = 'µ';

    public serverConcurrent() throws SocketException {
        socket = new DatagramSocket(PORT);
    }

    /**
     * Méthode permettant de lancer notre serveur afin de mettre en place une communication si la connexion
     * est accepté par celui-ci.
     */
    public void start() {
        while (true){
            DatagramPacket data = new DatagramPacket(buffer,buffer.length);
            try {
                // On reçoit le message et on traite celui-ci pour nos tests.
                socket.receive(data);
                String msg = new String(data.getData());
                String receivedMsg = msg.split(""+ CHAR_SEPARATOR)[0];
                System.out.println("message :" + receivedMsg);

                // Si le message reçu est le bon pour authoriser la connexion on instancie une communication
                if(MESSAGE_CONNECTION.equals(receivedMsg)){
                    Communications com = new Communications(data);
                    com.start();
                }
                // Sinon, le message reçu n'est pas le bon pour authoriser la connexion on renvoi un msg d'erreur.
                else {
                    buffer = (MESSAGE_CONNECTION_DENIED + CHAR_SEPARATOR).getBytes();
                    data.setData(buffer);
                    socket.send(data);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
