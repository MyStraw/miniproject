package edu.pnu.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

//	@ManyToOne
//	@JoinColumn(name = "author")
//	private Member member;
	private String author;

	@Column(name = "station_code")
	private Integer stationcode;

	@JsonBackReference
	@OneToMany(mappedBy = "board")
	private List<Like> likes = new ArrayList<>();

	@Column(name = "likes_count")
	private Integer likesCount = 0;

	private String image;	

	public void addLike() {
		 if (likesCount == null) {
		        likesCount = 0;
		    }
		this.likesCount += 1;
	}

	public void removeLike() {
		this.likesCount -= 1;
	}


	
//	@Lob
//	private byte[] imagefile;

//	@OneToMany(mappedBy = "board", orphanRemoval = true)
//	private List<Heart> likes;
//	@Column(name = "like_count")
//	private Integer likecount;	
//	public void likeChange(Integer likecount) {
//		this.likecount = likecount;
//	}

}
