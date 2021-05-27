package Concurrent;

import java.net.SocketException;

public class main {
    public static void main(String[] args) throws SocketException {
        serverConcurrent s = new serverConcurrent();
        s.start();
    }
}
