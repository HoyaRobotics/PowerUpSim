package ca.team4152.frcsim;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
This is the entry point of the program.
 */
public class Main extends Application{

    @Override
    public void start(Stage stage){
        Window window = new Window();
        stage.setScene(window);

        stage.setResizable(false);
        stage.setTitle("FIRST Power-Up Game Simulator");
        stage.show();
    }

    public static void main(String[] args){
        Main.launch(args);
    }
}