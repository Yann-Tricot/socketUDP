package Tools;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.HashMap;

public class TestPort {
    /**
     * Méthode permettant de tester la disponibilité d'un port donné.
     *
     * @param PORT sur le quel on souhaite tester sa disponibilité
     * @return true si le port est disponible, false sinon
     */
    public static boolean testPort(int PORT){
        try {
            DatagramSocket socket = new DatagramSocket(PORT);
            socket.connect(new InetSocketAddress("127.0.0.1",PORT));
            socket.close();
        } catch (SocketException e) {
            return false;
        }
        return true;
    }

    /**
     * Méthode permettant de tester la disponibilité d'une plage de ports donnée.
     *
     * @param START_PORT port de départ pour la plage de test
     * @param FINAL_PORT port de fin pour la plage de test
     * @return une HashMap composé de la combinaison <PORT, disponibilité>
     */
    public static HashMap<Integer,Boolean> testPort(int START_PORT, int FINAL_PORT) {
        HashMap<Integer, Boolean> testPortsMap = new HashMap<>();
        for (int index = START_PORT; index <= FINAL_PORT; index++)
            testPortsMap.put(index, testPort(index));
        return testPortsMap;
    }
}
