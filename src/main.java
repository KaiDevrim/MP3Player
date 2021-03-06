import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
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
        StackPane root = new StackPane();

        // getAudioLength(media);

        Button mainBtn = new Button();
        Button playlistBtn = new Button();
        playlistBtn.setTranslateY(50);

//        Button clearSongsBtn = new Button();
//        Button loopSongsBtn = new Button();
        Button fileBtn = new Button();
        fileBtn.setTranslateY(100);

        root.getChildren().add(mainButton(mainBtn));
        root.getChildren().add(playlistButton(playlistBtn, primaryStage));
        root.getChildren().add(fileButton(fileBtn, primaryStage));

        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();

    }

    public void pause(Button btn) {
        System.out.println("Pause");
        player.pauseAudio();
        btn.setText("▶︎");
    }
    public void play(Button btn) {
        System.out.println("Play");
        player.resumeAudio();
        btn.setText("❙❙");
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
    public Button mainButton(Button btn) {
        btn.setText("❙❙");
        btn.setFont(Font.font("verdana",20));
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (btn.getText().equals("❙❙")) {
                    pause(btn);
                }
                else if (btn.getText().equals("▶︎")) {
                    play(btn);
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
    public Button playlistButton(Button btn, Stage primaryStage) {
        btn.setText("Select a Playlist");
        btn.setFont(Font.font("verdana",20));
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                folderChooser(primaryStage);
            }
        });
        return btn;
    }
    public File folderChooser(Stage primaryStage) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Choose a folder for your playlist");
        File defaultDirectory = new File(System.getProperty("user.home"));
        chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showDialog(primaryStage);
        if (selectedDirectory != null) {
            playlist(selectedDirectory, primaryStage);
            return selectedDirectory;
        }
        return null;
    }
    public Button fileButton(Button btn, Stage primaryStage) {
        btn.setText("Select a File");
        btn.setFont(Font.font("verdana",20));
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fileChooser(primaryStage);
            }
        });
        return btn;
    }
    public File fileChooser(Stage primaryStage) {
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("SONG files (*.m4a, *.wav, *.mp3)", "*.m4a", "*.wav", "*.mp3");
        chooser.getExtensionFilters().add(extFilter);
        chooser.setTitle("Choose a folder for your playlist");
        File defaultDirectory = new File(System.getProperty("user.home"));
        chooser.setInitialDirectory(defaultDirectory);
        File selectedFile = chooser.showOpenDialog(primaryStage);
        // addMusicFiles(selectedFile, primaryStage);
        if (selectedFile != null) {
            player.addAudio(selectedFile);
            player.playAudio();
            return selectedFile;
        }
        return null;
    }
    public void playlist(File directory, Stage primaryStage) {
        ObservableList<String> validFiles = FXCollections.observableArrayList();
        ListView<String> list = new ListView<>();
        for (File file : getPlaylist(new File(directory.getAbsolutePath()))) {
            validFiles.add(file.getAbsolutePath());
            System.out.println(file);
            player.addAudio(file);
        }
        Button playlistPlayButton = new Button();
        playlistPlayButton.setFont(Font.font("verdana",20));
        playlistPlayButton.setText("▶︎");
        playlistPlayButton.setTranslateY(20);
        playlistPlayButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println(directory);
                System.out.println(getPlaylist(directory));
                player.playQue(getPlaylist(directory));
                player.playAudio();
            }
        });
        StackPane root = new StackPane();
        Stage stage = new Stage();
        root.getChildren().add(list);
        root.getChildren().add(playlistPlayButton);
        stage.setTitle("Playlist");
        stage.setScene(new Scene(root, 200, 250));
        String css = main.class.getResource("myStyle.css").toExternalForm();
        root.getStylesheets().add(css);
        stage.show();
        list.setItems(validFiles);
        list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                player.stopAudio();
                player.removeAudio();
                player.addAudio(new File(newValue));
                player.playAudio();
                System.out.println("Selected item: " + newValue);
            }
        });
        player.playQue(getPlaylist(directory));
    }
}