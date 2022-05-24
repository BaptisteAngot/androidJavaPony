package com.nfs.democours;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import com.nfs.democours.beans.Pony;
import com.nfs.democours.beans.Quote;
import com.nfs.democours.dao.DaoFactory;

public class PonyActivity extends AppCompatActivity {

    private Pony pony;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);
        pony = (Pony) getIntent().getExtras().get("name");
        ((EditText)findViewById(R.id.pony_string_input)).setText(pony.getName());
        ((RatingBar) findViewById(R.id.pony_rating_bar)).setRating(pony.getAge());
    }

    public void updateQuote(View view){
        pony.setName( ((EditText)findViewById(R.id.pony_string_input)).getText().toString());
        pony.setAge( ((RatingBar) findViewById(R.id.pony_rating_bar)).getNumStars());
        getIntent().putExtra("pony", pony);
        DaoFactory.getPonyDao(this).update(pony);
        setResult(RESULT_OK, getIntent());
        finish();
    }
    public void cancel(View view){
        setResult(RESULT_CANCELED, getIntent());
        finish();
    }
    public void delete(View view){
        getIntent().putExtra("index", pony.getId());
        setResult(RESULT_FIRST_USER, getIntent());
        finish();
    }
}