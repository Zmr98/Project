package com.example.lms;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;

import com.example.lms.common.ViewFiles;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ViewPdfFiles extends AppCompatActivity {

    private static final String TAG = "ViewPdfFiles";
    private PDFView pdfView;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf_files);

        getSupportActionBar().setTitle("PDF Viewer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pdfView=(PDFView) findViewById(R.id.pdfView);

        String url = getIntent().getStringExtra("url");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            url = getIntent().getStringExtra("url");
        }
        new RetrievePDFStream().execute(url);
    }

     class RetrievePDFStream extends AsyncTask<String,Void, InputStream> {
            @Override
            protected InputStream doInBackground(String... strings) {
                InputStream inputStream = null;

                try{

                    URL url=new URL(strings[0]);
                    HttpURLConnection urlConnection=(HttpURLConnection) url.openConnection();
                    if(urlConnection.getResponseCode()==200){
                        inputStream=new BufferedInputStream(urlConnection.getInputStream());

                    }
                }catch (IOException e){
                    return null;
                }
                return inputStream;

            }

            @Override
            protected void onPostExecute(InputStream inputStream) {
                pdfView.fromStream(inputStream).load();
            }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}