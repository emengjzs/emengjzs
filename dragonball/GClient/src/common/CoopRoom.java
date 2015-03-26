package common;
import common.RoomState;

public class CoopRoom {
	int RoomNO;
	RoomState roomState;
	
	public CoopRoom(int RoomNO,RoomState roomState){
		this.RoomNO=RoomNO;
		this.roomState=roomState;
	}
	
	public RoomState getState(){
		return roomState;
	}
	
	public void setState(RoomState state){
		this.roomState=state;
	}
	
	public int getRoomNO(){
		return RoomNO;
	}
}
