package dk.sdu.mmmi.cbse.scoring.controller;

import dk.sdu.mmmi.cbse.scoring.model.Score;
import dk.sdu.mmmi.cbse.scoring.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scores")
public class ScoreController {
    
    private final ScoreService scoreService;
    
    @Autowired
    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }
    
    /**
     * Submit a new score
     * @param score the score to submit
     * @return the saved score with generated ID
     */
    @PostMapping
    public ResponseEntity<Score> submitScore(@RequestBody Score score) {
        Score savedScore = scoreService.saveScore(score);
        return new ResponseEntity<>(savedScore, HttpStatus.CREATED);
    }
    
    /**
     * Get the top 10 highest scores
     * @return list of the top 10 scores
     */
    @GetMapping("/top")
    public ResponseEntity<List<Score>> getTopScores() {
        List<Score> topScores = scoreService.getTopScores();
        return new ResponseEntity<>(topScores, HttpStatus.OK);
    }
    
    /**
     * Get all scores for a specific player
     * @param playerName the name of the player
     * @return list of scores for the player
     */
    @GetMapping("/player/{playerName}")
    public ResponseEntity<List<Score>> getScoresByPlayer(@PathVariable String playerName) {
        List<Score> playerScores = scoreService.getScoresByPlayer(playerName);
        return new ResponseEntity<>(playerScores, HttpStatus.OK);
    }
    
    /**
     * Get all scores in the database
     * @return list of all scores
     */
    @GetMapping
    public ResponseEntity<List<Score>> getAllScores() {
        List<Score> allScores = scoreService.getAllScores();
        return new ResponseEntity<>(allScores, HttpStatus.OK);
    }
    
    /**
     * Reset all scores in the database
     * @return confirmation message
     */
    @DeleteMapping("/reset")
    public ResponseEntity<String> resetScores() {
        scoreService.resetScores();
        return new ResponseEntity<>("All scores have been reset", HttpStatus.OK);
    }
}
