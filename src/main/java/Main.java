import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import root.service.ControllerService;
import root.service.ControllerServiceImpl;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("root/config");
        AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
        ControllerService controllerService = (ControllerServiceImpl) autowireCapableBeanFactory.getBean("controllerServiceImpl");
        BorderPane root = controllerService.execute();

        Scene scene = new Scene(root);
        primaryStage.setTitle("Excel parser");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
