package com.codewithshreya.baby_new.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.codewithshreya.baby_new.R;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    TextView nameDetailTextView,descriptionDetailTextView, locationDetailTextView;
    ImageView DetailImageView;

    private void initializeWidgets(){
        nameDetailTextView= findViewById(R.id.nameDetailTextView);
        descriptionDetailTextView= findViewById(R.id.descriptionDetailTextView);
        //dateDetailTextView= findViewById(R.id.dateDetailTextView);
        //categoryDetailTextView= findViewById(R.id.categoryDetailTextView);
        locationDetailTextView= findViewById(R.id.locationDetailTextView);
        DetailImageView=findViewById(R.id.DetailImageView);
    }
    /*private String getDateToday(){
        DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
        Date date=new Date();
        String today= dateFormat.format(date);
        return today;
    }*/
    /*private String getRandomCategory(){
        String[] categories={"Vegetarian"};
        Random random=new Random();
        int index=random.nextInt(categories.length-1);
        return categories[index];
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initializeWidgets();

        //RECEIVE DATA FROM ITEMSACTIVITY VIA INTENT
        Intent i=this.getIntent();
        String name=i.getExtras().getString("NAME_KEY");
        String description=i.getExtras().getString("DESCRIPTION_KEY");
        String location=i.getExtras().getString("LOCATION_KEY");
        String imageURL=i.getExtras().getString("IMAGE_KEY");

        //SET RECEIVED DATA TO TEXTVIEWS AND IMAGEVIEWS
        nameDetailTextView.setText(name);
        descriptionDetailTextView.setText(description);
        locationDetailTextView.setText(location);

        Picasso.with(this)
                .load(imageURL)
                .placeholder(R.drawable.placeholder)
                .fit()
                .centerCrop()
                .into(DetailImageView);

    }

}
