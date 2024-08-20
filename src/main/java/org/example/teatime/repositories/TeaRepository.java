package org.example.teatime.repositories;

import org.example.teatime.entities.Tea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeaRepository extends JpaRepository<Tea, Long> {
    List<Tea> findByType(String type);
}
