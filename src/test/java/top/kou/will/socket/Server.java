package top.kou.will.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2017/1/19.
 */
public class Server extends Thread {
    private static ServerSocket serverSocket;
    private BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

    static {
        try {
            serverSocket = new ServerSocket(Constant.PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            Socket socket = serverSocket.accept();

            ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());

            Request request = (Request) reader.readObject();
            while (request != null && !Constant.Status.END.equals(request.status) && !Constant.Status.ERROR.equals(request.status)) {
                System.out.println(String.format("%s: %s", request.sessionId, request.data));

                writer.writeObject(new Response(request, console.readLine().concat("\n")));
                writer.flush();
            }

            writer.close();
            reader.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server().start();
    }
}
