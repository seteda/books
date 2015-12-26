package com.badlogic.androidgames.gamedev2d;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GameDev2DStarter extends ListActivity {
    String tests[] = { "CannonTest", "CannonGravityTest", "CollisionTest",
                       "Camera2DTest", "TextureAtlasTest", "SpriteBatcherTest",
                       "AnimationTest"};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, tests));
    }

    @Override
    protected void onListItemClick(ListView list, View view, int position,
            long id) {
        super.onListItemClick(list, view, position, id);
        String testName = tests[position];
        try {
            Class clazz = Class.forName("com.badlogic.androidgames.gamedev2d."
                    + testName);
            Intent intent = new Intent(this, clazz);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
