package com.frabbi.mydownloaddemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.frabbi.mydownloaddemo.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final int REQUEST_PERMISSION = 101;
    private ActivityMainBinding binding;
    private String[] imgName ;
    private int[] img;
    private List<InFo> list;
    private CustomAdapter customAdapter;
    private ListView listView;
    private String[] imgLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        imgName = getResources().getStringArray(R.array.listName);
        img = new int[]{R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5, R.drawable.img6, R.drawable.img7};
        imgLink = getResources().getStringArray(R.array.imageLink);

        list = new ArrayList<>();
        readyData();

        binding.downloadBtnId.setOnClickListener(this);
        customAdapter = new CustomAdapter(this,R.layout.list_view,list);
        listView = findViewById(R.id.listViewId);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(this);
        binding.progressBar.setMax(100);


    }

    private void readyData() {
        int id = 1;
        for (int i = 0; i < imgName.length; i++) {
            list.add(new InFo(id,img[i],imgName[i]));
            id++;
        }
    }

    public boolean isInternetConnected(){
        boolean connected = false;
        try {
            ConnectivityManager cm =
                    (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        }catch (Exception e){
            Log.e("Connectivity Exception-", e.getMessage());
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        if(view == binding.downloadBtnId){
            if(isInternetConnected()){
                   // binding.linearLayoutId.setVisibility(binding.linearLayoutId.VISIBLE);
                  //  new DownloadThread().start();
                new MyTask().execute(binding.linkInputTextId.getText().toString());
            }else {
                Toast.makeText(getApplicationContext(),"No Internet",Toast.LENGTH_LONG).show();
            }
        }
    }
   /* public boolean downloadImage(String url){
        boolean successful = false;
        URL downlodUrl = null;
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        File file = null;
        try {
            downlodUrl = new URL(url);
            connection = (HttpURLConnection) downlodUrl.openConnection();
            inputStream = connection.getInputStream();
            Random g = new Random();
            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()+"/"+ Uri.parse(url).getLastPathSegment()+g.nextInt());
            String dir = file.getAbsolutePath();
            if (!dir.contains(".jpg") || !dir.contains(".png")) {
                dir = dir+".jpg";
            }
            fileOutputStream = new FileOutputStream(dir);
            int read = -1;
            byte[] buffer = new byte[1024];
            while ((read = inputStream.read(buffer)) != -1){
                fileOutputStream.write(buffer, 0, read);
            }

            successful = true;
            return successful;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e ) {
            e.printStackTrace();
        }finally {
            if(connection != null){
                connection.disconnect();

            }
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }*/

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        binding.linkInputTextId.getText().clear();
        binding.linkInputTextId.setText(imgLink[i]);
        binding.linearLayoutId.setVisibility(binding.linearLayoutId.INVISIBLE);
    }
/*Child Thread */
/*    class DownloadThread extends Thread{
        @Override
        public void run() {
            super.run();
            if (downloadImage(binding.linkInputTextId.getText().toString())) {
                binding.linearLayoutId.setVisibility(binding.linearLayoutId.INVISIBLE);
            }

        }
    }*/

    class MyTask extends AsyncTask<String,Integer,Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            binding.linearLayoutId.setVisibility(binding.linearLayoutId.VISIBLE);

        }

        @Override
        protected Boolean doInBackground(String... strings) {
           // return downloadImage(strings[0]);
            boolean successful = false;
            URL downlodUrl = null;
            HttpURLConnection connection = null;
            InputStream inputStream = null;
            FileOutputStream fileOutputStream = null;
            File file = null;
            try {
                downlodUrl = new URL(strings[0]);
                connection = (HttpURLConnection) downlodUrl.openConnection();
                inputStream = connection.getInputStream();
                Random g = new Random();
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()+"/"+ Uri.parse(strings[0]).getLastPathSegment()+g.nextInt());
                String dir = file.getAbsolutePath();
                if (!dir.contains(".jpg") || !dir.contains(".png")) {
                    dir = dir+".jpg";
                }
                fileOutputStream = new FileOutputStream(dir);
                int read = -1;
                int imgFullSize = connection.getContentLength();

                int cSize = 0;
                byte[] buffer = new byte[1024];
                while ((read = inputStream.read(buffer)) != -1){
                    fileOutputStream.write(buffer, 0, read);
                    cSize+=read;
                    int puValue= ((cSize*100)/imgFullSize);
                 //   Log.d("Progress: ",puValue+" : "+imgFullSize);
                    publishProgress(puValue);
                }

                successful = true;
                return successful;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e ) {
                e.printStackTrace();
            }finally {
                if(connection != null){
                    connection.disconnect();

                }
                if(inputStream != null){
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return false;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
          //  Log.d("Progress: ",""+values[0]);
            binding.progressBar.setProgress(values[0]);

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                Toast.makeText(getApplicationContext(),"Download Completed"
                ,Toast.LENGTH_LONG).show();
            }
            binding.linearLayoutId.setVisibility(binding.linearLayoutId.INVISIBLE);
        }
    }

    private int inputStreamLength(InputStream inputStream) {
        int read = -1;
        int size = 0;
        byte[] buffer = new byte[1024];
        try {
            while ((read = inputStream.read(buffer)) != -1){
                size += read;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return size;
    }
}