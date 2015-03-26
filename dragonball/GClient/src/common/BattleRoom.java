package common;

public class BattleRoom {
	int RoomNO;
	RoomState roomState;
	
	public BattleRoom(int RoomNO,RoomState roomState){
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
