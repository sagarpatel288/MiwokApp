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

public class FamilyActivity extends AppCompatActivity implements Callbacks.OnChangeAudioFocusState {

    private MediaPlayer mediaPlayer;
    private MediaHelper mediaHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);
        mediaHelper = new MediaHelper(this, this);
        initWordList();
    }

    private void initWordList() {
        List<Word> words = new ArrayList<>();
        words.add(new Word("father", "әpә", R.raw.family_father, R.drawable.family_father));
        words.add(new Word("mother", "әṭa", R.raw.family_mother, R.drawable.family_mother));
        words.add(new Word("son", "angsi", R.raw.family_son, R.drawable.family_son));
        words.add(new Word("daughter", "tune", R.raw.family_daughter, R.drawable.family_daughter));
        words.add(new Word("older brother", "taachi", R.raw.family_older_brother, R.drawable.family_older_brother));
        words.add(new Word("younger brother", "chalitti", R.raw.family_younger_brother, R.drawable.family_younger_brother));
        words.add(new Word("older sister", "teṭe", R.raw.family_older_sister, R.drawable.family_older_sister));
        words.add(new Word("younger sister", "kolliti", R.raw.family_younger_sister, R.drawable.family_younger_sister));
        words.add(new Word("grandmother", "ama", R.raw.family_grandmother, R.drawable.family_grandmother));
        words.add(new Word("grandfather", "paapa", R.raw.family_grandfather, R.drawable.family_grandfather));
        showNumbers(words);
    }

    private void showNumbers(List<Word> words) {
        // Create an {@link ArrayAdapter}, whose data source is a list of Strings. The
        // adapter knows how to create layouts for each item in the list, using the
        // simple_list_item_1.xml layout resource defined in the Android framework.
        // This list item layout contains a single {@link TextView}, which the adapter will set to
        // display a single word.
        WordAdapter itemsAdapter = new WordAdapter(this, words, R.color.color_family);
        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // activity_numbers.xml layout file.
        ListView listView = findViewById(R.id.list);
        // Make the {@link ListView} use the {@link ArrayAdapter} we created above, so that the
        // {@link ListView} will display list items for each word in the list of words.
        // Do this by calling the setAdapter method on the {@link ListView} object and pass in
        // 1 argument, which is the {@link ArrayAdapter} with the variable name itemsAdapter.
        listView.setAdapter(itemsAdapter);
        setClickListner(listView);
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
                    mediaPlayer = MediaPlayer.create(FamilyActivity.this, word.getmRawAudioId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            Toast.makeText(FamilyActivity.this, "" + word.getmMiwokTranslation(), Toast.LENGTH_SHORT).show();
                            setImageDrawable(ivPlayPause, android.R.drawable.ic_media_play);
                            Utils.releaseMediaPlayer(mediaPlayer, mediaHelper);
                        }
                    });
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
    protected void onStop() {
        super.onStop();
        Utils.releaseMediaPlayer(mediaPlayer, mediaHelper);
        mediaPlayer = null;
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
