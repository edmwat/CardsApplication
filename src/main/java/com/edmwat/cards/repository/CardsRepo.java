package com.edmwat.cards.repository;

import com.edmwat.cards.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardsRepo extends JpaRepository<Card, Long> {
    Optional<List<Card>> findByUserId(String userId);

    Optional<Card> findByIdAndUserId(Long id, String name);
}
