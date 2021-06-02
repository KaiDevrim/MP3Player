import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;

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
    private ArrayList<AudioClip> players;
    //The JFXPanel starts JavaFX, otherwise you get a "Toolkit not initialized" error
    public AudioPlayer()
    {
        players = new ArrayList<AudioClip>();
        players.add(null);
    }
    
    public void addAudio(File directory)
    {
        System.out.println(directory + " add");
        Media file = new Media(directory.toURI().toString());
        AudioClip player;
        player = new AudioClip(file.getSource());
        players.add(player);
    }

    public void playAudio(String directory)
    {
        Media file = new Media(new File(directory).toURI().toString());
        AudioClip clip = new AudioClip(file.getSource());
        clip.play();
        players.add(clip);
    }
    
    public void resumeAudio()
    {
        players.get(quePos).play();
    }
    
    public void stopAudio()
    {
        for (int i = 0; i < players.size() - 1; i++)
        {
            if (players.get(i).isPlaying() == true)
            {
                players.get(i).stop();
                quePos = i;
            }
        }
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
}
