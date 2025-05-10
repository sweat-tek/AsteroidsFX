package dk.sdu.mmmi.cbse.scoringservice.repository;

import dk.sdu.mmmi.cbse.scoringservice.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    /**
     * Find the top 10 scores ordered by score in descending order.
     * @return List of the top 10 highest scores
     */
    List<Score> findTop10ByOrderByScoreDesc();
    
    /**
     * Find scores by player name ordered by score in descending order.
     * @param playerName the name of the player
     * @return List of scores for the given player
     */
    List<Score> findByPlayerNameOrderByScoreDesc(String playerName);
}

