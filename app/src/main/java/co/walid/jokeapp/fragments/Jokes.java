package co.walid.jokeapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.walid.jokeapp.Adapters.JokeAdapter;
import co.walid.jokeapp.model.Joke;
import co.walid.jokeapp.R;

public class Jokes extends Fragment {
    public static final String TAG = "TAG";
    String jokesUrl;
    RecyclerView jokesList;
    JokeAdapter adapter;
    List<Joke> jokes;

    public Jokes(String Url) {
        this.jokesUrl = Url;
        jokes = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_jokes, container, false);
        jokesList = v.findViewById(R.id.jokesList);
        adapter = new JokeAdapter(requireContext(), jokes);
        jokesList.setLayoutManager(new LinearLayoutManager(requireContext()));
        jokesList.setAdapter(adapter);
        getJokes(jokesUrl);
        return v;
    }

    public void getJokes(String url) {
        Log.d(TAG, "getJokes: " + url);
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jokesArray = response.getJSONArray("jokes");
                    List<Joke> newJokes = new ArrayList<>();
                    for (int i = 0; i < jokesArray.length(); i++) {
                        JSONObject jokeData = jokesArray.getJSONObject(i);
                        Joke j = new Joke();
                        j.setType(jokeData.getString("type"));
                        if (jokeData.getString("type").equals("single")) {
                            j.setFirstLine(jokeData.getString("joke"));
                        } else {
                            j.setFirstLine(jokeData.getString("setup"));
                            j.setSecondLine(jokeData.getString("delivery"));
                        }
                        newJokes.add(j);
                    }
                    jokes.clear();
                    jokes.addAll(newJokes);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.getLocalizedMessage());
            }
        });

        queue.add(objectRequest);
    }
}
