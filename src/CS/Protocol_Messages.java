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
	public void setJOIN_CHATROOM(String jOIN_CHATROOM) {
		JOIN_CHATROOM = jOIN_CHATROOM;
	}
	public String getCLIENT_IP() {
		return CLIENT_IP;
	}
	public void setCLIENT_IP(String cLIENT_IP) {
		CLIENT_IP = cLIENT_IP;
	}
	public String getPORT() {
		return PORT;
	}
	public void setPORT(String pORT) {
		PORT = pORT;
	}
	public String getCLIENT_NAME() {
		return CLIENT_NAME;
	}
	public void setCLIENT_NAME(String cLIENT_NAME) {
		CLIENT_NAME = cLIENT_NAME;
	}
	public int getErrorCode() {
		return ERROR_CODE;
	}
	public void setErrorCode(int ERROR_CODE) {
		this.ERROR_CODE = ERROR_CODE;
	}
	public String getErrorDescription() {
		return ERROR_DESCRIPTION;
	}
	public void setErrorDescription(String ERROR_DESCRIPTION) {
		this.ERROR_DESCRIPTION = ERROR_DESCRIPTION;
	}
	public String getJoinedChatroom() {
		return Joined_Chatroom;
	}
	public void setJoinedChatroom(String Joined_Chatroom) {
		this.Joined_Chatroom = Joined_Chatroom;
	}
	public String getServerIp() {
		return IP_Of_Server;
	}
	public void setServerIp(String IP_Of_Server) {
		this.IP_Of_Server = IP_Of_Server;
	}
	public int getRoomRef() {
		return ROOM_REF;
	}
	public void setRoomRef(int ROOM_REF) {
		this.ROOM_REF = ROOM_REF;
	}
	public int getJoinId() {
		return JOIN_ID;
	}
	public void setJoinId(int JOIN_ID) {
		this.JOIN_ID = JOIN_ID;
	}
	
	public String getLEAVE_CHATROOM() {
		return LEAVE_CHATROOM;
	}
	public void setLEAVE_CHATROOM(String lEAVE_CHATROOM) {
		LEAVE_CHATROOM = lEAVE_CHATROOM;
	}
	
public String getCHAT() {
		return CHAT;
	}
	public void setCHAT(String cHAT) {
		CHAT = cHAT;
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
	 + "JOIN_ID: " + JOIN_ID;
    		 
   }
	
	
}