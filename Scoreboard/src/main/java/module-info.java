
import dk.sdu.mmmi.cbse.common.services.IScoreService;
import dk.sdu.mmmi.cbse.score.ScoreSystem;

module ScoreBoard {
    requires Common;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.web;
    requires spring.context;
    provides IScoreService with ScoreSystem;

}