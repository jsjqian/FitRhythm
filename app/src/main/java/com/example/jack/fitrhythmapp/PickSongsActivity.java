package com.example.jack.fitrhythmapp;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class PickSongsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_songs);
        ArrayList<String> playlist = new ArrayList<String>();
        TableLayout table = (TableLayout)findViewById(R.id.playlist_table);
        for (String s : playlist) {
            TableRow row = new TableRow(this);
            TextView text = new TextView(this);
            Button button = new Button(this);
            text.setText(s);
            button.setText("+");
            row.addView(text);
            row.addView(button);
            table.addView(row);

        }
    }

    public ArrayList<String> getPlayList() {

        ArrayList<String> arrayList = new ArrayList<String>();

        String[] proj = {"*"};
        Uri tempPlaylistURI = MediaStore.Audio.Playlists.INTERNAL_CONTENT_URI;

        // In the next line 'this' points to current Activity.
        // If you want to use the same code in other java file then activity,
        // then use an instance of any activity in place of 'this'.

        Cursor playListCursor= getContentResolver().query(tempPlaylistURI, proj, null,null,null);

        if(playListCursor == null){
            System.out.println("Not having any Playlist on phone --------------");
            return arrayList;//don't have list on phone
        }

        System.gc();

        String playListName = null;

        System.out.println(">>>>>>>  CREATING AND DISPLAYING LIST OF ALL CREATED PLAYLIST  <<<<<<");

        for(int i = 0; i <playListCursor.getCount() ; i++)
        {
            playListCursor.moveToPosition(i);
            playListName = playListCursor.getString(playListCursor.getColumnIndex("name"));
            System.out.println("> " + i + "  : " + playListName );
            arrayList.add(playListName);
        }

        if(playListCursor != null)
            playListCursor.close();

        return arrayList;

    }

}
