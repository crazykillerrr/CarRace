/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */

package carrace;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author ddzul
 */

public class User implements Character {
    private final AnchorPane scene;
    private final ImageView imageView;
    private final FXMLDocumentController controller;
    private final double step = 5;

    public User(AnchorPane scene, ImageView imageView, FXMLDocumentController controller) {
        this.scene = scene;
        this.imageView = imageView;
        this.controller = controller;
    }

    public void movement(KeyCode code) {
        switch (code) {
            case RIGHT: 
                move(step, 0);
                break;
            case LEFT: 
                move(-step, 0);
                break;
            case DOWN: 
                move(0, step);
                break;
            case UP: 
                move(0, -step);
                break;
        }

        preventOutOfBounds();
    }

    @Override
    public void move(double deltaX, double deltaY) {
        imageView.setLayoutX(imageView.getLayoutX() + deltaX);
        imageView.setLayoutY(imageView.getLayoutY() + deltaY);
    }

    @Override
    public void preventOutOfBounds() {
        double x = imageView.getLayoutX();
        double y = imageView.getLayoutY();

        if (x < 100) {
            imageView.setLayoutX(100);
        } else if (x > 500) {
            imageView.setLayoutX(500);
        }

        if (y < 0) {
            imageView.setLayoutY(0);
        } else if (y > scene.getHeight() - imageView.getFitHeight()) {
            imageView.setLayoutY(scene.getHeight() - imageView.getFitHeight());
        }
    }

    @Override
    public double getX() {
        return imageView.getLayoutX();
    }

    @Override
    public double getY() {
        return imageView.getLayoutY();
    }
}
