package Concurrent;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Communications extends Thread{
    private final DatagramPacket data;
    final static char CHAR_SEPARATOR = 'µ';
    static byte[] buffer = new byte[serverConcurrent.length];
    private final DatagramSocket socket;

    public Communications(DatagramPacket data) throws SocketException {
        this.data = data;
        socket = new DatagramSocket();
    }

    @Override
    public void run() {
        boolean onGoingCom = true;
        while (onGoingCom){
            String msg = new String(data.getData());
            String receivedMsg = msg.split(""+ CHAR_SEPARATOR)[0];
            System.out.println("message :" + receivedMsg);

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
