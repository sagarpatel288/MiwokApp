package com.example.android.miwok.ui.fragments;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.miwok.R;
import com.example.android.miwok.adapters.WordAdapter;
import com.example.android.miwok.listeners.Callbacks;
import com.example.android.miwok.model.Word;
import com.example.android.miwok.utils.MediaHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FamilyFragment extends Fragment implements Callbacks.OnChangeAudioFocusState {

    private MediaPlayer mediaPlayer;
    private MediaHelper mediaHelper;
    public MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        public void setImage(ImageView imageView, int drawableResId) {
            setImageDrawable(imageView, drawableResId);
        }

        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            MediaHelper.releaseMediaPlayer(mediaPlayer, mediaHelper);
            setMediaPlayerNull();
        }
    };

    public FamilyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_family, container, false);
        mediaHelper = new MediaHelper(getActivity(), this);
        initWordList(rootView);
        return rootView;
    }

    private void initWordList(View rootView) {
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
        showNumbers(rootView, words);
    }

    private void showNumbers(View rootView, List<Word> words) {
        // Create an {@link ArrayAdapter}, whose data source is a list of Strings. The
        // adapter knows how to create layouts for each item in the list, using the
        // simple_list_item_1.xml layout resource defined in the Android framework.
        // This list item layout contains a single {@link TextView}, which the adapter will set to
        // display a single word.
        WordAdapter itemsAdapter = new WordAdapter(getActivity(), words, R.color.color_family);
        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // activity_numbers.xml layout file.
        ListView listView = rootView.findViewById(R.id.list);
        setClickListner(listView);
        // Make the {@link ListView} use the {@link ArrayAdapter} we created above, so that the
        // {@link ListView} will display list items for each word in the list of words.
        // Do this by calling the setAdapter method on the {@link ListView} object and pass in
        // 1 argument, which is the {@link ArrayAdapter} with the variable name itemsAdapter.
        listView.setAdapter(itemsAdapter);
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
                    mediaPlayer = MediaPlayer.create(getActivity(), word.getmRawAudioId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            Toast.makeText(getActivity(), "" + word.getmMiwokTranslation(), Toast.LENGTH_SHORT).show();
                            setImageDrawable(ivPlayPause, android.R.drawable.ic_media_play);
                            MediaHelper.releaseMediaPlayer(mediaPlayer, mediaHelper);
                            setMediaPlayerNull();
                        }
                    });
                }
            }
        });
    }

    public void setImageDrawable(ImageView imageView, int drawableResId) {
        if (imageView != null) {
            imageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), drawableResId));
        }
    }

    public void setMediaPlayerNull() {
        if (mediaPlayer != null) {
            mediaPlayer = null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        MediaHelper.releaseMediaPlayer(mediaPlayer, mediaHelper);
        mediaPlayer = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        MediaHelper.releaseMediaPlayer(mediaPlayer, mediaHelper);
        mediaPlayer = null;
    }

    @Override
    public void onTransientLoss() {
        Toast.makeText(getActivity(), "on transient loss", Toast.LENGTH_SHORT).show();
        MediaHelper.pauseMediaPlayer(mediaPlayer);
    }

    @Override
    public void onLoss() {
        Toast.makeText(getActivity(), "on loss", Toast.LENGTH_SHORT).show();
        MediaHelper.stopAndReleaseMediaPlayer(mediaPlayer, mediaHelper);
        setMediaPlayerNull();
    }

    @Override
    public void onTransientLossCanDuck() {
        Toast.makeText(getActivity(), "on transient loss can duck", Toast.LENGTH_SHORT).show();
        MediaHelper.pauseMediaPlayer(mediaPlayer);
    }

    @Override
    public void onGain() {
        Toast.makeText(getActivity(), "on gain", Toast.LENGTH_SHORT).show();
        MediaHelper.resumeMediaPlayer(mediaPlayer);
    }
}
