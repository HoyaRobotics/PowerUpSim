package ca.team4152.frcsim;

import ca.team4152.frcsim.utils.PowerUp;
import ca.team4152.frcsim.utils.RobotTask;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/*
THIS IS A MESS
I REALLY DON'T LIKE JAVAFX
AHH
 */
public class Window extends Scene{

    public static final Pane ROOT = new Pane();
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 720;

    private Simulator simulator = new Simulator();

    public Window(){
        super(ROOT, WIDTH, HEIGHT);
        loadElements();
    }

    private void loadElements(){
        Image titleImage = null;
        try{ titleImage = new Image(new FileInputStream("res/title.png")); }catch(FileNotFoundException e){ System.err.println("Could not find file for title.");}
        Image arrowImage = null;
        try{ arrowImage = new Image(new FileInputStream("res/arrow.png")); }catch(FileNotFoundException e){ System.err.println("Could not find file for arrow."); }

        ImageView title = new ImageView(titleImage);
        title.setTranslateX(10);
        title.setTranslateY(10);
        ImageView arrow = new ImageView(arrowImage);

        Button simulateButton = new Button("Simulate");
        simulateButton.setTranslateX(600);
        simulateButton.setTranslateY(47);
        simulateButton.setGraphic(arrow);
        simulateButton.setPadding(new Insets(10, 10, 10, 10));
        simulateButton.setPrefSize(240, 50);
        simulateButton.setStyle("-fx-font: 34 verdana; -fx-base: #28A7A5; -fx-text-fill: #08383A;");

        RobotInput robotInput0 = generateRobotInput(0);
        RobotInput robotInput1 = generateRobotInput(1);
        RobotInput robotInput2 = generateRobotInput(2);
        PowerUpInput powerUpInput = generatePowerUpInput();

        simulateButton.setOnAction(event -> {
            simulator.run(robotInput0.getSpeed(), robotInput1.getSpeed(), robotInput2.getSpeed(),
                    powerUpInput.getPowerUps(), robotInput0.getTasks(), robotInput1.getTasks(), robotInput2.getTasks());
        });

        ROOT.setBackground(new Background(new BackgroundFill(Color.color(0.35, 0.80, 0.94), null, null)));
        ROOT.getChildren().addAll(title, simulateButton, robotInput0.getGui(), robotInput1.getGui(), robotInput2.getGui(), powerUpInput.getGui());
    }

    //medium cyan - 0.09, 0.47, 0.58
    //dark cyan - 0.22, 0.41, 0.42
    private RobotInput generateRobotInput(int number){
        RobotInput robotInput = new RobotInput();

        VBox robotInputBox = new VBox();
        robotInputBox.setTranslateX(20 + (number * 220));
        robotInputBox.setTranslateY(190);
        robotInputBox.setPrefSize(200, 520);
        robotInputBox.setBackground(new Background(new BackgroundFill(Color.color(0.22, 0.41, 0.42), null, null)));

        Label inputTitle = new Label("Robot #" + (number + 1));
        inputTitle.setStyle("-fx-font: 22 verdana; -fx-background-color: #28A7A5");
        inputTitle.setPadding(new Insets(3, 42, 3, 42));
        inputTitle.setTranslateX(5);
        inputTitle.setTranslateY(5);

        HBox speedInput = new HBox();
        Label speedLabel = new Label("Speed (ft/s):");
        speedLabel.setTranslateY(5);
        speedLabel.setStyle("-fx-font: 18 verdana;");
        TextField speedField = new TextField();
        speedField.setText("10");
        speedField.setPrefSize(50, 20);
        speedField.setTranslateX(20);
        speedField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d*")){
                    speedField.setText(newValue.replaceAll("\\D", ""));
                }else if(!newValue.isEmpty()){
                    robotInput.setSpeed(Double.parseDouble(newValue));
                }
            }
        });
        speedField.setStyle("-fx-font: 16 verdana; -fx-base: #28A7A5");

        speedInput.setTranslateX(5);
        speedInput.setPadding(new Insets(10, 20, 0, 0));
        speedInput.getChildren().addAll(speedLabel, speedField);

        HBox tasksInput = new HBox();

        Label tasksTitle = new Label("Task List");
        tasksTitle.setStyle("-fx-font: 22 verdana; -fx-background-color: #28A7A5");
        tasksTitle.setPadding(new Insets(3, 17, 3, 17));
        tasksTitle.setTranslateX(5);
        tasksTitle.setTranslateY(20);

        VBox taskPane = new VBox();
        taskPane.setSpacing(2);
        taskPane.setPrefHeight(375);

        ScrollPane taskList = new ScrollPane();
        taskList.setTranslateY(25);
        taskList.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        taskList.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        taskList.setFitToWidth(true);
        taskList.setContent(taskPane);
        taskList.setStyle("-fx-base: #38686A");

        MenuBar addBar = new MenuBar();
        addBar.setTranslateX(18);
        addBar.setTranslateY(19);
        addBar.setStyle("-fx-font: 18 verdana; -fx-base: #78F7F5; -fx-padding: 0 0 0 0");
        Menu addMenu = new Menu("+");
        addBar.getMenus().add(addMenu);

        MenuItem autoMove = new MenuItem("Autonomous Move");
        autoMove.setOnAction(event -> { taskPane.getChildren().add(generateTaskGraphic(RobotTask.AUTO_MOVE, robotInput)); });
        MenuItem deliverExchange = new MenuItem("Deliver Cube to Exchange");
        deliverExchange.setOnAction(event -> {
            taskPane.getChildren().add(generateTaskGraphic(RobotTask.GET_CUBE, robotInput));
            taskPane.getChildren().add(generateTaskGraphic(RobotTask.CUBE_EXCHANGE, robotInput));
        });
        MenuItem deliverAlliance = new MenuItem("Deliver Cube to Your Switch");
        deliverAlliance.setOnAction(event -> {
            taskPane.getChildren().add(generateTaskGraphic(RobotTask.GET_CUBE, robotInput));
            taskPane.getChildren().add(generateTaskGraphic(RobotTask.CUBE_ALLIANCE_SWITCH, robotInput));
        });
        MenuItem deliverOpponent = new MenuItem("Deliver Cube to Their Switch");
        deliverOpponent.setOnAction(event -> {
            taskPane.getChildren().add(generateTaskGraphic(RobotTask.GET_CUBE, robotInput));
            taskPane.getChildren().add(generateTaskGraphic(RobotTask.CUBE_OPPONENT_SWITCH, robotInput));
        });
        MenuItem deliverScale = new MenuItem("Deliver Cube to the Scale");
        deliverScale.setOnAction(event -> {
            taskPane.getChildren().add(generateTaskGraphic(RobotTask.GET_CUBE, robotInput));
            taskPane.getChildren().add(generateTaskGraphic(RobotTask.CUBE_SCALE, robotInput));
        });
        MenuItem climb = new MenuItem("Climb");
        climb.setOnAction(event -> { taskPane.getChildren().add(generateTaskGraphic(RobotTask.CLIMB, robotInput)); });
        MenuItem wait = new MenuItem("Wait");
        wait.setOnAction(event -> { taskPane.getChildren().add(generateTaskGraphic(RobotTask.WAIT, robotInput)); });
        addMenu.getItems().addAll(autoMove, deliverExchange, deliverAlliance, deliverOpponent, deliverScale, climb);

        tasksInput.getChildren().addAll(tasksTitle, addBar);

        robotInputBox.getChildren().addAll(inputTitle, speedInput, tasksInput, taskList);

        robotInput.setGui(robotInputBox);
        return robotInput;
    }

    private HBox generateTaskGraphic(int id, RobotInput input){
        HBox task = new HBox();
        task.setMinHeight(50);
        task.setBackground(new Background(new BackgroundFill(Color.color(0.42, 0.61, 0.62), null, null)));
        task.setPadding(new Insets(0, 0, 2, 0));

        VBox taskText = new VBox();
        Label taskLine1 = new Label();
        taskLine1.setTranslateX(5);
        taskLine1.setTranslateY(2);
        taskLine1.setStyle("-fx-font: 18 verdana; -fx-base: #78F7F5");
        Label taskLine2 = new Label();
        taskLine2.setTranslateX(5);
        taskLine2.setStyle("-fx-font: 18 verdana; -fx-base: #78F7F5");
        taskText.getChildren().addAll(taskLine1, taskLine2);

        Button removeTask = new Button("X");
        removeTask.setMinSize(30, 30);
        removeTask.setTranslateY(9);
        removeTask.setTranslateX(-3);
        removeTask.setStyle("-fx-font: 18 arial;");

        Pane spacer = new Pane();
        task.setHgrow(spacer, Priority.ALWAYS);

        RobotTask addTask = RobotTask.newTask(id);
        if(id == RobotTask.AUTO_MOVE){
            taskLine1.setText("Autonomous");
            taskLine2.setText("Movement");
        }else if(id == RobotTask.GET_CUBE){
            taskLine1.setText("Pick Up");
            taskLine2.setText("Cube");
        }else if(id == RobotTask.CUBE_EXCHANGE){
            taskLine1.setText("Deliver Cube");
            taskLine2.setText("to Exchange");
        }else if(id == RobotTask.CUBE_ALLIANCE_SWITCH){
            taskLine1.setText("Deliver Cube");
            taskLine2.setText("to Your Switch");
        }else if(id == RobotTask.CUBE_OPPONENT_SWITCH){
            taskLine1.setText("Deliver Cube");
            taskLine2.setText("to Their Switch");
        }else if(id == RobotTask.CUBE_SCALE){
            taskLine1.setText("Deliver Cube");
            taskLine2.setText("to Scale");
        }else if(id == RobotTask.CLIMB){
            taskLine1.setText("Climb to");
            taskLine2.setText("Face the Boss");
        }else if(id == RobotTask.WAIT){
            taskLine1.setText("Wait");
            taskLine2.setText("Time (s):");
            taskLine2.setStyle("-fx-font: 16 verdana;");
            TextField field = new TextField();
            field.setPrefSize(40, 20);
            field.setTranslateX(10);
            field.setTranslateY(20);
            field.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if(!newValue.matches("\\d*")){
                        field.setText(newValue.replaceAll("\\D", ""));
                    }else if(!newValue.isEmpty()){
                        addTask.setTimeToComplete(Integer.parseInt(newValue));
                    }
                }
            });
            task.getChildren().addAll(taskText, field, spacer, removeTask);
        }

        removeTask.setOnAction(event -> {
            ((VBox) task.getParent()).getChildren().remove(task);
            input.getTasks().remove(addTask);
        });

        if(task.getChildren().isEmpty()) task.getChildren().addAll(taskText, spacer, removeTask);
        input.getTasks().add(addTask);
        return task;
    }

    private PowerUpInput generatePowerUpInput(){
        PowerUpInput powerUpInput = new PowerUpInput();

        VBox powerUpInputBox = new VBox();
        powerUpInputBox.setTranslateX(780);
        powerUpInputBox.setTranslateY(190);
        powerUpInputBox.setPrefSize(200, 520);
        powerUpInputBox.setBackground(new Background(new BackgroundFill(Color.color(0.22, 0.41, 0.42), null, null)));

        Label inputTitle = new Label("Power Ups");
        inputTitle.setStyle("-fx-font: 22 verdana; -fx-background-color: #28A7A5");
        inputTitle.setPadding(new Insets(3, 36, 3, 36));
        inputTitle.setTranslateX(5);
        inputTitle.setTranslateY(5);

        Label info1 = new Label("If start time is -1");
        info1.setStyle("-fx-font: 18 verdana;");
        info1.setTranslateX(5);
        info1.setTranslateY(5);
        Label info2 = new Label("they activate ASAP.");
        info2.setStyle("-fx-font: 18 verdana;");
        info2.setTranslateX(5);
        info2.setTranslateY(2);

        HBox powerUpTitle = new HBox();
        powerUpTitle.setSpacing(4);

        Label levelTitle = new Label("Level");
        levelTitle.setStyle("-fx-font: 20 verdana; -fx-background-color: #28A7A5");
        levelTitle.setPadding(new Insets(5, 7, 5, 7));
        levelTitle.setTranslateX(5);
        levelTitle.setTranslateY(15);

        Label startTitle = new Label("Start");
        startTitle.setStyle("-fx-font: 20 verdana; -fx-background-color: #28A7A5");
        startTitle.setPadding(new Insets(5, 7, 5, 7));
        startTitle.setTranslateX(5);
        startTitle.setTranslateY(15);

        MenuBar addBar = new MenuBar();
        addBar.setTranslateX(10);
        addBar.setTranslateY(13);
        addBar.setStyle("-fx-font: 18 verdana; -fx-base: #78F7F5; -fx-padding: 0 0 0 0");
        addBar.setMaxWidth(30);
        Menu addMenu = new Menu("+");
        addBar.getMenus().add(addMenu);

        powerUpTitle.getChildren().addAll(levelTitle, startTitle, addBar);

        VBox itemPane = new VBox();
        itemPane.setSpacing(2);
        itemPane.setPrefHeight(375);

        ScrollPane itemList = new ScrollPane();
        itemList.setTranslateY(21);
        itemList.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        itemList.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        itemList.setFitToWidth(true);
        itemList.setContent(itemPane);
        itemList.setStyle("-fx-base: #38686A");

        MenuItem force = new MenuItem("Force");
        force.setOnAction(event -> {
            itemPane.getChildren().add(generatePowerUpGraphic(PowerUp.FORCE, powerUpInput));
        });
        MenuItem boost = new MenuItem("Boost");
        boost.setOnAction(event -> {
            itemPane.getChildren().add(generatePowerUpGraphic(PowerUp.BOOST, powerUpInput));
        });
        MenuItem levitate = new MenuItem("Levitate");
        levitate.setOnAction(event -> {
            itemPane.getChildren().add(generatePowerUpGraphic(PowerUp.LEVITATE, powerUpInput));
        });
        MenuItem comingSoon = new MenuItem("SoonTM");
        addMenu.getItems().addAll(force, boost, levitate);

        powerUpInputBox.getChildren().addAll(inputTitle, info1, info2, powerUpTitle, itemList);

        powerUpInput.setGui(powerUpInputBox);
        return powerUpInput;
    }

    private HBox generatePowerUpGraphic(int id, PowerUpInput input){
        HBox powerUp = new HBox();
        powerUp.setMinHeight(50);
        powerUp.setBackground(new Background(new BackgroundFill(Color.color(0.42, 0.61, 0.62), null, null)));
        powerUp.setPadding(new Insets(0, 0, 2, 0));

        Label powerUpDesc = new Label();
        powerUpDesc.setTranslateX(5);
        powerUpDesc.setTranslateY(5);
        powerUpDesc.setStyle("-fx-font: 30 verdana;");

        Button removeTask = new Button("X");
        removeTask.setMinSize(30, 30);
        removeTask.setTranslateY(9);
        removeTask.setTranslateX(-3);
        removeTask.setStyle("-fx-font: 18 arial;");

        TextField levelField = new TextField();
        levelField.setTranslateX(-20);
        levelField.setTranslateY(9);
        levelField.setMaxWidth(35);
        levelField.setText("1");
        levelField.setStyle("-fx-font: 16 verdana");

        TextField startField = new TextField();
        startField.setTranslateX(-17);
        startField.setTranslateY(9);
        startField.setMaxWidth(55);
        startField.setText("-1");
        startField.setStyle("-fx-font: 16 verdana");

        Pane spacer = new Pane();
        powerUp.setHgrow(spacer, Priority.ALWAYS);

        PowerUp addPowerUp;
        int startTime = -1, level = 1;
        if(id == PowerUp.LEVITATE){
            powerUpDesc.setText("L");
            levelField.setText("3");
        }else if(id == PowerUp.BOOST){
            powerUpDesc.setText("B");
        }else if(id == PowerUp.FORCE){
            powerUpDesc.setText("F");
        }
        addPowerUp = PowerUp.newPowerUp(id, startTime, level);

        levelField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(id == PowerUp.LEVITATE){
                    levelField.setText("3");
                }
                if(!newValue.matches("\\d*")){
                    levelField.setText(newValue.replaceAll("\\D", ""));
                }else if(newValue.length() > 1){
                    levelField.setText(newValue.substring(0, 1));
                }else if(!newValue.isEmpty()){
                    int newLevel = Integer.parseInt(newValue);
                    if(newLevel < 1){
                        levelField.setText("1");
                    }else if(newLevel > 3){
                        levelField.setText("3");
                    }
                    addPowerUp.setLevel(newLevel);
                }
            }
        });
        startField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("-?\\d*")){
                    startField.setText(newValue.replaceAll("\\D", ""));
                }else if(newValue.length() > 3){
                    startField.setText(newValue.substring(0, 3));
                }else if(!newValue.isEmpty() && !newValue.equals("-")){
                    int newStart = Integer.parseInt(newValue);
                    if(newStart < -1){
                        startField.setText("-1");
                    }else if(newStart > 150){
                        startField.setText("150");
                    }
                    addPowerUp.setStartTime(newStart);
                }
            }
        });
        removeTask.setOnAction(event -> {
            ((VBox) powerUp.getParent()).getChildren().remove(powerUp);
            input.getPowerUps().remove(addPowerUp);
        });

        powerUp.getChildren().addAll(powerUpDesc, spacer, levelField, startField, removeTask);
        input.getPowerUps().add(addPowerUp);
        return powerUp;
    }

    private class RobotInput{
        private VBox gui = new VBox();
        private double speed = 10;
        private List<RobotTask> tasks = new ArrayList<>();

        public void setGui(VBox gui){ this.gui = gui; }
        public VBox getGui(){ return gui; }
        public void setSpeed(double speed){ this.speed = speed; }
        public double getSpeed(){ return speed; }
        public List<RobotTask> getTasks(){ return tasks; }
    }

    private class PowerUpInput{
        private VBox gui = new VBox();
        private List<PowerUp> powerUps = new ArrayList<>();

        public void setGui(VBox gui){ this.gui = gui; }
        public VBox getGui(){ return gui; }
        public List<PowerUp> getPowerUps(){ return powerUps; }
    }
}