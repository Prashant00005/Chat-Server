package CS;


import java.io.PrintStream;


public class SameClient_WriteThread extends Thread {

	
	private PrintStream printStream = null;
	private String[] Accept_String;
	
	public SameClient_WriteThread(PrintStream printStream_obj1, String[] Accept_String_obj1)
	{
			this.Accept_String = Accept_String_obj1;
			this.printStream = printStream_obj1;
	}

	public void run() 
	
	{
			System.out.println("ClientWriter "+Thread.currentThread().getId()+ " : Created a thread");
			
			Chat_Protocol cp = new Chat_Protocol();
	
			if(Accept_String[0].startsWith("HELO ")) 
			{
				System.out.println("Helo Msg Thread writer ID Start"+Thread.currentThread().getId());
				cp.Func_HelloMsg(Accept_String[0],printStream);
				System.out.println("Helo Msg Thread writer ID End"+Thread.currentThread().getId());
				return;
			}
			else if(Accept_String[0].startsWith("JOIN_CHATROOM: ")) 
			{
				System.out.println("Join Msg Thread writer ID Start"+Thread.currentThread().getId());
				cp.Func_JoinMsg(Accept_String[0],Accept_String[1],Accept_String[2],Accept_String[3],printStream);
			
				System.out.println("Join Msg Thread writer ID End"+Thread.currentThread().getId());
			
			}
			else if(Accept_String[0].startsWith("LEAVE_CHATROOM: ")) 
			{
				
				System.out.println("Leave Msg Thread writer ID Start"+Thread.currentThread().getId());
				cp.Func_LeaveMsg(Accept_String[0],Accept_String[1],Accept_String[2],printStream);
				
				System.out.println("Leave Msg Thread writer ID End"+Thread.currentThread().getId());
				return;
			}
			
			else if(Accept_String[0].startsWith("CHAT: ")) {
				System.out.println("Chat Msg Thread writer ID Start"+Thread.currentThread().getId());
				cp.Func_ChatMsg(Accept_String[0],Accept_String[1],Accept_String[2],Accept_String[3],printStream);
				System.out.println("Chat Msg Thread writer ID End"+Thread.currentThread().getId());
			}
			
	}

	
	
	
	
}





	


	