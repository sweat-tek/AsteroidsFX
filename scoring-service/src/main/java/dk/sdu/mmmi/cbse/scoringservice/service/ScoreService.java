package dk.sdu.mmmi.cbse.scoringservice.service;

import dk.sdu.mmmi.cbse.scoringservice.model.Score;
import dk.sdu.mmmi.cbse.scoringservice.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScoreService {
    
    private final ScoreRepository scoreRepository;
    
    @Autowired
    public ScoreService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }
    
    /**
     * Save a new score to the database
     * @param score the score to save
     * @return the saved score with generated ID
     */
    public Score saveScore(Score score) {
        score.setTimestamp(LocalDateTime.now());
        return scoreRepository.save(score);
    }
    
    /**
     * Get the top 10 highest scores
     * @return list of the top 10 scores
     */
    public List<Score> getHighScores() {
        return scoreRepository.findTop10ByOrderByScoreDesc();
    }
    
    /**
     * Get the top 10 highest scores (alias for getHighScores for API consistency)
     * @return list of the top 10 scores
     */
    public List<Score> getTopScores() {
        return getHighScores();
    }
    
    /**
     * Get all scores for a specific player
     * @param playerName the name of the player
     * @return list of scores for the player
     */
    public List<Score> getScoresByPlayer(String playerName) {
        return scoreRepository.findByPlayerNameOrderByScoreDesc(playerName);
    }
    
    /**
     * Get all scores in the database
     * @return list of all scores
     */
    public List<Score> getAllScores() {
        return scoreRepository.findAll();
    }
    
    /**
     * Delete all scores from the database
     */
    public void resetScores() {
        scoreRepository.deleteAll();
    }
}

