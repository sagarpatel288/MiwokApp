package com.example.android.miwok.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.miwok.R;
import com.example.android.miwok.model.Word;

import java.util.List;

/*
 * {@link WordAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
 * based on a data source, which is a list of {@link Word} objects.
 * */
public class WordAdapter extends ArrayAdapter<Word> {

    private int bgColor;

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param objects A List of Word objects to display in a list
     * @param bgColorResId Background color resource id (R.color.color_xyz) for vertical text views
     */
    public WordAdapter(@NonNull Context context, @NonNull List<Word> objects, int bgColorResId) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, objects);
        this.bgColor = ContextCompat.getColor(context, bgColorResId);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view 
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_list, parent, false);
        }

        // Get the {@link Word} object located at this position in the list 
        Word currentWord = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name 
        TextView miwokTextView = listItemView.findViewById(R.id.miwok_text_view);
        // Get the version name from the current Word object and 
        // set this text on the name TextView 
        miwokTextView.setText(currentWord.getmMiwokTranslation());

        // Find the TextView in the list_item.xml layout with the ID version_number 
        TextView defaultTextView = listItemView.findViewById(R.id.default_text_view);
        // Get the version number from the current Word object and 
        // set this text on the number TextView 
        defaultTextView.setText(currentWord.getmDefaultTranslation());

        // Find the ImageView in the list_item.xml layout with the ID list_item_icon 
        ImageView iconView = listItemView.findViewById(R.id.image);
        // Get the image resource ID from the current Word object and 
        // set the image to iconView 
        if (currentWord.getmImageResourceId() != 0) {
            iconView.setVisibility(View.VISIBLE);
            iconView.setImageResource(currentWord.getmImageResourceId());
        } else {
            iconView.setVisibility(View.GONE);
        }

        //Find linear layout that contains those vertical text view labels
        LinearLayout linearLayout = listItemView.findViewById(R.id.ll_vertical_texts);
        LinearLayout layoutImg = listItemView.findViewById(R.id.layout_img);
        //and set the background color
        linearLayout.setBackgroundColor(bgColor);
        layoutImg.setBackgroundColor(bgColor);

        // Return the whole list item layout (containing 2 TextViews and an ImageView) 
        // so that it can be shown in the ListView 
        return listItemView;

    }
}
