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
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.Set;
import java.util.HashSet;

/**
 *
 * @author jcs
 */
class Game {
    private int enemyKills = 0;
    private int asteroidKills = 0;

    private final Text asteroidKillText = new Text(10, 20, "Asteroids destroyed: 0");
    private final Text enemyKillText = new Text(10, 40, "Enemies destroyed: 0");

    private final GameData gameData = new GameData();
    private final World world = new World();
    private final Map<Entity, Polygon> polygons = new ConcurrentHashMap<>();
    private final Pane gameWindow = new Pane();
    private final List<IGamePluginService> gamePluginServices;
    private final List<IEntityProcessingService> entityProcessingServiceList;
    private final List<IPostEntityProcessingService> postEntityProcessingServices;

    Game(List<IGamePluginService> gamePluginServices, List<IEntityProcessingService> entityProcessingServiceList, List<IPostEntityProcessingService> postEntityProcessingServices) {
        this.gamePluginServices = gamePluginServices;
        this.entityProcessingServiceList = entityProcessingServiceList;
        this.postEntityProcessingServices = postEntityProcessingServices;
    }

    public void start(Stage window) throws Exception {
        gameWindow.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        gameWindow.getChildren().addAll(asteroidKillText, enemyKillText);

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

        for (IGamePluginService plugin : gamePluginServices) {
            plugin.start(gameData, world);
        }

        for (Entity entity : world.getEntities()) {
            Polygon polygon = new Polygon(entity.getPolygonCoordinates());
            polygon.setFill(entity.getColor());
            polygon.setStroke(Color.BLACK);
            polygons.put(entity, polygon);
            gameWindow.getChildren().add(polygon);
        }

        window.setScene(scene);
        window.setTitle("ASTEROIDS");
        window.show();
    }

    public void render() {
        new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (lastUpdate > 0) {
                    float deltaSeconds = (now - lastUpdate) / 1_000_000_000f;
                    gameData.setDelta(deltaSeconds);
                }
                lastUpdate = now;

                update();
                draw();
                gameData.getKeys().update();
            }
        }.start();
    }

    private void update() {
        for (IEntityProcessingService processor : entityProcessingServiceList) {
            processor.process(gameData, world);
        }
        for (IPostEntityProcessingService postProcessor : postEntityProcessingServices) {
            postProcessor.process(gameData, world);
        }
    }

    private void draw() {
        Set<Entity> toRemove = new HashSet<>();

        for (Entity e : polygons.keySet()) {
            if (!world.getEntities().contains(e)) {
                String className = e.getClass().getSimpleName();

                if (className.equalsIgnoreCase("Enemy")) {
                    enemyKills++;
                    enemyKillText.setText("Enemies destroyed: " + enemyKills);
                } else if (className.equalsIgnoreCase("Asteroid")) {
                    asteroidKills++;
                    asteroidKillText.setText("Asteroids destroyed: " + asteroidKills);
                }

                Polygon p = polygons.get(e);
                if (p != null) {
                    gameWindow.getChildren().remove(p);
                }
                toRemove.add(e);
            }
        }

        for (Entity e : toRemove) {
            polygons.remove(e);
        }

        for (Entity e : world.getEntities()) {
            Polygon polygon = polygons.get(e);
            if (polygon == null) {
                polygon = new Polygon(e.getPolygonCoordinates());
                polygon.setFill(e.getColor());
                polygon.setStroke(Color.BLACK);
                polygons.put(e, polygon);
                gameWindow.getChildren().add(polygon);
            }

            polygon.setTranslateX(e.getX());
            polygon.setTranslateY(e.getY());
            polygon.setRotate(e.getRotation());
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
