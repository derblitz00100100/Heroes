package com.example.heroes;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.widget.Toast.makeText;

public class HeroesListActivity extends AppCompatActivity {

    public static final String TAG = HeroesListActivity.class.getSimpleName();
    private ListView heroListView;
    private List<Hero> heroList;
    private HeroAdapter heroAdapter;

    public static final String EXTRA_HERO = "hero selected";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        InputStream JsonFileInputStream = getResources().openRawResource(R.raw.heroes);
        String jsonString = readTextFile(JsonFileInputStream);
        Gson gson = new Gson();
        Hero[] heroes = gson.fromJson(jsonString, Hero[].class);
        heroList = Arrays.asList(heroes);
        Log.d(TAG, "onCreate: " + heroList.toString());

        heroAdapter = new HeroAdapter(heroList);
        heroListView = findViewById(R.id.listview_main_herolistview);
        heroListView.setAdapter(heroAdapter);

        heroListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent targetIntent = new Intent(HeroesListActivity.this, HeroDetailActivity.class);
                targetIntent.putExtra(EXTRA_HERO, heroList.get(position));
                startActivity(targetIntent);
                finish();
            }
        });
    }

    public String readTextFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }

    private class HeroAdapter extends ArrayAdapter<Hero> {
        // make an instance variable to keep track of the hero list
        private List<Hero> heroesList;

        public HeroAdapter(List<Hero> heroesList) {
            //since we're in the HeroListActivity class we already have the context
            // we're hard coding in a particular layout, so we don't need to put it in
            // the constructor either
            // we'll send a placeholder resource to the superclass of -1
            super(HeroesListActivity.this, -1, heroesList);
            this.heroesList = heroList;

        }

        // the goal of the adapter is to link your list to the listView
        // and tell the listview where each aspect of the list goes.
        // so we override a method called getView.


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_hero, parent, false);
            }

            TextView textViewName = convertView.findViewById(R.id.textView_heroitem_name);
            TextView textViewRank = convertView.findViewById(R.id.textView_heroitem_rank);
            TextView textViewDescription = convertView.findViewById(R.id.textView_heroitem_description);

            textViewName.setText(heroesList.get(position).getName());
            textViewDescription.setText(heroesList.get(position).getDescription());
            textViewRank.setText(String.valueOf(heroesList.get(position).getRanking()));

            return convertView;
        }
    }

    // this is for the

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_heroeslist_sorting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_heroeslist_sort_by_name:
                sortByName();
                return true;
            case R.id.action_heroeslist_sort_by_rank:
                sortByRank();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sortByRank() {
        //extract list from the adapter
        //Collections.sort(heroadapter.heroeslist)
        Collections.sort(heroList, new Comparator<Hero>() {
            @Override
            public int compare(Hero hero1, Hero hero2) {
                return hero1.getRanking() - hero2.getRanking();
            }
        });
        heroAdapter.notifyDataSetChanged();
        Toast.makeText(this , "Sort by rank clicked", Toast.LENGTH_SHORT).show();
    }

    private void sortByName() {
        Collections.sort(heroList, new Comparator<Hero>() {
            @Override
            public int compare(Hero hero1, Hero hero2) {
                return hero1.getName().compareTo(hero2.getName());
            }
        });
        heroAdapter.notifyDataSetChanged();
        Toast.makeText(this, "sort by name clicked", Toast.LENGTH_SHORT).show();
    }
}
