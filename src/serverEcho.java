import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class serverEcho {
    final static int port = 8532;
    final static int taille = 1024;
    static byte[] buffer = new byte[taille];

    public static void main(String argv[]) throws Exception
    {
        DatagramSocket socket = new DatagramSocket(port);
        while(true)
        {
            DatagramPacket data = new DatagramPacket(buffer,buffer.length);
            socket.receive(data);

            String msgReceived = new String(data.getData());
            String msgReceivedToSend = msgReceived.split("-end-")[0];
            System.out.println("message CLIENT: " + msgReceivedToSend);

            byte bufferToSend[] = new byte[msgReceivedToSend.getBytes().length];
            String msgToSend = msgReceivedToSend + "-end-";
            bufferToSend = msgToSend.getBytes(StandardCharsets.UTF_8);
            data.setData(bufferToSend);

            System.out.println(data.getAddress());
            socket.send(data);
        }
    }

    public static ArrayList<Integer> testPort(int portStart, int portEnd){
        ArrayList<Integer> occupiedPorts = new ArrayList<Integer>();
        for (int index = portStart; index <= portEnd; index++){
            try {
                DatagramSocket socket = new DatagramSocket(index);
                socket.connect(new InetSocketAddress("127.0.0.1", index));
                socket.close();
            } catch (SocketException e){
                occupiedPorts.add(index);
            }
        }
        return occupiedPorts;
    }
}
