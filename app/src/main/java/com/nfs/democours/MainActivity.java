package com.nfs.democours;

import static com.nfs.democours.helper.TagLog.API_EX;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nfs.democours.beans.Pony;
import com.nfs.democours.beans.adapter.PonyListAdapter;
import com.nfs.democours.beans.adapter.QuoteListAdapter;
import com.nfs.democours.dao.DaoFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    FAIRE ATTENTION A LA CONSO DE LA BATTERIE : Resume, Pause, Stop puis Restart
 */
public class MainActivity extends AppCompatActivity {

    private static String tagLifeCycle = "Life Cycle";
    private static String quoteLog = "*** QUOTE ***";
    private static String poniesLog = "*** PONY ***";

    private List<Pony> ponies;

    private ListView poniesListView;

    private PonyListAdapter pla;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(tagLifeCycle, "onCreate");
        setContentView(R.layout.activity_main);

        VolleyLog.DEBUG = true;

        ponies = new ArrayList<>();
        initDb();

        callApiRest();

        poniesListView = (ListView) findViewById(R.id.mainQuoteList);
        pla = new PonyListAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, ponies);
        poniesListView.setAdapter(pla);
        poniesListView.setOnItemLongClickListener(this::onItemLongClick);

        final EditText quoteInput = (EditText) findViewById(R.id.mainQuoteInput);
        quoteInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                quoteInput.setHint("");
                return false;
            }
        });

    }

    private void initDb() {
        ponies = DaoFactory.getPonyDao(this).getAll();
        if (ponies == null || ponies.size() == 0) {
            for (String s : getResources().getStringArray(R.array.ex_pony)) {
                DaoFactory.getPonyDao(this).addPony(new Pony(0, s, "bleu", 0));
            }
            ponies = DaoFactory.getPonyDao(this).getAll();
        }
    }

    public void addPony(View view) {
        Pony p = new Pony(ponies.size(), ((EditText) findViewById(R.id.mainQuoteInput)).getText().toString(), ((EditText) findViewById(R.id.mainQuoteInput)).getText().toString(), 0);
        int i = DaoFactory.getPonyDao(this).addPony(p);
        p.setId(i);
        ponies.add(p);

        Log.d(poniesLog, "quotes " + ponies.toString());
        resfrehList(); // uniquement car pas API ni DB ni autre
    }

    /*public void clickMe(View view){
        Log.d("click", "click");
        Toast toast = Toast.makeText( this,"on m'a clické", Toast.LENGTH_LONG);
        toast.show();
        TextView tv = findViewById(R.id.TVMain);
        tv.setText("on a cliqué");
    }*/

    private void resfrehList() {
        pla = new PonyListAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, ponies);
        poniesListView.setAdapter(pla);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(tagLifeCycle, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(tagLifeCycle, "onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(tagLifeCycle, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(tagLifeCycle, "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(tagLifeCycle, "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(tagLifeCycle, "onRestart");
    }

    private boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Pony pony = (Pony) poniesListView.getItemAtPosition(position);
        Intent intent = new Intent(MainActivity.this, PonyActivity.class);
        intent.putExtra("pony", pony);
        startActivityForResult(intent, 1);
        return false;
    }

    private void callApiRest() {
        // make http get request https://ludivinecrepin.fr/api/pony-get.php
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                "https://ludivinecrepin.fr/api/pony-get.php",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null && response.length() > 0) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    Pony p = new Pony(jsonObject.getInt("id"), jsonObject.getString("name"), jsonObject.getString("color"), jsonObject.getInt("age"));
                                    DaoFactory.getPonyDao(getApplicationContext()).addPony(p);
                                    ponies.add(p);
                                    resfrehList();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", error.toString());
                    }
                });
        queue.add(jsonArrayRequest);
        // make http post request https://ludivinecrepin.fr/api/pony-add.php
        final String json = "{\n" +
                "    \"age\": 20,\n" +
                "    \"name\": \"Etalon du cul 20\",\n" +
                "    \"color\": \"arc en ciel\"\n" +
                "  }";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://ludivinecrepin.fr/api/pony-add.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", error.toString());
                    }
                }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return json.getBytes(StandardCharsets.UTF_8);
            }
        };
        queue.add(stringRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Log.d(poniesLog, "code retour " + resultCode);
            if (resultCode == RESULT_OK) {
                Pony p = (Pony) data.getExtras().get("pony");
                for (int i = 0; i < ponies.size(); i++) {
                    if (ponies.get(i).getId() == p.getId()) {
                        ponies.get(i).setName(p.getName());
                        ponies.get(i).setCouleur(p.getCouleur());
                        ponies.get(i).setAge(p.getAge());
                        break;
                    }
                }
                //resfrehList();
                pla.notifyDataSetChanged();
            } else if (resultCode == RESULT_FIRST_USER) {
                for (int i = 0; i < ponies.size(); i++) {
                    if (ponies.get(i).getId() == Integer.parseInt(data.getExtras().get("index").toString())) {
                        ponies.remove(i);
                        break;
                    }
                }
                resfrehList();
            }
        }
    }
}