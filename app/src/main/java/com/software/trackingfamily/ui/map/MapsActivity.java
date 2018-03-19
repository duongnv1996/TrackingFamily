package com.software.trackingfamily.ui.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.software.trackingfamily.MemberAdapter;
import com.software.trackingfamily.R;
import com.software.trackingfamily.application.AppController;
import com.software.trackingfamily.interfaces.ICallback;
import com.software.trackingfamily.interfaces.OnBackPressedListener;
import com.software.trackingfamily.models.User;
import com.software.trackingfamily.ui.AuthActivity;
import com.software.trackingfamily.ui.base.BaseActivity;
import com.software.trackingfamily.ui.profile.ProfileActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MapsActivity extends BaseActivity implements OnMapReadyCallback, OnBackPressedListener, MapContract.View, ICallback<Integer> {


    @BindView(R.id.imgAvatar)
    ImageView imgAvatar;
    @BindView(R.id.tvDistance)
    TextView tvDistance;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.layout_distance)
    LinearLayout layoutDistance;
    @BindView(R.id.btnMyLocation)
    FloatingActionButton btnMyLocation;
    @BindView(R.id.rcvMember)
    RecyclerView rcvMember;
    @BindView(R.id.avatarMember)
    CircleImageView avatarMember;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.lastTime)
    TextView lastTime;
    @BindView(R.id.btnDirect)
    ImageView btnDirect;
    @BindView(R.id.btnCall)
    ImageView btnCall;
    @BindView(R.id.LayoutinfoMember)
    LinearLayout LayoutinfoMember;
    private GoogleMap mMap;
    private LatLng myLatlng;
    private List<User> mListMember;
    private MemberAdapter mAdapter;
    private MapPresenter mapPresenter;
    private HashMap<User, Marker> mapMarker;
    private HashMap<Marker, User> mapUser;
    private List<LatLng> mListLatlng;
    private LatLngBounds latlngBoundCamera;
    private DirectionCustomModel directionCustomModel;
    private List<LatLng> latLngs = new ArrayList<>();
    private Polyline lines;

    @Override
    protected int initLayout() {
        return R.layout.activity_maps;
    }

    @Override
    protected void initVariables() {
        mapPresenter = new MapPresenter(this);
        mapPresenter.getListMember();
        mListMember = new ArrayList<>();
        mAdapter = new MemberAdapter(mListMember, getContext(), this);
        rcvMember.setAdapter(mAdapter);
        mapMarker = new HashMap<>();
        mapUser = new HashMap<>();
        mListLatlng = new ArrayList<>();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setNavigationBarColor(Color.parseColor("#20111111"));
//        }
        ButterKnife.bind(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        rcvMember.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rcvMember.setHasFixedSize(true);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            mapPresenter.getListMember();
        }
    }

    @OnClick(R.id.imgAvatar)
    public void onClickAvt() {
        startActivity(new Intent(MapsActivity.this, ProfileActivity.class));
    }

    private void logOut() {
        AppController.getInstance().setUser(null);
        AppController.getInstance().getmSetting().clear();
        startActivity(new Intent(this, AuthActivity.class));
        finishAffinity();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        boolean success = mMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.style));
        if (success) {

        } else {

        }


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
            }
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                myLatlng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16));
                mapPresenter.updateLatlng(myLatlng);
            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (mapUser.containsKey(marker) && myLatlng != null) {
                    tvName.setText(mapUser.get(marker).getUsername());
                    tvPhone.setText(mapUser.get(marker).getPhone());

                    showInfor();
                    currentUser = mapUser.get(marker);
                    LatLngBounds.Builder bound = new LatLngBounds.Builder().include(marker.getPosition()).include(myLatlng);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bound.build(), 18));
                    String url = getDirectionsUrl(myLatlng, marker.getPosition());
                    DownloadTask downloadTask = new DownloadTask();
                    downloadTask.execute(url);
                    if (lines != null) lines.remove();

                }
                return false;
            }
        });
        // bindToMap();

    }

    User currentUser;

    @OnClick({R.id.btnDirect, R.id.btnCall, R.id.btnMyLocation, R.id.btnBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnDirect:
                Uri gmmIntentUri = Uri.parse("geo:" + currentUser.getLast_lat() + "," + currentUser.getLast_lng());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
                break;
            case R.id.btnCall:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + tvPhone.getText().toString()));
                startActivity(intent);
                break;
            case R.id.btnBack:
                hideInfor();
                break;
            case R.id.btnMyLocation:
                if (latlngBoundCamera != null) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latlngBoundCamera, 16));
                } else {
                    if (myLatlng != null)
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLatlng, 16));

                }
                break;
        }
    }

    private void hideInfor() {
        findViewById(R.id.btnBack).setVisibility(View.INVISIBLE);
        rcvMember.setVisibility(View.VISIBLE);
        LayoutinfoMember.setVisibility(View.INVISIBLE);
        layoutDistance.setVisibility(View.INVISIBLE);
    }

    private void showInfor() {
        findViewById(R.id.btnBack).setVisibility(View.VISIBLE);
        rcvMember.setVisibility(View.INVISIBLE);
        LayoutinfoMember.setVisibility(View.VISIBLE);
        layoutDistance.setVisibility(View.VISIBLE);
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hiddenProgress() {

    }

    @Override
    public void onErrorApi(String message) {
        LogUtils.e(message);
    }

    @Override
    public void onError(String message) {
        LogUtils.e(message);

    }

    @Override
    public void onErrorAuthorization() {

    }

    @Override
    public void onSuccessGetListMember(List<User> listUser) {
        mListMember.clear();
        mListMember.addAll(listUser);
        mAdapter.notifyDataSetChanged();

        bindToMap();
    }

    private void bindToMap() {
        if (myLatlng != null) mListLatlng.add(myLatlng);
        LatLngBounds.Builder latlngBounds = LatLngBounds.builder();

        for (User u : mListMember) {
            if (u.getLast_lat() != null && !u.getLast_lat().isEmpty() && u.getLast_lng() != null && !u.getLast_lng().isEmpty()) {
                double lat = Double.parseDouble(u.getLast_lat());
                double lng = Double.parseDouble(u.getLast_lng());
                LatLng latlng = new LatLng(lat, lng);
                mListLatlng.add(latlng);
                latlngBounds.include(latlng);
                Marker marker = mMap.addMarker(new MarkerOptions().position(latlng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_a)));
                mapMarker.put(u, marker);
                mapUser.put(marker, u);
            }
        }
        latlngBoundCamera = latlngBounds.build();
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latlngBoundCamera, 16));
    }


    // click to member
    @Override
    public void callBack(Integer object) {
        tvName.setText(mListMember.get(object).getUsername());
        tvPhone.setText(mListMember.get(object).getPhone());

        showInfor();
        if (mapMarker.containsKey(mListMember.get(object)) && myLatlng != null) {
            Marker m = mapMarker.get(mListMember.get(object));
            LatLngBounds.Builder bound = new LatLngBounds.Builder().include(m.getPosition()).include(myLatlng);
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bound.build(), 18));
            currentUser = mListMember.get(object);

            String url = getDirectionsUrl(myLatlng, m.getPosition());
            DownloadTask downloadTask = new DownloadTask();
            downloadTask.execute(url);
            if (lines != null) lines.remove();

        }
    }


    //    Polyline
    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=AIzaSyCD4lvlAW4lrqWufNztN_KRL8SuIEBW2Wg";

        return url;
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception while downloading url", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


    /**
     * A class to download data from Google Directions URL
     */
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /**
     * A class to parse the Google Directions in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();
                // Starts parsing data
                directionCustomModel = parser.parse(jObject);
                String duration = directionCustomModel.getDuration().replace("min", "phút");
                duration.replace("hour", "giờ");
                duration.replace("s", "");
                duration.replace(" ", "");
                routes = directionCustomModel.getMapPolyline();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            if (result == null) return;
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                points.add(new LatLng(myLatlng.latitude, myLatlng.longitude));
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    latLngs.add(position);
                    points.add(position);
                }
                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width((int) getResources().getDimension(R.dimen.poliline__default_width));
//                lineOptions.color(ContextCompat.getColor(BookingActivity.this, R.color.green));
                lineOptions.color(R.color.colorPrimary);
            }

            // Drawing polyline in the Google Map for the i-th route
//            mMap.cl ear();
//            if (lineOptions != null) mMap.addPolyline(lineOptions);
//            else showDialogErrorContent("Hệ thống chưa xác định được đường đi tới địa điểm này");
            tvDistance.setText(String.format(getString(R.string.distance_booking_format), directionCustomModel.getDistanceTxt()));
            tvTime.setText(directionCustomModel.getDuration().replace("day", "ngày").replace("min", "phút").replace("hour", "giờ").replace("s", ""));
            PolylineOptions optionsBackground = new PolylineOptions().add().width(10);
            if (lineOptions == null) return;
//            if(getmSetting().getBoolean(Constants.DIRECTION,true)){
            lines = mMap.addPolyline(lineOptions);
            startAnim(points, lines);
//            }
        }
    }

    private void startAnim(List<LatLng> bangaloreRoute, Polyline lines) {
        if (mMap != null) {
            MapAnimator.getInstance(this).animateRoute(mMap, bangaloreRoute, lines);
        } else {
            Toast.makeText(getApplicationContext(), "Map not ready", Toast.LENGTH_LONG).show();
        }
    }
}
