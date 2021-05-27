package Echo;

import Echo.serverEcho;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class clientEcho {
    final static int taille = 1024;
    final static byte[] buffer = new byte[taille];

    public static void main(String argv[]) throws Exception
    {
        InetAddress serveur = InetAddress.getByName("127.0.0.1");
        int length = 1024;
        byte buffer[] = new byte[taille];

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Write your own message ...");
        String toSend = myObj.nextLine() + "-end-";
        buffer = toSend.getBytes("UTF-8");

        DatagramPacket dataSent = new DatagramPacket(buffer,buffer.length,serveur, serverEcho.port);
        DatagramSocket socket = new DatagramSocket();

        socket.send(dataSent);

        DatagramPacket dataReceived = new DatagramPacket(new byte[length],length);
        socket.receive(dataReceived);

        String messageReceived = new String(dataReceived.getData());
        messageReceived = messageReceived.split("-end-")[0];

        System.out.println("Data received : " + messageReceived);
        System.out.println("From : " + dataReceived.getAddress() + ":" + dataReceived.getPort());
    }
}
