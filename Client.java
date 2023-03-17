import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class Client {

    Socket socket;
    BufferedReader bufferedReader;
    PrintWriter out;

    public Client() {
        try {
            System.out.println("Sending request to server....");
            socket = new Socket("127.0.0.1", 7777);
            System.out.println("Connection done...");

            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void startReading() {
        // thread which will read the data and give it
        Runnable r1 = () -> {
            System.out.println("reader started..");

            while (true) {

                String msg;
                try {
                    msg = bufferedReader.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Server terminated the chat");
                        break;
                    }

                    System.out.println("Server : " + msg);

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        };

        new Thread(r1).start();
    }

    private void startWriting() {
        // thread which take the data from and send to client

        Runnable r2 = () -> {
            System.out.println("writer started..  ");
            try {
                while (true) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

                    String content = br.readLine();
                    out.println(content);
                    out.flush();
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("this is client......");
        new Client();
    }
}
