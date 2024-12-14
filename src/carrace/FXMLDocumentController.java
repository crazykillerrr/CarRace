/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */

package carrace;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author ddzul
 */

public class FXMLDocumentController implements Initializable {

    @FXML
    public ImageView user;  
    @FXML
    public AnchorPane scene;
    @FXML
    private Label gameOverLabel;  
    @FXML
    public Label scoreLabel;  
    @FXML
    private Label finalScoreLabel;  

    private double step = 5;

    private Random random = new Random();
    User userController;
    Enemy enemyController;

    public int score = 0;  
    private Timeline moveForwardTimeline;
    private boolean gameOver = false;
    @FXML
    private ImageView track;
    @FXML
    private ImageView exitBtn2;
    @FXML
    private ImageView restartBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userController = new User(scene, user, this);
        enemyController = new Enemy(scene, user, this);

        user.setFocusTraversable(true);
        user.requestFocus();
        scene.setOnKeyPressed(this::onKeyPressed);

        gameOverLabel.setVisible(false);
        finalScoreLabel.setVisible(false);
        
        enemyController.startEnemySpawning();
        startMovingForward();
    }

    private void startMovingForward() {
        moveForwardTimeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
            if (!gameOver) {
                double y = user.getLayoutY();
                user.setLayoutY(y - step); 
            }
        }));
        moveForwardTimeline.setCycleCount(Timeline.INDEFINITE);
        moveForwardTimeline.play();
    }

    @FXML
    private void onKeyPressed(KeyEvent event) {
        if (gameOver) return; 
        userController.movement(event.getCode());
    }

    public void endGame() {
        gameOver = true;
        moveForwardTimeline.stop(); 

        gameOverLabel.setVisible(true);
        finalScoreLabel.setVisible(true);
        finalScoreLabel.setText("Final Score: " + score);
       
        enemyController.stopEnemies();
        loadGameOverScreen();
    }

    private void loadGameOverScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GameOver.fxml"));
            Scene gameOverScene = new Scene(loader.load());

            GameOverController controller = loader.getController();
            controller.setFinalScore(score); 

           
            Stage stage = (Stage) scene.getScene().getWindow();
            stage.setScene(gameOverScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void exitGame() {
        System.exit(0); 
    }

    public void restartGame() {
    
    enemyController.stopEnemies();
    enemyController.clearEnemies();
    moveForwardTimeline.stop();  
    user.setLayoutX(367);
    user.setLayoutY(481);

    score = 0;
    scoreLabel.setText("Score: " + score);

    gameOverLabel.setVisible(false);
    finalScoreLabel.setVisible(false);

    startMovingForward();
    enemyController.startEnemySpawning();
}
}
