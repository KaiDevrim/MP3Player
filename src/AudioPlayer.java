import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
        Media file = new Media(directory.toURI().toString());
        MediaPlayer player;
        player = new MediaPlayer(file);
        players.add(player);
    }

    public void playAudio()
    {
        players.get(quePos).play();
    }
    
    public void pauseAudio()
    {
        if (players.get(quePos).getStatus().equals(Status.PAUSED))
        {
            return;
        }
        players.get(quePos).pause();
    }

    public void resumeAudio()
    {
        if (players.get(quePos).getStatus().equals(Status.PLAYING))
        {
            return;
        }
        playAudio();
    }
    
    public void stopAudio()
    {
        players.get(quePos).stop();
    }

    public void removeAudio() {
        players.remove(quePos);
    }

    public void playQue(List<File> fileList)
    {
        System.out.println(fileList.get(0));
        final int[] i = {0};
        players.get(quePos).setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                if (i[0] != fileList.size()) {
                    i[0]++;
                    addAudio(fileList.get(i[0]));
                    playAudio();
                }
                return;
            }
        });
    }

    public void setVolume(double volume)
    {
        players.get(quePos).setVolume(volume);
    }
}
