import java.io.*;
import javafx.scene.media.*;
import javafx.embed.swing.JFXPanel;
import java.util.*;
/**
 * plays audio
 * 
 * @author Harikishan
 * @version 0.1
 */
public class AudioPlayer
{
    private ArrayList<AudioClip> players;
    //The JFXPanel starts JavaFX, otherwise you get a "Toolkit not initialized" error
    public AudioPlayer()
    {
        final JFXPanel runtime = new JFXPanel();
        players = new ArrayList<AudioClip>();
    }
    
    public void addAudio(String directory)
    {
        Media file = new Media(new File(directory).toURI().toString());
        AudioClip player;
        player = new AudioClip(file.getSource());
        players.add(player);
    }
    
    public void play()
    {
        players.get(0).play();
    }
    
    public void stop()
    {
        players.get(0).stop();
    }
}