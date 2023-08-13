package edu.pnu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.pnu.entity.Busanuser;

@Repository
public interface BusanUserRepository extends JpaRepository<Busanuser, String> {
//    Optional<BusanUser> findById(String id);
//    Optional<BusanUser> findByUsername(String username);
//    boolean existsById(String id);
}
