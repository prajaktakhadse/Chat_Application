import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class Server {

    ServerSocket server;
    Socket socket;
    BufferedReader bufferedReader;// to read data
    PrintWriter out; // to write data

    public Server() {
        try {
            server = new ServerSocket(7777);
            System.out.println("server is ready to accept connection..!!");
            System.out.println("waiting...");
            socket = server.accept();// client accept
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
                        System.out.println("client terminated the chat");
                        break;
                    }

                    System.out.println("Client : " + msg);

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

        System.out.println("this is server...going to start server");
        new Server();

    }
}
