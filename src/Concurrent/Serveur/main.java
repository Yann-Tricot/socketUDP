package Concurrent.Serveur;

import java.net.SocketException;

public class main {
    public static void main(String[] args) throws SocketException {
        // On instancie un serveur sur lequel se connecter.
        serverConcurrent server = new serverConcurrent();
        // On appelle la fonction "start" de notre serveur pour lancer ce dernier.
        server.start();
    }
}
