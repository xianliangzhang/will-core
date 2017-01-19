package top.kou.will.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2017/1/18.
 */
public class SocketTest {
    private static final BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
    private static final int PORT = 20001;
    private static ServerSocket serverSocket;
    static {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class Server extends Thread {

        @Override
        public void run() {
            try {
                Socket socket = serverSocket.accept();

                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                String request;
                while (!(request = reader.readLine()).equals("bye")) {
                    System.out.println( "From-Client: " + request );

                    System.out.print("  -- Server Input: ");
                    writer.write(console.readLine().concat("\n"));
                    writer.flush();
                }

                writer.close();
                reader.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class Client extends Thread {

        @Override
        public void run() {
            try {
                Socket socket = new Socket("127.0.0.1", PORT);
                System.out.println("Client - Connect To Server OK - " + socket);



            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String... args) throws Exception {
        new Server().start();

        Socket socket = new Socket("127.0.0.1", PORT);
        System.out.println("Client - Connect To Server OK - " + socket);

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        writer.write("Hello Server!\n");
        writer.flush();

        String response;
        while (!(response = reader.readLine()).equals("bye")) {
            System.out.println("From-Server: " + response);

            System.out.print("  Client Input: ");
            writer.write(console.readLine().concat("\n"));
            writer.flush();
        }

        writer.close();
        reader.close();
        socket.close();

    }
}
