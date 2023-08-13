package edu.pnu.dto;

import java.util.List;

public class BusanFoodCommentDTO {
    private String name;
    private String gugun;
    private String addr;
    private List<String> foodComments;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGugun() {
		return gugun;
	}
	public void setGugun(String gugun) {
		this.gugun = gugun;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public List<String> getFoodComments() {
		return foodComments;
	}
	public void setFoodComments(List<String> foodComments) {
		this.foodComments = foodComments;
	}
	
}
