import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

import java.io.File;
import java.util.ArrayList;
/**
 * plays audio
 * 
 * @author Harikishan
 * @version 0.2
 */
public class AudioPlayer
{
    private int quePos;
    private ArrayList<MediaPlayer> players;
    //The JFXPanel starts JavaFX, otherwise you get a "Toolkit not initialized" error
    public AudioPlayer()
    {
        players = new ArrayList<MediaPlayer>();
        quePos = 0;
    }
    //Adds a MediaPlayer to the ArrayList, players
    public void addAudio(File directory)
    {
        System.out.println(directory + " add");
        Media file = new Media(directory.toURI().toString());
        MediaPlayer player;
        player = new MediaPlayer(file);
        players.add(player);
    }

    public void playAudio()
    {
        players.get(quePos).play();
        System.out.println(players.get(quePos).getMedia().getDuration());
    }
    
    public void pauseAudio()
    {
        if (players.get(quePos).getStatus().equals(Status.PAUSED))
        {
            System.out.println("Audio is paused");
            return;
        }
        players.get(quePos).pause();
    }

    public void resumeAudio()
    {
        if (players.get(quePos).getStatus().equals(Status.PLAYING))
        {
            System.out.println("Audio is playing");
            return;
        }
        playAudio();
    }
    
    public void stopAudio()
    {
        players.get(quePos).stop();
    }

    public void playQue()
    {
        for (int i = 0; i < players.size(); i++)
        {
            quePos = i;
            players.get(i).play();
            //wait(players.get(quePos).getCycleDuration());
        }
    }

    public void setVolume(double volume)
    {
        players.get(quePos).setVolume(volume);
    }
}
