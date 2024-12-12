/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */

package carrace;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ddzul
 */

public class MenuController implements Initializable {

    @FXML
    private ImageView playBtn;
    @FXML
    private ImageView exitBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playBtn.setOnMouseClicked(event -> startGame());
        exitBtn.setOnMouseClicked(event -> exitApplication());
    }

    private void startGame() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
            javafx.scene.Scene gameScene = new javafx.scene.Scene(loader.load());
            Stage stage = (Stage) playBtn.getScene().getWindow(); 
            stage.setScene(gameScene); 
            stage.show(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exitApplication() {
        System.exit(0); 
    }
}
