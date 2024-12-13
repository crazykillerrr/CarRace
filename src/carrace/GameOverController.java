/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package carrace;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author ddzul
 */
public class GameOverController implements Initializable {

    @FXML
    private Label finalScoreLabel; 
    @FXML
    private ImageView exitBtn2;  
    @FXML
    private ImageView restartBtn;  


    private int finalScore;  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        restartBtn.setOnMouseClicked(event -> restartGame());
        exitBtn2.setOnMouseClicked(event -> exitGame());
    }

    public void setFinalScore(int score) {
        this.finalScore = score;
        finalScoreLabel.setText("Your Score: " + score);  
    }
    private void restartGame() {
    try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
            javafx.scene.Scene gameScene = new javafx.scene.Scene(loader.load());
            Stage stage = (Stage) exitBtn2.getScene().getWindow(); 
            stage.setScene(gameScene); 
            stage.show(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void exitGame() {
        System.exit(0);  
    }
}
