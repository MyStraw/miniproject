package edu.pnu.dto;

import java.util.List;

public class BusanFestivalCommentDTO {
	private String name;
	private String gugun;
	private String place;

	private List<String> festivalComments;

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

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public List<String> getFestivalComments() {
		return festivalComments;
	}

	public void setFestivalComments(List<String> festivalComments) {
		this.festivalComments = festivalComments;
	}

}
