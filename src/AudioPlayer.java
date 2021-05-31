import java.io.*;
import javafx.scene.media.*;
import javafx.embed.swing.JFXPanel;
import java.util.*;
/**
 * plays audio
 * 
 * @author Harikishan
 * @version 0.2
 */
public class AudioPlayer
{
    private int quePos;
    private ArrayList<AudioClip> players;
    private ArrayList<AudioClip> singlePlayer;
    //The JFXPanel starts JavaFX, otherwise you get a "Toolkit not initialized" error
    public AudioPlayer()
    {
        players = new ArrayList<AudioClip>();
        singlePlayer = new ArrayList<AudioClip>();
        players.add(null);
        singlePlayer.add(null);
    }
    
    public void addAudio(String directory)
    {
        Media file = new Media(new File(directory).toURI().toString());
        AudioClip player;
        player = new AudioClip(file.getSource());
        players.add(player);
    }

    public void playAudio(String directory)
    {
        Media file = new Media(new File(directory).toURI().toString());
        AudioClip clip = new AudioClip(file.getSource());
        clip.play();
        singlePlayer.set(0, clip);
    }
    
    public void playQue()
    {
        for (int i = 0; i < players.size(); i++)
        {
            quePos = i;
            players.get(i).play();
            while (players.get(i).isPlaying())
            {
            }
        }
    }
    
    public void stopAudio()
    {
        if (singlePlayer.get(0).isPlaying() == true)
        {
            singlePlayer.get(0).stop();
        }

        if (players.get(quePos).isPlaying() == true)
        {
            players.get(quePos).stop();
        }
    }

}
