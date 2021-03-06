package com.example.heroes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HeroDetailActivity extends AppCompatActivity {

    private TextView heroName;
    private TextView description;
    private TextView superpower;
    private TextView ranking;
    private ImageView heroProfile;
    private Button goBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_detail);
        wireWidgets();
        setListeners();
        Intent lastIntent = getIntent();
        Hero heroSelected = lastIntent.getParcelableExtra(HeroesListActivity.EXTRA_HERO);

        heroName.setText(heroSelected.getName());
        description.setText(heroSelected.getDescription());
        superpower.setText(heroSelected.getSuperpower());
        ranking.setText(heroSelected.getRanking() + "");
        int resourceImage = getResources().getIdentifier(heroSelected.getImage(), "drawable", getPackageName());
        heroProfile.setImageDrawable(getResources().getDrawable(resourceImage));

    }

    private void setListeners() {
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent targetIntent = new Intent(HeroDetailActivity.this, HeroesListActivity.class);
                startActivity(targetIntent);
                finish();
            }
        });
    }

    private void wireWidgets() {
        heroName = findViewById(R.id.textView_heroDetail_name);
        description = findViewById(R.id.textView_heroDetail_description);
        superpower = findViewById(R.id.textView_heroDetail_superpower);
        ranking = findViewById(R.id.textView_heroDetail_ranking);
        heroProfile = findViewById(R.id.imageView_herodetail_profile);
        goBackButton = findViewById(R.id.button_heroDetail_goback);
    }
}
