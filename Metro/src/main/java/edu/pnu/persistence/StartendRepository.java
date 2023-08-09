package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.pnu.domain.Startend;

public interface StartendRepository extends JpaRepository<Startend, Integer> {

}
