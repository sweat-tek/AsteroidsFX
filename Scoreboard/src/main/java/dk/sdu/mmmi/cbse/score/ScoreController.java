package dk.sdu.mmmi.cbse.score;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ScoreController {


    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping("/score")
    public Long getScore() {
        return scoreService.getTotalScore();
    }

    @GetMapping("/addscore")
    public Long updateScore(@RequestParam(value = "point") Long point) {
        return scoreService.increaseTotalScore(point);
    }
}