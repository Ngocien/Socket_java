import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    public static void main(String args[]) throws IOException {
        InetAddress address = InetAddress.getLocalHost();
        Socket s1 = null;
        BufferedReader br = null;
        BufferedReader is = null;
        PrintWriter os = null;

        try {
            s1 = new Socket(address, 4445);
            br = new BufferedReader(new InputStreamReader(System.in));
            is = new BufferedReader(new InputStreamReader(s1.getInputStream()));
            os = new PrintWriter(s1.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.print("IO Exception");
        }

        System.out.println("Client Address : " + address);
        System.out.println("Enter Data to echo Server ( Enter QUIT to end):");

        String response=null;
        String line =null;
        try{
            line=br.readLine();
            while(line.compareTo("QUIT")!=0){
                os.println(line);
                os.flush();
                response = is.readLine();
                System.out.println("Server send: "+response);
                line=br.readLine();

            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Socket read Error");
        } finally {
            assert is != null;
            is.close();
            assert os != null;
            os.close();
            br.close();
            s1.close();
            System.out.println("Connection Closed");

        }


    }


}