package com.example.android.miwok.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.miwok.R;
import com.example.android.miwok.adapters.WordAdapter;
import com.example.android.miwok.model.Word;

import java.util.ArrayList;
import java.util.List;

public class NumbersActivity extends AppCompatActivity {

    public static final String TAG = NumbersActivity.class.getSimpleName();
    private ArrayList<String> words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);
        initNumbersArray();
        initNumberArrayList();
        //showNumbersOnDynamicView(findViewById(R.id.rootView));
        initWordList();
    }

    private void initWordList() {
        List<Word> words = new ArrayList<>();
        words.add(new Word("one", "lutti", R.drawable.number_one));
        words.add(new Word("two", "otiiko", R.drawable.number_two));
        words.add(new Word("three", "tolookosu", R.drawable.number_three));
        words.add(new Word("four", "oyyisa", R.drawable.number_four));
        words.add(new Word("five", "massokka", R.drawable.number_five));
        words.add(new Word("six", "temmokka", R.drawable.number_six));
        words.add(new Word("seven", "kenekaku", R.drawable.number_seven));
        words.add(new Word("eight", "kawinta", R.drawable.number_eight));
        words.add(new Word("nine", "wo'e", R.drawable.number_nine));
        words.add(new Word("ten", "na'aacha", R.drawable.number_ten));

        showNumbers(words);
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
        ListView listView = (ListView) findViewById(R.id.list);

        // Make the {@link ListView} use the {@link ArrayAdapter} we created above, so that the
        // {@link ListView} will display list items for each word in the list of words.
        // Do this by calling the setAdapter method on the {@link ListView} object and pass in
        // 1 argument, which is the {@link ArrayAdapter} with the variable name itemsAdapter.
        listView.setAdapter(itemsAdapter);
    }

    private void showNumbersOnDynamicView(View view) {
        if (view instanceof LinearLayout){
            showNumbers((LinearLayout) view);
        }
    }

    private void showNumbers(LinearLayout linearLayout) {
        for (int i = 0; i < words.size(); i++){
            TextView textView = new TextView(this);
            textView.setText(words.get(i));
            linearLayout.addView(textView);
        }
    }
}
