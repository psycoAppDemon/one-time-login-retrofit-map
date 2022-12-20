package com.example.assignment_osos;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    RecyclerView recyclerView;
    private GoogleMap mMap;
    private Marker mm;
    private UserAdapter userAdapter;
    private Pair<Users, Integer> prevUser;


    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            float lat = Float.parseFloat(userAdapter.list.get(viewHolder.getPosition()).getAddress().getLatlng().getLat());
            float lng = Float.parseFloat(userAdapter.list.get(viewHolder.getPosition()).getAddress().getLatlng().getLng());

            if (mm != null) mm.remove();

            mm = mMap.addMarker((new MarkerOptions().position(new LatLng(lat, lng))).title("" + lat + " " + lng));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 2f));

            int position = viewHolder.getLayoutPosition();
            if (prevUser != null) {
                prevUser.first.setColor(-1);
                userAdapter.notifyItemChanged(prevUser.second);
            }
            Users user = userAdapter.getUser(position);
            user.setColor(Color.parseColor("#E6EEF7"));
            userAdapter.removeUser(position);
            userAdapter.addUser(user, position);
            prevUser = new Pair<>(user, position);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        recyclerView = findViewById(R.id.user_list_recycler_view);
        userAdapter = new UserAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(userAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        callRetrofit();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void callRetrofit() {
        Log.d("XYZ", "called");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Retrofit_Interface jsonPlaceHolder = retrofit.create(Retrofit_Interface.class);
        Call<ArrayList<Users>> call = jsonPlaceHolder.getUsers();

        call.enqueue(new Callback<ArrayList<Users>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Users>> call, @NonNull Response<ArrayList<Users>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MapsActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                assert response.body() != null;
                userAdapter.updateList(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Users>> call, @NonNull Throwable t) {
                Toast.makeText(MapsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
    }


}