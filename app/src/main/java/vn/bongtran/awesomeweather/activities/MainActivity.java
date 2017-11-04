package vn.bongtran.awesomeweather.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import vn.bongtran.awesomeweather.R;
import vn.bongtran.awesomeweather.adapter.WeatherAdapter;
import vn.bongtran.awesomeweather.businesslogic.LocalDataProcessing;
import vn.bongtran.awesomeweather.businesslogic.WeatherInfoParser;
import vn.bongtran.awesomeweather.constant.Constant;
import vn.bongtran.awesomeweather.database.DataManager;
import vn.bongtran.awesomeweather.model.WeatherInfo;
import vn.bongtran.awesomeweather.utils.NetworkUtils;

public abstract class MainActivity extends WeatherBaseActivity implements View.OnClickListener {
    private ArrayList<WeatherInfo> weatherInfo;
    private WeatherAdapter weatherAdapter;
    RecyclerView weatherView;

    private SwipeRefreshLayout swipeRefreshLayout;
    EditText txtSearch;

    private boolean isLoading;
    private String locationName;
    ListView namesView;
    ArrayAdapter nameAdapter;
    ArrayList<String> names = new ArrayList<String>();

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    private String apiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtSearch = findViewById(R.id.txt_search);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.sc_weather_list);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.orange);
        swipeRefreshLayout.setColorSchemeResources(R.color.white);

        weatherView = (RecyclerView) findViewById(R.id.view_weathers);
        weatherAdapter = new WeatherAdapter(this);
        weatherView.setAdapter(weatherAdapter);

        namesView = findViewById(R.id.view_names);
        nameAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, names);
        namesView.setAdapter(nameAdapter);

        setListener();
    }

    private void setListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        getWeatherInfo();
                    }
                }, 500);
            }
        });

        findViewById(R.id.btn_clear).setOnClickListener(this);
        findViewById(R.id.btn_search).setOnClickListener(this);

        txtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    updateNamesSuggestion();
                    showNamesView(true);
                    showWeathersView(false);
                } else {
                    showNamesView(false);
                    showWeathersView(true);
                }
            }
        });

        namesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                locationName = names.get(i);
                txtSearch.setText(locationName);
                Log.d("LOG NAME", locationName);
                processSearch();
            }
        });
    }

    private void updateNamesSuggestion() {
        names = DataManager.sharedInstance().getNames();
        nameAdapter.clear();
        nameAdapter.addAll(names);
        nameAdapter.notifyDataSetChanged();
        Log.d("NAMES", names.toString());
    }

    private void getWeatherInfo() {
        if (!NetworkUtils.hasInternetConnection(this)) {
//            Toast.makeText(MainActivity.this, "Can't connect to the internet", Toast.LENGTH_LONG).show();
            WeatherInfo info = DataManager.sharedInstance().getInfo(locationName);
            if (info != null) {
//                info.setOffline(true);
                showData(info);
            } else {
                showDialog(MainActivity.this, getString(R.string.txt_title_no_internet), getString(R.string.txt_msg_no_internet));
            }
            return;
        }
        showProgressDialog(getString(R.string.txt_status_waiting));

        Log.d("LOG API KEY", apiKey);

        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constant.WEATHER_INFO_URL).newBuilder();
        urlBuilder.addQueryParameter("key", apiKey);
        urlBuilder.addQueryParameter("q", locationName);
        urlBuilder.addQueryParameter("fx", "yes");
        urlBuilder.addQueryParameter("tp", "24");
        urlBuilder.addQueryParameter("format", "json");
        urlBuilder.addQueryParameter("num_of_days", "1");
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, R.string.txt_cant_connect, Toast.LENGTH_LONG).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showDialog(MainActivity.this, getString(R.string.txt_title_not_found), getString(R.string.txt_msg_not_found));
                        }
                    });
                } else {
                    final String bodyString = response.body().string();
                    if (WeatherInfoParser.hasWeatherData(bodyString)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                Log.d("LOG", bodyString);
                                WeatherInfo info = WeatherInfoParser.getWeatherInfo(bodyString);
                                showData(info);
                                saveData(info);
//                                requestPermission();
                            }
                        });

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                try {
//                                    String message = WeatherInfoParser.getErrorMessage(response.body().string());
                                showDialog(MainActivity.this, getString(R.string.txt_title_not_found), getString(R.string.txt_msg_not_found_name));
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
                            }
                        });
                    }
                }
                hideProgressDialog();
            }
        });
    }

    private void showData(WeatherInfo info) {
        weatherInfo = new ArrayList<>();
        weatherInfo.add(info);
        weatherAdapter.setData(weatherInfo);
        weatherAdapter.notifyDataSetChanged();
    }

    private void saveData(WeatherInfo info) {
        LocalDataProcessing.saveInfo(info, locationName);
//        new DownloadImageAsyncTask().execute(info.getWeatherIcon());
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_search:
                processSearch();
                break;
            case R.id.btn_clear:
                clearText();
                break;
            default:
                break;
        }
    }

    private void clearText() {
        txtSearch.setText("");
    }

    private void showWeathersView(boolean visible) {
        if (visible) {
            weatherView.setVisibility(View.VISIBLE);
        } else {
            weatherView.setVisibility(View.GONE);
        }
    }

    private void showNamesView(boolean visible) {
        if (visible) {
            namesView.setVisibility(View.VISIBLE);
        } else {
            namesView.setVisibility(View.GONE);
        }
    }

    private void closeKeyBoard() {
        txtSearch.clearFocus();
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void processSearch() {
        showNamesView(false);
        showWeathersView(true);
        closeKeyBoard();
        locationName = txtSearch.getText().toString();
        if (!locationName.isEmpty())
            getWeatherInfo();
        else {
            Toast.makeText(this, R.string.txt_msg_type_to_search, Toast.LENGTH_LONG).show();
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, // request permission when it is not granted.
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                }
                return;
            }
        }
    }
}