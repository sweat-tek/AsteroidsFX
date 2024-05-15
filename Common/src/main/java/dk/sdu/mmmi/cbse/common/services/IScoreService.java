package dk.sdu.mmmi.cbse.common.services;

public interface IScoreService {
    Long addToTotalScore(Long point);

    Long getTotalScore();
}