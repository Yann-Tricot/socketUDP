package Concurrent;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;

public class Communications extends Thread{
    private final DatagramPacket data;
    private DatagramSocket socket;

    // Variables pour le port de connexion
    final static int START_PORT = 8000;
    final static int FINAL_PORT = 60000;
    private int port=0;

    // Variables pour buffer d'envoi d'un message à un client
    final static char CHAR_SEPARATOR = 'µ';
    static byte[] buffer = new byte[serverConcurrent.length];

    public Communications(DatagramPacket data) throws SocketException {
        this.data = data;
        socket = new DatagramSocket();
        HashMap<Integer,Boolean> map = Tools.TestPort.testPort(START_PORT, FINAL_PORT);
        boolean nonePort = true;
        int actualPort = START_PORT;
        do{
            if(map.get(actualPort)) {
                port = actualPort;
                socket = new DatagramSocket(actualPort);
                nonePort = false;
            }
            else
                actualPort++;
        }while (nonePort);
    }

    @Override
    public void run() {
        boolean onGoingCom = true;
        while (onGoingCom){
            // On recupère le message et on traite celui-ci pour nos tests.
            String msg = new String(data.getData());
            String receivedMsg = msg.split(""+ CHAR_SEPARATOR)[0];
            System.out.println("PORT: " + port + " - "+ data.getAddress() + "\nmessage_CLIENT: " + receivedMsg);

            // Si le message reçu est le bon pour mettre fin à la connexion on arrête celle-ci et notifie le client
            if(clientConcurrent.MESSAGE_END_CLIENT.equals(receivedMsg)){
                buffer = (serverConcurrent.MESSAGE_END_SERVER + CHAR_SEPARATOR).getBytes();
                data.setData(buffer);
                onGoingCom = false;
                try {
                    socket.send(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // Sinon, le message n'est pas celui pour mettre fin à la connexion et on poursuit l'échange
            else{
                byte[] bufferMsg = (serverConcurrent.MESSAGE_SERVER + CHAR_SEPARATOR).getBytes();
                data.setData(bufferMsg);
                try {
                    socket.send(data);
                    data.setData(buffer);
                    socket.receive(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
