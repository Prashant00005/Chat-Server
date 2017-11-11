package CS;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class SameClient_WriteThread extends Thread {

	
	private PrintStream printStream = null;
	private String[] Accept_String;
	
	public SameClient_WriteThread(PrintStream printStream_obj1, String[] Accept_String_obj1) {
		this.Accept_String = Accept_String_obj1;
		this.printStream = printStream_obj1;
	}

	public void run() {
		System.out.println("ClientWriter "+Thread.currentThread().getId()+ " : Created a thread");
		Chat_Protocol cp = new Chat_Protocol();

		if(Accept_String[0].startsWith("HELO ")) 
		{
			System.out.println("Thread writer ID Start"+Thread.currentThread().getId());
			cp.Func_HelloMsg(Accept_String[0],printStream);
			System.out.println("Thread writer ID End"+Thread.currentThread().getId());
			return;
		}
	}

	
	
	
	
}





	


	