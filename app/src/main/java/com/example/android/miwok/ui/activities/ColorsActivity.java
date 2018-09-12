package com.example.android.miwok.ui.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.miwok.R;
import com.example.android.miwok.adapters.WordAdapter;
import com.example.android.miwok.listeners.Callbacks;
import com.example.android.miwok.model.Word;
import com.example.android.miwok.utils.MediaHelper;
import com.example.android.miwok.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ColorsActivity extends AppCompatActivity implements Callbacks.OnChangeAudioFocusState {

    private MediaHelper mediaHelper;
    private List<Word> wordList;
    private MediaPlayer mediaPlayer;
    public MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        public void setImage(ImageView imageView, int drawableResId) {
            setImageDrawable(imageView, drawableResId);
        }

        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            Utils.releaseMediaPlayer(mediaPlayer, mediaHelper);
            mediaPlayer = null;
        }


    };

    public List<Word> getwordList() {
        return wordList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);
        mediaHelper = new MediaHelper(this, this);
        initWordList();
    }

    private void initWordList() {
        List<Word> wordList = new ArrayList<>();
        wordList.add(new Word("red", "weṭeṭṭi", R.raw.color_red, R.drawable.color_red));
        wordList.add(new Word("green", "chokokki", R.raw.color_green, R.drawable.color_green));
        wordList.add(new Word("brown", "ṭakaakki", R.raw.color_brown, R.drawable.color_brown));
        wordList.add(new Word("gray", "ṭopoppi", R.raw.color_gray, R.drawable.color_gray));
        wordList.add(new Word("black", "kululli", R.raw.color_black, R.drawable.color_black));
        wordList.add(new Word("white", "kelelli", R.raw.color_white, R.drawable.color_white));
        wordList.add(new Word("dusty yellow", "ṭopiisә", R.raw.color_dusty_yellow, R.drawable.color_dusty_yellow));
        wordList.add(new Word("mustard yellow", "chiwiiṭә", R.raw.color_mustard_yellow, R.drawable.color_mustard_yellow));
        showNumbers(wordList);
    }

    private void showNumbers(List<Word> wordList) {
        setwordList(wordList);
        // Create an {@link ArrayAdapter}, whose data source is a list of Strings. The
        // adapter knows how to create layouts for each item in the list, using the
        // simple_list_item_1.xml layout resource defined in the Android framework.
        // This list item layout contains a single {@link TextView}, which the adapter will set to
        // display a single word.
        WordAdapter itemsAdapter = new WordAdapter(this, wordList, R.color.color_colors);
        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // activity_numbers.xml layout file.
        ListView listView = findViewById(R.id.list);
        setClickListner(listView);
        // Make the {@link ListView} use the {@link ArrayAdapter} we created above, so that the
        // {@link ListView} will display list items for each word in the list of wordList.
        // Do this by calling the setAdapter method on the {@link ListView} object and pass in
        // 1 argument, which is the {@link ArrayAdapter} with the variable name itemsAdapter.
        listView.setAdapter(itemsAdapter);
    }

    public void setwordList(List<Word> wordList) {
        this.wordList = wordList;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Utils.releaseMediaPlayer(mediaPlayer, mediaHelper);
        mediaPlayer = null;
    }

    private void setClickListner(final ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final ImageView ivPlayPause = view.findViewById(R.id.iv_play_pause);
                setImageDrawable(ivPlayPause, android.R.drawable.ic_media_pause);
                final Word word = (Word) listView.getItemAtPosition(i);
                if (word.getmRawAudioId() != 0) {
                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
//                        setImageDrawable(ivPlayPause, android.R.drawable.ic_media_play);
                        mediaPlayer.release();
                        mediaPlayer = null;
                        mediaHelper.abandonAudioFocus();
                    }

//                    if (mediaHelper.getAudioFocus() == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(ColorsActivity.this, word.getmRawAudioId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            Toast.makeText(ColorsActivity.this, "" + word.getmMiwokTranslation(), Toast.LENGTH_SHORT).show();
                            setImageDrawable(ivPlayPause, android.R.drawable.ic_media_play);
                        }
                    });
//                    }
                }
            }
        });
    }

    public void setImageDrawable(ImageView imageView, int drawableResId) {
        if (imageView != null) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this, drawableResId));
        }
    }

    @Override
    public void onTransientLoss() {
        Toast.makeText(this, "on transient loss", Toast.LENGTH_SHORT).show();
        Utils.pauseMediaPlayer(mediaPlayer);
    }

    @Override
    public void onLoss() {
        Toast.makeText(this, "on loss", Toast.LENGTH_SHORT).show();
        Utils.stopAndReleaseMediaPlayer(mediaPlayer, mediaHelper);
    }

    @Override
    public void onTransientLossCanDuck() {
        Toast.makeText(this, "on transient loss can duck", Toast.LENGTH_SHORT).show();
        Utils.pauseMediaPlayer(mediaPlayer);
    }

    @Override
    public void onGain() {
        Toast.makeText(this, "on gain", Toast.LENGTH_SHORT).show();
        Utils.resumeMediaPlayer(mediaPlayer);
    }
}
