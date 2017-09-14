package networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Handler extends Thread {
	private String name;
	
    private Socket socket;
    
    private BufferedReader in;
    
    private PrintWriter out;
    
    public Handler(Socket socket) {
		this.socket = socket;
	}
    
    @Override
    public void run() {
    	try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			
			
			 while (true) {
			     out.println("Your name: ");
			     name = in.readLine();
			     if (name == null) {
			         return;
			     }
			     synchronized (QuizMaster.players) {
			         if (!QuizMaster.players.contains(name)) {
			             QuizMaster.players.add(name);
			             break;
			         }
			     }
			 }
			 
			 out.println("Name accepted!");
			 out.println("Choose: " + QuizMaster.players);
			 
			 while (true) {
				 
			 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	
    	super.run();
    }
}
