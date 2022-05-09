package etu.toptip.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Base64;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;

import etu.toptip.R;
import etu.toptip.activities.AddPlaceActivity;
import etu.toptip.fragments.NotificationsFragment;
import etu.toptip.model.NotificationsModel;
import etu.toptip.views.NotificationsView;


public class NotificationsController {
    private final NotificationsView notificationsView;
    private final Context context;
    private FragmentActivity addPlaceActivity;

    public NotificationsController(FragmentActivity addPlaceActivity, NotificationsFragment notificationsFragment, NotificationsView notificationsView) {
        this.notificationsView = notificationsView;
        this.context = notificationsFragment.getContext();
        this.addPlaceActivity = addPlaceActivity;
    }

    /**
     * Try to get the image as a Bitmap from a given link as parameter
     *
     * @param src link of the image to download
     * @return Bitmap image
     */
    public Bitmap fetchImage(final String src) {
        try {
            return BitmapFactory.decodeStream(new URL(src).openConnection().getInputStream());
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }


    public void newNotification() {
        NotificationsModel notificationsModel = NotificationsModel.getInstance();
        Observable e = new Observable();
        e.notifyObservers();

        EditText name = (EditText) addPlaceActivity.findViewById(R.id.nameResto);
        String nameText = name.getText().toString();
        EditText ville = (EditText) addPlaceActivity.findViewById(R.id.VilleResto);
        String villeText = ville.getText().toString();
        EditText code = (EditText) addPlaceActivity.findViewById(R.id.CodeP);
        String codeText = ville.getText().toString();
        EditText adresse = (EditText) addPlaceActivity.findViewById(R.id.AdresseResto);
        String adresseText = adresse.getText().toString();

        ImageView image = (ImageView) addPlaceActivity.findViewById(R.id.IVPreviewImage);
        Bitmap bitmap = Bitmap.createBitmap(image .getWidth(), image .getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        image.draw(canvas);
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String imageAsString = Base64.encodeToString(b, Base64.DEFAULT);

        notificationsModel.setNotificationText(nameText+", "+adresseText+", "+villeText);
        notificationsModel.setNotificationImage("file:///C:/Users/morga/Downloads/logo.webp");
        notificationsView.update(e, notificationsModel);
    }
}
