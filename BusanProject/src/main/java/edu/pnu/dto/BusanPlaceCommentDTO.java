package edu.pnu.dto;

import java.util.List;

public class BusanPlaceCommentDTO {
	private String name;
	private String gugun;
	private String addr;
	private List<String> placeComments;

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

	public List<String> getPlaceComments() {
		return placeComments;
	}

	public void setPlaceComments(List<String> placeComments) {
		this.placeComments = placeComments;
	}

}
