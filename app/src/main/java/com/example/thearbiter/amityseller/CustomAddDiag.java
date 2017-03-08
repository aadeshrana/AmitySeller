package com.example.thearbiter.amityseller;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import it.sauronsoftware.ftp4j.FTPClient;

/**
 * Created by Aadesh Rana on 24-02-17.
 */

public class CustomAddDiag extends DialogFragment {
    private static final String PULL_ITEMS_URL = "http://frame.ueuo.com/midnightshop/addnewItem.php";
    JSONParser jsonParser = new JSONParser();
    EditText nameOfItem, priceOfitem;
    static String sendName, sendPrice, SendAvail;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    Uri filepath1;
    static final String FTP_HOST = "93.188.160.157";
    static final String FTP_USER = "u856924126";
    static final String FTP_PASS = "aadesh";

    String sNameOfItem,sPrice;
    final FTPClient client = new FTPClient();
    final FTPClient client2 = new FTPClient();
    private int PICK_IMAGE_REQUEST = 1;
    ImageView displayImage;
    String realPath1;
    Bitmap bitmap;
    File f;
    Context context;
    Button chooseImage,uploadImage;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_new_items, container, false);
        setCancelable(false);
         chooseImage = (Button) view.findViewById(R.id.choseImage);
         uploadImage = (Button) view.findViewById(R.id.upoadImage);
        displayImage = (ImageView) view.findViewById(R.id.tempImage);

        Button cancel = (Button) view.findViewById(R.id.cancelButton);
        Button addnew = (Button) view.findViewById(R.id.addnew);
        nameOfItem = (EditText) view.findViewById(R.id.addnewItem);
        priceOfitem = (EditText) view.findViewById(R.id.addnewPrice);
        context = getActivity();
        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendName = nameOfItem.getText().toString();
                Log.d("what", "" + sendName);
                sendPrice = priceOfitem.getText().toString();
                new AddnewItem().execute();


            }
        });

        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sNameOfItem = nameOfItem.getText().toString();
                sPrice = priceOfitem.getText().toString();
                new connecthis().execute();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    public class AddnewItem extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {

            try {
                Log.d("Before Vie Orders", "");
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("itemName", sendName));
                params.add(new BasicNameValuePair("priceofitem", sendPrice));
                params.add(new BasicNameValuePair("avail", "1"));

                JSONObject json = jsonParser.makeHttpRequest(PULL_ITEMS_URL, "POST", params);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getActivity(), "added bro", Toast.LENGTH_SHORT).show();
            dismiss();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            filepath1 = data.getData();
           // displayImage.setImageURI(filepath1);
            realPath1 = ImageFilePath.getPath(getActivity(), data.getData());
            Log.d("gg", "kk" + realPath1);
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), filepath1);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
            byte[] bitMapData = out.toByteArray();
            String namegetter[] = realPath1.split("/");
            int finalElement = namegetter.length - 1;
            f = new File(context.getCacheDir(), namegetter[finalElement]);
            f.createNewFile();
            Picasso.with(context).load(f).into(displayImage);
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitMapData);
            fos.flush();
            fos.close();


        } catch (Exception e) {

        }

    }
    class connecthis extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {


            try {
                client.connect(FTP_HOST, 21);
                client.login(FTP_USER, FTP_PASS);
           /* client.enterLocalPassiveMode();*/
                client.setPassive(true);
                Log.d("REAL PATH OF LIFE IS 1 ", "" + realPath1);
                client.setType(FTPClient.TYPE_BINARY);
                client.setAutoNoopTimeout(20);
                client.upload(f);


            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Unsuccessful. Try Again", Toast.LENGTH_SHORT).show();
            }

            try {
                client.disconnect(true);

                String namegetter[] = realPath1.split("/");
                int finalElement = namegetter.length - 1;
                Log.d("kateko name 1", "" + namegetter[finalElement]);
//                    pdialog.show();
                try {

                    client2.connect(FTP_HOST, 21);
                    client2.login(FTP_USER, FTP_PASS);


                    String sendThis;
                    sendThis = sNameOfItem.replaceAll("\\s+", "");
                    Log.d("nameOF","ya"+sNameOfItem);
                    client2.rename(namegetter[finalElement], sendThis + sPrice+".JPG");
                } catch (Exception f) {
                    f.printStackTrace();
                }
                client2.disconnect(true);
//                    pdialog.dismiss();

            } catch (Exception e2) {
                e2.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//                pdialog.dismiss();
            Toast.makeText(context, "Uploaded Image 1", Toast.LENGTH_SHORT).show();




        }
    }

}
