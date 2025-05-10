/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author jcs
 */
class Game {

    private final GameData gameData = new GameData();
    private final World world = new World();
    private final Map<Entity, Polygon> polygons = new ConcurrentHashMap<>();
    private final Pane gameWindow = new Pane();
    private final List<IGamePluginService> gamePluginServices;
    private final List<IEntityProcessingService> entityProcessingServiceList;
    private final List<IPostEntityProcessingService> postEntityProcessingServices;
    
    // Game state variables
    private boolean gameOver = false;
    private int score = 0;
    private Text scoreText;
    private Text gameOverText;
    private RestTemplate restTemplate = new RestTemplate();
    private static final String SCORING_SERVICE_URL = "http://localhost:8080/api/scores";

    Game(List<IGamePluginService> gamePluginServices, List<IEntityProcessingService> entityProcessingServiceList, List<IPostEntityProcessingService> postEntityProcessingServices) {
        this.gamePluginServices = gamePluginServices;
        this.entityProcessingServiceList = entityProcessingServiceList;
        this.postEntityProcessingServices = postEntityProcessingServices;
    }

    public void start(Stage window) throws Exception {
        scoreText = new Text(10, 20, "Destroyed asteroids: 0");
        gameWindow.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        gameWindow.getChildren().add(scoreText);
        
        // Create game over text
        gameOverText = new Text(gameData.getDisplayWidth()/2, gameData.getDisplayHeight()/2, 
                "GAME OVER\nPress R to restart");
        gameOverText.setFill(Color.WHITE);
        gameOverText.setFont(new Font(30));
        gameOverText.setTextAlignment(TextAlignment.CENTER);
        gameOverText.setVisible(false);
        gameOverText.setTranslateX(-100); // Center the text
        gameWindow.getChildren().add(gameOverText);

        Scene scene = new Scene(gameWindow);
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.LEFT)) {
                gameData.getKeys().setKey(GameKeys.LEFT, true);
            }
            if (event.getCode().equals(KeyCode.RIGHT)) {
                gameData.getKeys().setKey(GameKeys.RIGHT, true);
            }
            if (event.getCode().equals(KeyCode.UP)) {
                gameData.getKeys().setKey(GameKeys.UP, true);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                gameData.getKeys().setKey(GameKeys.SPACE, true);
            }
            if (event.getCode().equals(KeyCode.R)) {
                if (gameOver) {
                    restartGame();
                }
            }
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.LEFT)) {
                gameData.getKeys().setKey(GameKeys.LEFT, false);
            }
            if (event.getCode().equals(KeyCode.RIGHT)) {
                gameData.getKeys().setKey(GameKeys.RIGHT, false);
            }
            if (event.getCode().equals(KeyCode.UP)) {
                gameData.getKeys().setKey(GameKeys.UP, false);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                gameData.getKeys().setKey(GameKeys.SPACE, false);
            }

        });

        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : getGamePluginServices()) {
            iGamePlugin.start(gameData, world);
        }
        for (Entity entity : world.getEntities()) {
            Polygon polygon = new Polygon(entity.getPolygonCoordinates());
            polygons.put(entity, polygon);
            gameWindow.getChildren().add(polygon);
        }
        window.setScene(scene);
        window.setTitle("ASTEROIDS");
        window.show();
    }

    public void render() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!gameOver) {
                    update();
                }
                draw();
                gameData.getKeys().update();
            }

        }.start();
    }
    
    /**
     * Restarts the game by resetting the world, game state, and plugins
     */
    private void restartGame() {
        // Submit score to scoring service
        submitScore();
        
        // Clear current entities
        for (Entity entity : world.getEntities()) {
            world.removeEntity(entity);
        }
        polygons.clear();
        gameWindow.getChildren().removeIf(node -> node instanceof Polygon);
        
        // Reset game state
        gameOver = false;
        score = 0;
        scoreText.setText("Destroyed asteroids: 0");
        gameOverText.setVisible(false);
        
        // Restart all game plugins
        for (IGamePluginService iGamePlugin : getGamePluginServices()) {
            iGamePlugin.start(gameData, world);
        }
        
        // Add new entities to the window
        for (Entity entity : world.getEntities()) {
            Polygon polygon = new Polygon(entity.getPolygonCoordinates());
            polygons.put(entity, polygon);
            gameWindow.getChildren().add(polygon);
        }
    }
    
    /**
     * Submits the final score to the scoring service
     */
    private void submitScore() {
        try {
            // Create score object (adapt to your actual Score class as needed)
            Map<String, Object> scoreData = new ConcurrentHashMap<>();
            scoreData.put("player", "Player1");
            scoreData.put("score", score);
            
            // Post score to the scoring service
            ResponseEntity<String> response = restTemplate.postForEntity(
                SCORING_SERVICE_URL, scoreData, String.class);
            
            System.out.println("Score submitted: " + response.getBody());
        } catch (Exception e) {
            System.err.println("Failed to submit score: " + e.getMessage());
        }
    }
    
    /**
     * Handles game over state
     */
    public void setGameOver() {
        if (!gameOver) {
            gameOver = true;
            gameOverText.setVisible(true);
            System.out.println("GAME OVER");
        }
    }
    
    /**
     * Increments the score when an asteroid is destroyed
     */
    public void incrementScore() {
        score++;
        scoreText.setText("Destroyed asteroids: " + score);
    }

    private void update() {
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }
        
        // Check for game over condition in collision detection
        for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices()) {
            postEntityProcessorService.process(gameData, world);
            
            // Check if player entity is gone (player was hit)
            boolean playerExists = false;
            for (Entity entity : world.getEntities()) {
                if (entity.getEntityType() != null && entity.getEntityType().equalsIgnoreCase("PLAYER")) {
                    playerExists = true;
                }
            }
            
            // If player is gone, set game over
            if (!playerExists && !gameOver) {
                setGameOver();
            }
        }
    }

    private void draw() {
        for (Entity polygonEntity : polygons.keySet()) {
            if (!world.getEntities().contains(polygonEntity)) {
                Polygon removedPolygon = polygons.get(polygonEntity);
                polygons.remove(polygonEntity);
                gameWindow.getChildren().remove(removedPolygon);
            }
        }

        for (Entity entity : world.getEntities()) {
            Polygon polygon = polygons.get(entity);
            if (polygon == null) {
                polygon = new Polygon(entity.getPolygonCoordinates());
                polygons.put(entity, polygon);
                gameWindow.getChildren().add(polygon);
            }
            polygon.setTranslateX(entity.getX());
            polygon.setTranslateY(entity.getY());
            polygon.setRotate(entity.getRotation());
        }

    }

    public List<IGamePluginService> getGamePluginServices() {
        return gamePluginServices;
    }

    public List<IEntityProcessingService> getEntityProcessingServices() {
        return entityProcessingServiceList;
    }

    public List<IPostEntityProcessingService> getPostEntityProcessingServices() {
        return postEntityProcessingServices;
    }

}
