package com.sri.event.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "eventHalls")
public class EventHallEntity {

	@Id
	@GeneratedValue
	private long id;

	private String hallId;
	private String hallName;
	private String halladdress;
	private int hallCapacity;
	private boolean ac;
	private boolean foodFacility;
	private boolean isBooked = false;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
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
	public boolean isBooked() {
		return isBooked;
	}
	public void setBooked(boolean isBooked) {
		this.isBooked = isBooked;
	}
	
	
	
}
