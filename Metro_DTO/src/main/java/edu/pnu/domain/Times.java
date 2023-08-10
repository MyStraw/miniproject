package edu.pnu.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "times")


public class Times {	
	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer seq;
	private Integer train_startseq;
	private Integer station_code;
	private String arrive_time;
	private String depart_time;	

}