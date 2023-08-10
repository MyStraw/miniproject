package edu.pnu.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity

@Table(name = "member")
public class Member {	 
	@Id // 해당 필드가 엔티티의 PK, 기본키임을 나타낸다. username이 기본키.
	private String username;
	private String password;
	private String role;
	@Column(columnDefinition = "TINYINT(1)")
	private boolean enabled;
	private Date date;
	
	@JsonBackReference
	@OneToMany(mappedBy = "member")
    private List<Like> likes = new ArrayList<>();

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList(role);
	}
}
