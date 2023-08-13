package edu.pnu.domain;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="board")
public class Board {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer seq;
	private String username;
	private String beach;
	private String title;
	private String content;
	@Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createDate = new Date();
	private String imagePath;
	
	

}

