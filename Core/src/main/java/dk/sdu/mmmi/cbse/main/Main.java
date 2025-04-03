package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import java.lang.module.ModuleReference;
import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleDescriptor;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.ServiceLoader.Provider;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
import dk.sdu.mmmi.cbse.common.util.ServiceLocator;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main extends Application {



    public static void main(String[] args) {
        launch(Main.class);
    }

    @Override
    public void start(Stage window) throws Exception {
        
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ModuleConfig.class);

        for (String beanName : ctx.getBeanDefinitionNames()) {
            System.out.println(beanName);
        }
        
        Game game = ctx.getBean(Game.class);
        game.start(window);
        game.render();        

    }

}
