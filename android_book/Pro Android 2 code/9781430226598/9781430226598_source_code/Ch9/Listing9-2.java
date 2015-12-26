    private void playLocalAudio()throws Exception
    {
        mediaPlayer = MediaPlayer.create(this, R.raw.music_file);
        mediaPlayer.start();
    }

