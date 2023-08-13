package edu.pnu.dto;

import java.util.List;

public class BusanTourCommentDTO {
	private String name;
	private String gugun;
	private String plage;

	private List<String> tourComments;

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

	public String getPlage() {
		return plage;
	}

	public void setPlage(String plage) {
		this.plage = plage;
	}

	public List<String> getTourComments() {
		return tourComments;
	}

	public void setTourComments(List<String> tourComments) {
		this.tourComments = tourComments;
	}

}
