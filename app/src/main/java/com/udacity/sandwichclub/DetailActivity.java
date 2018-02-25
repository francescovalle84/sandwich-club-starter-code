package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.w3c.dom.Text;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = this.getSupportActionBar();

        // Set the action bar back button to look like an up button
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // When the home button is pressed, take the user back to the VisualizerActivity
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        final String separator = ", ";

        // AlsoKnownAs
        TextView alsoKnownAsTv = findViewById(R.id.also_known_tv);
        alsoKnownAsTv.setText(getStringFromList(sandwich.getAlsoKnownAs(), separator));

        // PlaceOfOrigin
        TextView placeOfOriginTv = findViewById(R.id.origin_tv);
        placeOfOriginTv.setText(getStringFromString(sandwich.getPlaceOfOrigin()));

        // Description
        TextView descriptionTv = findViewById(R.id.description_tv);
        descriptionTv.setText(getStringFromString(sandwich.getDescription()));

        // Ingredients
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        ingredientsTv.setText(getStringFromList(sandwich.getIngredients(), separator));

    }

    /*
     * Returns N/A if data is not available
     */
    private String getStringFromString(String src) {
        String resultString = src;

        if(src.equals("")) {
            resultString = getResources().getString(R.string.data_not_available);
        }
        return resultString;
    }

    /*
     * Returns a String with the list of values
     */
    private String getStringFromList(List<String> src, String separator) {
        String resultString;
        boolean atLeastOne = false;

        StringBuilder builder = new StringBuilder();
        for(String item : src) {
            atLeastOne = true;
            builder.append(item);
            builder.append(separator);
        }

        if(atLeastOne) {
            resultString = builder.toString();
            resultString = resultString.substring(0, resultString.length() - separator.length());
        }
        else {
            resultString = getResources().getString(R.string.data_not_available);
        }

        return resultString;
    }
}
