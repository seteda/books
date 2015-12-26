import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity
{
    static final String AUDIO_PATH =
"http://www.androidbook.com/akc/filestorage/android/documentfiles/3389/play.mp3";

    private MediaPlayer mediaPlayer;
    private int playbackPosition=0;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button startPlayerBtn = (Button)findViewById(R.id.startPlayerBtn);
        Button pausePlayerBtn = (Button)findViewById(R.id.pausePlayerBtn);
        Button restartPlayerBtn = (Button)findViewById(R.id.restartPlayerBtn);

        startPlayerBtn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view)
            {
                try {
                    playAudio(AUDIO_PATH);
//                    playLocalAudio();
//                    playLocalAudio_UsingDescriptor();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }});

        pausePlayerBtn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view)
            {
                if(mediaPlayer!=null)
                {
                    playbackPosition = mediaPlayer.getCurrentPosition();
                    mediaPlayer.pause();
                }
            }});

        restartPlayerBtn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view)
            {
                if(mediaPlayer!=null && !mediaPlayer.isPlaying())
                {
                    mediaPlayer.seekTo(playbackPosition);
                    mediaPlayer.start();
                }
            }});
    }

    private void playAudio(String url)throws Exception
    {
        killMediaPlayer();

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(url);
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        killMediaPlayer();
    }
    private void killMediaPlayer()
    {
        if(mediaPlayer!=null)
        {
            try
            {
                mediaPlayer.release();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}

