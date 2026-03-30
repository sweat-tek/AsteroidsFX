package dk.sdu.mmmi.cbse.main;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main extends Application {

    private AnnotationConfigApplicationContext context;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {


        // Spring
        context = new AnnotationConfigApplicationContext(ModuleConfig.class);


        Game game = context.getBean(Game.class);

        // Start game
        game.start(stage);
        game.render();
    }

    @Override
    public void stop() throws Exception {
        if (context != null) {
            context.close();
        }
    }
}
