package etu.toptip.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import etu.toptip.IListner;
import etu.toptip.R;
import etu.toptip.model.Place;

public class PlaceAdapter extends BaseAdapter {

    private Context context ;
    private List<Place> places ;
    private LayoutInflater inflater ;

    private IListner listner ;

    public PlaceAdapter(Context context, List<Place> places) {
        this.context = context;
        this.places = places;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return places.size();
    }

    @Override
    public Place getItem(int i) {
        return places.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        LinearLayout view ;
        view = (LinearLayout) (convertView == null ? inflater.inflate(R.layout.adapter_item, parent, false) : convertView);
        Place currentPlace = getItem(i);
        String name = currentPlace.getName();
        String details = currentPlace.getDescription();
        Integer image = currentPlace.getImage();

        TextView nameView = view.findViewById(R.id.place_name);
        nameView.setText(name);

        TextView detailsView = view.findViewById(R.id.place_details);
        detailsView.setText(details);


        ImageView imageView = view.findViewById(R.id.place_icon);
        imageView.setImageResource(image);

        view.setOnClickListener(click->{
            listner.OnClickPlace(currentPlace);
        });



        return view;
    }

    public void addListner(IListner listner ){
        this.listner = listner;
    }
}