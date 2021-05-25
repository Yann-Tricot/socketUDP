import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class clientEcho {
    final static int taille = 1024;
    final static byte[] buffer = new byte[taille];

    public static void main(String argv[]) throws Exception
    {
        InetAddress serveur = InetAddress.getByName("127.0.0.1");
        int length = 1024;
        byte buffer[] = new byte[taille];
        DatagramPacket dataSent = new DatagramPacket(buffer,length,serveur,serverEcho.port);
        DatagramSocket socket = new DatagramSocket();

        socket.send(dataSent);

        DatagramPacket dataReceived = new DatagramPacket(new byte[length],length);
        socket.receive(dataReceived);
        System.out.println("Data received : " + new String(dataReceived.getData()));
        System.out.println("From : " + dataReceived.getAddress() + ":" + dataReceived.getPort());
    }
}
