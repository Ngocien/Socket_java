package Socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server{

    private ServerSocket serverSocket;
    private static ArrayList<ServerHandler> clientList;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while(true)
            new ServerHandler(serverSocket.accept()).start();
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    public Server() throws IOException {

        clientList = new ArrayList<>();
        ServerSocket listener = null;


        System.out.println("Server is waiting to accept user...");

        try {
            listener = new ServerSocket(6666);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }

        try {
            while (true) {
                Socket socketOfServer = listener.accept();
                ServerHandler client = new  ServerHandler(socketOfServer);
                clientList.add(client);
                client.start();
                System.out.println("Connection...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            listener.close();
        }
    }

    public static void main(String args[]) throws IOException
    {
        new Server();

    }





    private static class ServerHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
//        List<String> all_mess = new ArrayList<>();


        public ServerHandler(Socket socket) throws IOException {
            this.clientSocket = socket;
            in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            out = new PrintWriter(this.clientSocket.getOutputStream(), true);

        }

        public void run() {
            while(true)
            {
                try
                {
                    String userInput = in.readLine();
                    System.out.println("Server receive" + userInput);
                    sendtoAll(userInput);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public static void sendtoAll(String mess)
    {
        for (ServerHandler i: clientList) {
            i.out.println(mess);
            System.out.println("Server send to: " + mess);
        }

    }


}
