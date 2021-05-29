import javafx.application.*;
import javafx.beans.value.ObservableValue;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.util.Duration;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class main extends Application {
    AudioPlayer player = new AudioPlayer();
    public static void main(String[] args) {
        launch(args);
        AudioPlayer player = new AudioPlayer();
        player.addAudio("music2.m4a");
        player.playAudio();
    }

    double currentProgress = 0;
    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        primaryStage.setTitle("MP3 Player");
        String userHome = System.getProperty("user.home");
        String desktop = userHome+"/Desktop/Music/";
        System.out.println(desktop+"/music.mp3");
        player.addAudio(desktop+"/music.mp3");
        File mediaFile = new File(desktop+"/music.mp3");
        Media media = new Media(mediaFile.toURI().toString());
        getAudioLength(media);
        Button btn = new Button();
        double sliderWidth = 200;

        final Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(50);
        slider.setMinWidth(sliderWidth);
        slider.setMaxWidth(sliderWidth);

        final ProgressBar pb = new ProgressBar(0);
        pb.setMinWidth(sliderWidth);
        pb.setMaxWidth(sliderWidth);

        // ProgressBar pb = new ProgressBar();
        StackPane root = new StackPane();
        root.getChildren().add(button(btn));
        // root.getChildren().add(progressBar(pb));
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

    public void pause(Button btn) {
        System.out.println("Pause");
        // player.stop();
        btn.setText("❙❙");
    }
    public void play(Button btn) {
        System.out.println("Play");
        // player.play();
        btn.setText("▶︎");
    }
    public double getAudioLength(Media media) throws InterruptedException {
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                System.out.println("Duration: "+media.getDuration().toMinutes());
            }
        });
        return 0;
    }

    public Button button(Button btn) {
        btn.setText("▶︎");
        btn.setFont(Font.font("verdana",20));
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (btn.getText().equals("❙❙")) {
                    play(btn);
                }
                else if (btn.getText().equals("▶︎")) {
                    pause(btn);
                }
            }
        });
        return btn;
    }
    public ProgressBar progressBar(ProgressBar pb) {
        Timer currentProgressTimer = new Timer();
        currentProgressTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                currentProgress += 0.01;
                System.out.println(currentProgress);
            }
        }, 100, 100);
        System.out.println(currentProgress);
        pb.setProgress(currentProgress);
        return pb;
    }

}