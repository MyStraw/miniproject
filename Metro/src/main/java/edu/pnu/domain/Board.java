package edu.pnu.domain;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

@Table(name = "board")

public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Integer id;
	private String title;
	private String content;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "member_username")
	private String author;

	@Column(name = "station_code")
	private Integer stationcode;

	@OneToMany(mappedBy = "board", orphanRemoval = true)
	private List<Heart> likes;
	@Column(name = "like_count")
	private Integer likecount;	
	private String image;
	
	@Lob
	private byte[] imagefile;
	public void likeChange(Integer likecount) {
		this.likecount = likecount;
	}

}
