package top.kou.will.socket;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2017/1/18.
 */
public class FileTransferSocketTest {
    public static InetAddress address;

    private static final int PORT = 20002;
    private static ServerSocket serverSocket;

    static {
        try {
            address = InetAddress.getLocalHost();
            serverSocket = new ServerSocket(PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class Server extends Thread {
        static String TEMP_DIR = "d://lab//";

        @Override
        public void run() {
            try {
                Socket socket = serverSocket.accept();
                new Consumer(socket).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        class Consumer extends Thread {
            private Socket socket;

            public Consumer(Socket socket) {
                this.socket = socket;
            }

            @Override
            public void run() {
                try {
                    BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
                    BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());

                    byte[] bytes = new byte[1024];
                    int readSize = -1;
                    readSize = in.read(bytes);

                    String fileName = new String( subByteArray(bytes, readSize), "utf8");

                    File targetFile = new File(TEMP_DIR.concat(fileName));
                    if (targetFile.exists()) {
                        targetFile.delete();
                    }
                    out.write("CONTINUE".getBytes());
                    out.flush();

                    FileOutputStream outputStream = new FileOutputStream(targetFile);
                    while ((readSize = in.read(bytes)) != -1) {
                        System.out.print(".");
                        outputStream.write(bytes, 0, readSize);
                    }
                    outputStream.close();
                    System.out.println("Received File: " + fileName);

                    in.close();
                    out.close();
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            byte[] subByteArray(byte[] origin, int targetLength) {
                byte[] target = new byte[targetLength];
                System.arraycopy(origin, 0, target, 0, targetLength);
                return target;
            }
        }
    }

    static class Client extends Thread {
        private File file;

        public Client(String filePath) {
            file = new File(filePath);

            if (!file.exists()) {
                throw new RuntimeException(String.format("File Not Found [filePath=%s]", filePath));
            }
        }

        @Override
        public void run() {
            try {
                Socket socket = new Socket(address, PORT);
                System.out.println("Connection To Server: " + socket);

                BufferedInputStream  in = new BufferedInputStream(socket.getInputStream());
                BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());

                out.write(file.getName().getBytes());
                out.flush();

                byte[] bytes = new byte[1024];
                int readSize = -1;
                readSize = in.read(bytes);

                if (new String(bytes, "utf8").startsWith("CONTINUE")) {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    while ( (readSize = fileInputStream.read(bytes)) != -1 ) {
                        out.write(bytes, 0, readSize);
                        out.flush();
                    }
                }
                out.close();
                in.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Server().start();

        for (int i = 1; i <= 2; i ++) {
            String name = "d://" + i + ".jpg";
            System.out.println("Start Send: " + name);
            new Client(name).start();
        }
    }

}
