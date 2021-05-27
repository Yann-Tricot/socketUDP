package Concurrent;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class serverConcurrent {
    final static int PORT = 8532;
    final static String MESSAGE_END_SERVER = "OK connection ended";
    final static String MESSAGE_SERVER = "OK";
    final static String MESSAGE_CONNECTION = "Connection";
    final static String MESSAGE_CONNECTION_DENIED = "Connection denied ...";
    DatagramSocket socket;
    final static int length = 1024;
    static byte[] buffer = new byte[length];
    final static char CHAR_SEPARATOR = 'µ';

    public serverConcurrent() throws SocketException {
        socket = new DatagramSocket(PORT);
    }

    public void start() {
        while (true){
            DatagramPacket data = new DatagramPacket(buffer,buffer.length);
            try {
                socket.receive(data);
                String msg = new String(data.getData());
                String receivedMsg = msg.split(""+ CHAR_SEPARATOR)[0];
                System.out.println("message :" + receivedMsg);

                if(MESSAGE_CONNECTION.equals(receivedMsg)){
                    Communications com = new Communications(data);
                    com.start();
                }
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
