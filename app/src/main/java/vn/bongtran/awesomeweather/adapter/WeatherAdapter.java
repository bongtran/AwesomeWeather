package vn.bongtran.awesomeweather.adapter;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import vn.bongtran.awesomeweather.R;
import vn.bongtran.awesomeweather.model.WeatherInfo;
import vn.bongtran.awesomeweather.utils.FileUtils;

/**
 * Created by Peter on 11/3/17.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.Holder> {
    private Context context;
    private ArrayList<WeatherInfo> weatherInfoArrayList = new ArrayList<>();

    public WeatherAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<WeatherInfo> infors) {
        this.weatherInfoArrayList = infors;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(R.layout.weather_item, parent, false);
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final WeatherInfo info = weatherInfoArrayList.get(position);
        StringBuilder builder = new StringBuilder();
        builder.append("Humidity ").append(info.getHumidity()).append("%");
        String humidity = builder.toString();

        holder.txtCityName.setText(info.getCityName());
        holder.txtDescription.setText(info.getWeatherDescription());
        holder.txtHumidity.setText(humidity);
        holder.txtTime.setText(info.getObservationTime());
        if (info.isOffline()) {
            try {
                File imageFile = FileUtils.getFileFromURL(new URL(info.getWeatherIcon()));

                Picasso.with(context).load(imageFile).centerCrop().resize(70, 70).into(holder.imgView);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            Picasso.with(context).load(info.getWeatherIcon()).centerCrop().resize(70, 70).into(holder.imgView);
        }
    }

    @Override
    public int getItemCount() {
        return weatherInfoArrayList.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView imgView;
        TextView txtCityName;
        TextView txtTime;
        TextView txtHumidity;
        TextView txtDescription;


        public Holder(View itemView) {
            super(itemView);
            imgView = (ImageView) itemView.findViewById(R.id.img_weather_icon);
            txtCityName = (TextView) itemView.findViewById(R.id.txt_city_name);
            txtTime = (TextView) itemView.findViewById(R.id.txt_date);
            txtDescription = (TextView) itemView.findViewById(R.id.txt_description);
            txtHumidity = (TextView) itemView.findViewById(R.id.txt_humidity);
        }
    }
}
