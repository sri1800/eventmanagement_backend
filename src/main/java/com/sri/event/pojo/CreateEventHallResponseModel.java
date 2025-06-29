package com.sri.event.pojo;

public class CreateEventHallResponseModel {
    
	private String hallId;
    private String hallName;
    private String halladdress;
    private int hallCapacity;
    private boolean ac;
    private boolean foodFacility;
	public String getHallId() {
		return hallId;
	}
	public void setHallId(String hallId) {
		this.hallId = hallId;
	}
	public String getHallName() {
		return hallName;
	}
	public void setHallName(String hallName) {
		this.hallName = hallName;
	}
	public String getHalladdress() {
		return halladdress;
	}
	public void setHalladdress(String halladdress) {
		this.halladdress = halladdress;
	}
	public int getHallCapacity() {
		return hallCapacity;
	}
	public void setHallCapacity(int hallCapacity) {
		this.hallCapacity = hallCapacity;
	}
	public boolean isAc() {
		return ac;
	}
	public void setAc(boolean ac) {
		this.ac = ac;
	}
	public boolean isFoodFacility() {
		return foodFacility;
	}
	public void setFoodFacility(boolean foodFacility) {
		this.foodFacility = foodFacility;
	}

    
    
}

