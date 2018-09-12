package com.example.android.miwok.ui.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.miwok.R;
import com.example.android.miwok.adapters.WordAdapter;
import com.example.android.miwok.listeners.Callbacks;
import com.example.android.miwok.model.Word;
import com.example.android.miwok.utils.MediaHelper;
import com.example.android.miwok.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class NumbersActivity extends AppCompatActivity implements Callbacks.OnChangeAudioFocusState {

    public static final String TAG = NumbersActivity.class.getSimpleName();
    private ArrayList<String> words;
    private MediaPlayer mediaPlayer;
    private MediaHelper mediaHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);
        initNumbersArray();
        initNumberArrayList();
        mediaHelper = new MediaHelper(this, this);
        //showNumbersOnDynamicView(findViewById(R.id.rootView));
        initWordList();
    }

    private void initNumbersArray() {
        String[] words = new String[10];

        words[0] = "one";
        words[1] = "two";
        words[2] = "three";
        words[3] = "four";
        words[4] = "five";
        words[5] = "six";
        words[6] = "seven";
        words[7] = "eight";
        words[8] = "nine";
        words[9] = "ten";

        Log.v(TAG, "word at index 0: " + words[0]);
        Log.v(TAG, "word at index 1: " + words[1]);
        Log.v(TAG, "word at index 2: " + words[2]);
        Log.v(TAG, "word at index 3: " + words[3]);
        Log.v(TAG, "word at index 4: " + words[4]);
        Log.v(TAG, "word at index 5: " + words[5]);
        Log.v(TAG, "word at index 6: " + words[6]);
        Log.v(TAG, "word at index 7: " + words[7]);
        Log.v(TAG, "word at index 8: " + words[8]);
        Log.v(TAG, "word at index 9: " + words[9]);
    }

    private void initNumberArrayList() {
        words = new ArrayList<>();
        words.add("one");
        words.add("two");
        words.add("three");
        words.add("four");
        words.add("five");
        words.add("six");
        words.add("seven");
        words.add("eight");
        words.add("nine");
        words.add("ten");

        Log.v(TAG, "word at list index 0: " + words.get(0));
        Log.v(TAG, "word at list index 1: " + words.get(1));
        Log.v(TAG, "word at list index 2: " + words.get(2));
        Log.v(TAG, "word at list index 3: " + words.get(3));
        Log.v(TAG, "word at list index 4: " + words.get(4));
        Log.v(TAG, "word at list index 5: " + words.get(5));
        Log.v(TAG, "word at list index 6: " + words.get(6));
        Log.v(TAG, "word at list index 7: " + words.get(7));
        Log.v(TAG, "word at list index 8: " + words.get(8));
        Log.v(TAG, "word at list index 9: " + words.get(9));

//        showNumbers(words);
    }

    private void initWordList() {
        List<Word> words = new ArrayList<>();
        words.add(new Word("one", "lutti", R.raw.number_one, R.drawable.number_one));
        words.add(new Word("two", "otiiko", R.raw.number_two, R.drawable.number_two));
        words.add(new Word("three", "tolookosu", R.raw.number_three, R.drawable.number_three));
        words.add(new Word("four", "oyyisa", R.raw.number_four, R.drawable.number_four));
        words.add(new Word("five", "massokka", R.raw.number_five, R.drawable.number_five));
        words.add(new Word("six", "temmokka", R.raw.number_six, R.drawable.number_six));
        words.add(new Word("seven", "kenekaku", R.raw.number_seven, R.drawable.number_seven));
        words.add(new Word("eight", "kawinta", R.raw.number_eight, R.drawable.number_eight));
        words.add(new Word("nine", "wo'e", R.raw.number_nine, R.drawable.number_nine));
        words.add(new Word("ten", "na'aacha", R.raw.number_ten, R.drawable.number_ten));
        showNumbers(words);
    }

    private void showNumbers(List<Word> words) {
        // Create an {@link ArrayAdapter}, whose data source is a list of Strings. The
        // adapter knows how to create layouts for each item in the list, using the
        // simple_list_item_1.xml layout resource defined in the Android framework.
        // This list item layout contains a single {@link TextView}, which the adapter will set to
        // display a single word.
        WordAdapter itemsAdapter = new WordAdapter(this, words, R.color.color_numbers);
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
                    mediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getmRawAudioId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            Toast.makeText(NumbersActivity.this, "" + word.getmMiwokTranslation(), Toast.LENGTH_SHORT).show();
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

    private void showNumbersOnDynamicView(View view) {
        if (view instanceof LinearLayout) {
            showNumbers((LinearLayout) view);
        }
    }

    private void showNumbers(LinearLayout linearLayout) {
        for (int i = 0; i < words.size(); i++) {
            TextView textView = new TextView(this);
            textView.setText(words.get(i));
            linearLayout.addView(textView);
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
