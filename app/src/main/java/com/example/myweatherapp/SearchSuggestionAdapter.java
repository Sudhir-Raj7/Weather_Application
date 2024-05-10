package com.example.myweatherapp;

import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

////TODO what is a CursorAdapter
public class SearchSuggestionAdapter extends CursorAdapter {
    ////TODO why is it final?
    private final ArrayAdapter<String> arrayAdapter;

    public SearchSuggestionAdapter(ArrayAdapter<String> adapter) {
        super(adapter.getContext(), null, 0);
        this.arrayAdapter = adapter;
    }

    @Override
    public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
        // Create a cursor with suggestions matching the user input
        ////TODO what is a MatrixCursor
        MatrixCursor cursor = new MatrixCursor(new String[]{"_id", "suggestion"});

        if (constraint != null && constraint.length() > 0) {
            for (int i = 0; i < arrayAdapter.getCount(); i++) {
                String suggestion = arrayAdapter.getItem(i);
                if (suggestion.toLowerCase().contains(constraint.toString().toLowerCase())) {
                    ////TODO what do you understand by new Object[]{i, suggestion}
                    cursor.addRow(new Object[]{i, suggestion});
                }
            }
        }

        return cursor;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        TextView view = new TextView(context);
        ////TODO no magic literals
        view.setPadding(16, 16, 16, 16);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textView = (TextView) view;
        ////TODO as this is throwing an error should have been handled in the try catch block
        textView.setText(cursor.getString(cursor.getColumnIndexOrThrow("suggestion")));
    }
}
