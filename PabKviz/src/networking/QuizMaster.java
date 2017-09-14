package networking;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.LinkedList;

public class QuizMaster {
	private static final int PORT = 11000;
	
	public static LinkedList<String> players = new LinkedList<String>();
	
	public static void main(String[] args) throws Exception {
		
		String hexAddress = toHexAddress(InetAddress.getLocalHost().getHostAddress());
		System.out.println("The server is running at: " + hexAddress);
		
		ServerSocket listener = new ServerSocket(PORT);
		try {
            while (true) {
                new Handler(listener.accept()).start();
            }
        } finally {
            listener.close();
        }
		
	}
	
	public static String toHexAddress (String ipAddress){
		String[] addressArray = ipAddress.split("\\.");
		String hexAddress = "";
		for (int i = 0; i < addressArray.length; i++) {
			String toConcat = Integer.toHexString(Integer.parseInt(addressArray[i])).toUpperCase();
			if(toConcat.length()==1){
				toConcat = "0" + toConcat;
			}
			hexAddress += toConcat;	
		}
		System.out.println(hexAddress);
		return hexAddress;
	}
}
