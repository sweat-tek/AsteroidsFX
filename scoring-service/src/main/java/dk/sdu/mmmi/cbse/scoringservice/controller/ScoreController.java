package dk.sdu.mmmi.cbse.scoringservice.controller;

import dk.sdu.mmmi.cbse.scoringservice.model.Score;
import dk.sdu.mmmi.cbse.scoringservice.service.ScoreService;
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
    
    @PostMapping
    public ResponseEntity<Score> addScore(@RequestBody Score score) {
        Score savedScore = scoreService.saveScore(score);
        return new ResponseEntity<>(savedScore, HttpStatus.CREATED);
    }
    
    @GetMapping("/highscores")
    public ResponseEntity<List<Score>> getHighScores() {
        List<Score> highScores = scoreService.getHighScores();
        return new ResponseEntity<>(highScores, HttpStatus.OK);
    }
    
    @DeleteMapping
    public ResponseEntity<Void> resetScores() {
        scoreService.resetScores();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

