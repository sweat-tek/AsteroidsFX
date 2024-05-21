package dk.sdu.mmmi.cbse.score;

import dk.sdu.mmmi.cbse.common.services.IScoreService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ScoreSystem implements IScoreService {
    private Long totalScore = 0L;

    public static void main(String[] args){
        SpringApplication.run(ScoreSystem.class, args);
    }

    @GetMapping("/score")
    public Long increaseScore(@RequestParam(value= "points") Long points) {
        totalScore += points;
        return totalScore;
    }

    @GetMapping("/")
    public String showScore() {
        return "Current score: " + totalScore;
    }

    @Override
    public Long addToTotalScore(Long point) {
        return null;
    }

    @Override
    public Long getTotalScore() {
        return null;
    }
}