import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;

public class main extends Application {
    AudioPlayer player = new AudioPlayer();
    public static void main(String[] args) {
        launch(args);
    }

    double currentProgress = 0;
    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        primaryStage.setTitle("MP3 Player");
        AudioPlayer player = new AudioPlayer();
        getPlaylist(new File("src/music/"));
        // getAudioLength(media);
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
    public static List<File> getPlaylist(File directory) {
        File[] listOfFiles = directory.listFiles();
        Set<String> validFileTypes = Set.of("M4A", "WAV", "MP3");
        List<File> validFiles = new ArrayList<File>();
        for (File file : listOfFiles) {
            if (isFile(file) && hasExtension(file.getName(), validFileTypes)) {
                    validFiles.add(file);
            }
        }
        return validFiles;
    }
    public static boolean hasExtension(String filename, Set<String> extensions){
        for(String extension : extensions){
            if(filename.toLowerCase().endsWith(extension.toLowerCase())) return true;
        }
        return false;
    }
    public static boolean isFile(File file) {
        if (file.isFile() && file.canRead() && !file.isDirectory() && !file.isHidden()) { return true; }
        else { return false; }
    }
}