package com.example.capstone.repository.contraindicate;

import com.example.capstone.entity.contraindicate.Contraindicate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContraindicateRepository extends JpaRepository<Contraindicate, Long> {
    Optional<Contraindicate> findByPillAAndPillB(String pill_a, String pill_b);
}
