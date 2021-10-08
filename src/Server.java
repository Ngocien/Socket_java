
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;


public class Server {
    private static ArrayList<ServerThread> clientList = new ArrayList<>();




    public static void main(String args[]) throws IOException {

        Socket s=null;
        ServerSocket ss2= new ServerSocket(4445);
        System.out.println("Server Listening......");
        while(true){
            try{
//                assert ss2 != null;
                s = ss2.accept();
                System.out.println("connection Established");
                ServerThread st= new ServerThread(s);
                clientList.add(st);
                System.out.println("hello" + clientList);
                st.start();

            }

            catch(Exception e){
                e.printStackTrace();
                System.out.println("Connection Error");

            }
        }

    }
    static class ServerThread extends Thread {
        public BufferedReader is = null;
        private PrintWriter os = null;
        private Socket s = null;
        private String content = "";


        public ServerThread(Socket s) throws IOException {
            this.s = s;
            is = new BufferedReader(new InputStreamReader(s.getInputStream()));
            os = new PrintWriter(s.getOutputStream(), true);
        }

        public void run()
        {
            while(true) {
                try
                {
                    String userInput = is.readLine();
                    if(userInput.compareTo("QUIT")!=0) {
//                        content += userInput;
                        System.out.println("Server receive: " + userInput);
                        sendtoAll(userInput);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            }
        }


    public static void sendtoAll(String mess)
    {
        for (ServerThread i: clientList) {
            i.os.println(mess);
            System.out.println("Server send to: " +i.s + "-- "+mess);
        }

    }


}

