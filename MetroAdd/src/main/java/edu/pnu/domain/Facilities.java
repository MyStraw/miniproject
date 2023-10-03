package edu.pnu.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "facilities")
public class Facilities {
	@Id	@Column(name = "station_code")
	private Integer stationcode;	
	private String above_under;	
	private Integer floor;	
	private Boolean connector;	
	private Boolean transfer_parking;	
	private Boolean cycle_locker;		
	private Boolean meet_place;		
	private Boolean locker;		
	private Boolean photo;	
	private Boolean doc_machine;		
	private Boolean baby_milk;		
	private Boolean wheel_lift;
	private Boolean elevator_inside;
	private Boolean elevator_out;	
	private Boolean escalator;
	private Boolean blind_way;	
	private Boolean external_ramp;
	private Boolean emergency_phone;	
	private Boolean police;	
}