package com.example.jan455app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    public SearchFragment() {
        // Required empty public constructor
    }

    ImageView mtgCardImage;
    TextInputEditText mtgCardName;
    Button mtgSearchCard;

    String imageUrl;
    String preparedCardName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        mtgCardImage = (ImageView) v.findViewById(R.id.imageCard);
        mtgCardName = (TextInputEditText) v.findViewById(R.id.txtSearchedCard);
        mtgSearchCard = (Button) v.findViewById(R.id.btnSearchCard);


        mtgSearchCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pripravit si nazev karty
                preparedCardName = mtgCardName.getText().toString().trim().replace(' ', '+');

                Toast.makeText(getActivity(), preparedCardName, Toast.LENGTH_SHORT);

                RequestQueue cardRequest = Volley.newRequestQueue(getActivity());
                String url = "https://api.scryfall.com/cards/named?fuzzy="+preparedCardName;

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject images = response.getJSONObject("image_uris");
                                    Glide.with(getActivity()).load(images.getString("normal")).into(mtgCardImage);
                                }catch (Exception ex) {
                                    Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getActivity(), "Error in request: " +error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });

                cardRequest.add(jsonObjectRequest);
            }
        });





        return v;
    }
}