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
@Table(name = "station")
public class Station {
	@Id	@Column(name = "station_code")
	private Integer stationcode;
	private String line_num;
	private String station_name;
	private String stationname_plus;
	private String english_name;
	private String tel;
	private String address;
	@Column(length = 1000)
	private String history;
}