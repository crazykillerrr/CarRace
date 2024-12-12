/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */

package carrace;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import java.util.Random;
import javafx.scene.layout.AnchorPane;
/**
 *
 * @author ddzul
 */

public class Enemy implements Character {
    private final AnchorPane scene;
    private final ImageView imageView;
    private final FXMLDocumentController controller;
    private final Random random = new Random();
    private boolean gameOver = false;
    private final double minX = 100; 
    private final double maxX = 500; 

    public Enemy(AnchorPane scene, ImageView imageView, FXMLDocumentController controller) {
        this.scene = scene;
        this.imageView = imageView;
        this.controller = controller;
    }

    public void startEnemySpawning() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> spawnRandomEnemy()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void spawnRandomEnemy() {
        if (gameOver) return;

        String[] enemies = {"enemy1.png", "enemy2.png", "enemy3.png", "enemy4.png", "enemy5.png"};
        String enemyImage = enemies[random.nextInt(enemies.length)];

       
        ImageView enemy = new ImageView();
        enemy.setImage(new javafx.scene.image.Image(getClass().getResource("/img/" + enemyImage).toString()));
        enemy.setFitWidth(imageView.getFitWidth());
        enemy.setFitHeight(imageView.getFitHeight());

        // Set random position
        double randomX = getRandomXPosition(enemy);
        enemy.setLayoutX(randomX);
        enemy.setLayoutY(-100);
        scene.getChildren().add(enemy);

        moveEnemyDown(enemy);
    }

    public double getRandomXPosition(ImageView newEnemy) {
        double randomX;
        boolean validPosition;

        do {
            validPosition = true;
            randomX = minX + random.nextDouble() * (maxX - minX);
            newEnemy.setLayoutX(randomX);

          
            for (Node node : scene.getChildren()) {
                if (node instanceof ImageView && node != newEnemy && node != imageView) {
                    ImageView existingEnemy = (ImageView) node;
                    if (existingEnemy.getLayoutY() < 0 &&
                        existingEnemy.getBoundsInParent().intersects(newEnemy.getBoundsInParent())) {
                        validPosition = false;
                        break;
                    }
                }
            }
        } while (!validPosition);

        return randomX;
    }

    public void moveEnemyDown(ImageView enemy) {
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(enemy);
        transition.setCycleCount(1);
        transition.setInterpolator(javafx.animation.Interpolator.LINEAR);
        transition.setDuration(Duration.seconds(5));
        transition.setByY(scene.getHeight() + 10);

        Timeline collisionCheckTimeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
            if (enemy.getBoundsInParent().intersects(imageView.getBoundsInParent())) {
                scene.getChildren().remove(enemy); 
                controller.endGame(); 
            }
        }));

        collisionCheckTimeline.setCycleCount(Timeline.INDEFINITE);
        collisionCheckTimeline.play();

        transition.setOnFinished(event -> {
            scene.getChildren().remove(enemy); 
            collisionCheckTimeline.stop();

            if (!gameOver) {
                controller.score += 5; 
                controller.scoreLabel.setText("Score: " + controller.score);
            }
        });

        transition.play();
    }

    public void stopEnemies() {
        gameOver = true; 
    }

    public void clearEnemies() {
        scene.getChildren().removeIf(node -> node instanceof ImageView && node != imageView);
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

        if (x < minX) {
            imageView.setLayoutX(minX);
        } else if (x > maxX) {
            imageView.setLayoutX(maxX);
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
