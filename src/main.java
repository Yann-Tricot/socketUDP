import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        ArrayList<Integer> resList = serverEcho.testPort(1, 9000);
        for (Integer res: resList) {
            System.out.println(res + "\n");
        }
    }
}
