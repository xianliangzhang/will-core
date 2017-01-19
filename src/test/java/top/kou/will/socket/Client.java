package top.kou.will.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

/**
 * Created by Administrator on 2017/1/19.
 */
public class Client extends Thread {
    private BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
    private String clientIdentifier;
    private Request request;

    public Client(String clientIdentifier) {
        this.clientIdentifier = clientIdentifier;
        request = new Request(clientIdentifier, null, null);
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket("127.0.0.1", Constant.PORT);
            System.out.println("Client - Connect To Server OK - " + socket);

            ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());

            request.newRequest(UUID.randomUUID().toString(), null);
            writer.writeObject(request);
            writer.flush();

            Response response = (Response) reader.readObject();
            while (response != null && !Constant.Status.END.equals(response.status) && !Constant.Status.ERROR.equals(response.status)) {
                System.out.print("Server: ".concat(response.data.toString()));

                String message = console.readLine().concat("\n");
                writer.writeObject(request.newRequest(UUID.randomUUID().toString(), message));
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
        new Client("tom").start();
    }
}
