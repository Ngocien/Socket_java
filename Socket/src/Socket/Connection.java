package Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Connection{
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        List<String> content= new ArrayList<>();


        public void startConnection(String ip, int port) throws IOException {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }

        public void sendMessage(String msg) throws IOException {
            out.println(msg);
            System.out.println("Client send to server: " + msg);
//            in.readLine();
//            System.out.println("Client receive: " + in.readLine());
        }

        public List<String> receiveMessage() throws IOException {

            new Thread(() -> {
                String temp = "";
                while (true) {
                    try {
                        temp = in.readLine();
                        content.add(temp);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Client" + clientSocket + " receive: " + temp);
                }
            }

            ).start();
            return content;
        }

        public void stopConnection() throws IOException {
            in.close();
            out.close();
            clientSocket.close();
        }
}
