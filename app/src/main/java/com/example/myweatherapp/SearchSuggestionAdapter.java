package com.example.myweatherapp;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

////TODO what is a CursorAdapter
/*
When the user enters text into the SearchView, the adapter queries its
data source (ArrayAdapter<String>) to find suggestions that match the input.
It then creates a cursor containing these suggestions and updates the SearchView to display them.
 */
public class SearchSuggestionAdapter extends CursorAdapter {
    ////TODO why is it final?
    /*
    To ensure the arrayAdapter reference cant be changed.
     */
    private final ArrayAdapter<String> arrayAdapter;

    public SearchSuggestionAdapter(ArrayAdapter<String> adapter) {
        super(adapter.getContext(), null, 0);
        this.arrayAdapter = adapter;
    }

    @Override
    public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
        // Create a cursor with suggestions matching the user input
        ////TODO what is a MatrixCursor
        /*

    A MatrixCursor is a type of cursor used to hold tabular data in memory.
    It allows you to create and manipulate rows of data similar to a database cursor, but without accessing an actual database.
    it is used to generate suggestions for the search view based on user input.
    It holds suggestions matching the user input and is used to create a list of suggestions displayed to the user as they type in the search view.
         */
        MatrixCursor cursor = new MatrixCursor(new String[]{"_id", "suggestion"});

        if (constraint != null && constraint.length() > 0) {
            for (int i = 0; i < arrayAdapter.getCount(); i++) {
                String suggestion = arrayAdapter.getItem(i);
                if (suggestion.toLowerCase().contains(constraint.toString().toLowerCase())) {
                    ////TODO what do you understand by new Object[]{i, suggestion}
                    /*
                     new Object[]{i, suggestion} creates an array of objects containing the index i and the suggestion string suggestion.
                      This array is passed as a parameter to the addRow method of the MatrixCursor, which adds a new row to the cursor with the specified data.

                     */
                    cursor.addRow(new Object[]{i, suggestion});
                }
            }
        }

        return cursor;
    }

    //// Constants for padding
    private static final int PADDING_LEFT = 16;
    private static final int PADDING_TOP = 16;
    private static final int PADDING_RIGHT = 16;
    private static final int PADDING_BOTTOM = 16;

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        TextView view = new TextView(context);
        ////TODO no magic literals //fixed
        view.setPadding(PADDING_LEFT, PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textView = (TextView) view;
        ////TODO as this is throwing an error should have been handled in the try catch block //done
        try {
            // Attempt to set the text from the cursor column
            textView.setText(cursor.getString(cursor.getColumnIndexOrThrow("suggestion")));
        } catch (IllegalArgumentException e) {
            // Log the error or handle it appropriately
            textView.setText("No suggestion");
        }
    }
}
