package CS;


public class Protocol_Messages {


String CLIENT_NAME;
String CLIENT_IP;
String JOIN_CHATROOM;
String LEAVE_CHATROOM;
String PORT;
String ERROR_DESCRIPTION;
String Joined_Chatroom;
String IP_Of_Server;
String CHAT;
String Disconnect;
int ROOM_REF;
int JOIN_ID;
int ERROR_CODE;
	
	
	
	public String getJOIN_CHATROOM() {
		return JOIN_CHATROOM;
	}
	public void setJOIN_CHATROOM(String join_chatroom) {
		JOIN_CHATROOM = join_chatroom;
	}
	public String getCLIENT_IP() {
		return CLIENT_IP;
	}
	public void setCLIENT_IP(String client_ip) {
		CLIENT_IP = client_ip;
	}
	public String getPORT() {
		return PORT;
	}
	public void setPORT(String port) {
		PORT = port;
	}
	public String getCLIENT_NAME() {
		return CLIENT_NAME;
	}
	public void setCLIENT_NAME(String client_name) {
		CLIENT_NAME = client_name;
	}
	public int getErrorCode() {
		return ERROR_CODE;
	}
	public void setErrorCode(int error_code) {
		this.ERROR_CODE = error_code;
	}
	public String getErrorDescription() {
		return ERROR_DESCRIPTION;
	}
	public void setErrorDescription(String error_description) {
		this.ERROR_DESCRIPTION = error_description;
	}
	public String getJoinedChatroom() {
		return Joined_Chatroom;
	}
	public void setJoinedChatroom(String joined_chatroom) {
		this.Joined_Chatroom = joined_chatroom;
	}

	public String getIP_Of_Server() {
		return IP_Of_Server;
	}
	public void setIP_Of_Server(String iP_Of_Server) {
		IP_Of_Server = iP_Of_Server;
	}
	public int getRoomRef() {
		return ROOM_REF;
	}
	public void setRoomRef(int room_ref) {
		this.ROOM_REF = room_ref;
	}
	public int getJoinId() {
		return JOIN_ID;
	}
	public void setJoinId(int join_id) {
		this.JOIN_ID = join_id;
	}
	
	public String getLEAVE_CHATROOM() {
		return LEAVE_CHATROOM;
	}
	public void setLEAVE_CHATROOM(String leave_chatroom) {
		LEAVE_CHATROOM = leave_chatroom;
	}
	
public String getCHAT() {
		return CHAT;
	}
	public void setCHAT(String chat) {
		CHAT = chat;
	}
	public String getDisconnect() {
		return Disconnect;
	}
	public void setDisconnect(String disconnect) {
		Disconnect = disconnect;
	}
	 
public String Join_ReplyTo_Client()
{
//Return with message to client in format as given in question
	return "JOINED_CHATROOM: "+Joined_Chatroom+"\n" + "SERVER_IP: " + IP_Of_Server + "\n" + "PORT: "
	 + PORT + "\n"
	 + "ROOM_REF: "+ ROOM_REF +"\n"
	 + "JOIN_ID: " + JOIN_ID +"\n";
    		 
   }
	
	
}