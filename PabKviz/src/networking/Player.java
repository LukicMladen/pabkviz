package networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import javax.xml.bind.DatatypeConverter;

public class Player {

    BufferedReader in;
    PrintWriter out;
    
    private void run() throws IOException {

    	Scanner consoleReader = new Scanner(System.in);
    	
    	System.out.println("Server address: ");
        String serverAddressHex = consoleReader.nextLine();
        
        InetAddress iAddress = InetAddress.getByAddress(DatatypeConverter.parseHexBinary(serverAddressHex));
        String serverAddress = iAddress.toString().replaceFirst("/", "");
        
        Socket socket = new Socket(serverAddress, 11000);
        
        in = new BufferedReader(new InputStreamReader(
            socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        while (true) {
            String line = in.readLine();
            if (line.startsWith("Your")) {
                System.out.println("Your name: ");
                out.println(consoleReader.nextLine());
            } else if (line.startsWith("Nam")) {
            	System.out.println("Name accepted!");
            	
            } else if (line.startsWith("Choo")) {
            	System.out.println(line);
            	
            }
        }
        
       
    }

    public static void main(String[] args) throws Exception {
        Player player = new Player();
        player.run();
        
    }
}