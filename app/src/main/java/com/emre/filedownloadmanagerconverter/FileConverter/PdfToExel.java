package com.emre.filedownloadmanagerconverter.FileConverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;



import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.aspose.pdf.Document;
import com.aspose.pdf.SaveFormat;
import java.io.File;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.emre.filedownloadmanagerconverter.R;
import android.os.Environment;

import java.util.UUID;



public class PdfToExel extends AppCompatActivity {

    private final String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator;
    String outputex;
    ProgressBar progressBar;
    TextView text6;

    Document pdf;


    ImageButton backhomeBtn;

    ImageButton pdftoexeluploadBtn;
    TextView pdftoexelText;
    UUID uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_to_exel);

        backhomeBtn=findViewById(R.id.backhomebutton);
        pdftoexeluploadBtn=findViewById(R.id.pdftoexeluploadbutton);
        pdftoexelText=findViewById(R.id.pdftoexeltext);


        backhomeBtn.setOnClickListener(v -> {
            startActivity(new Intent(PdfToExel.this, FileConverterActivity.class));
            finish();
        });

        pdftoexeluploadBtn.setOnClickListener(v -> {
            Intent myfileopener = new Intent(Intent.ACTION_GET_CONTENT);
            myfileopener.setType("*/*");
            startActivityForResult(Intent.createChooser(myfileopener,"Choose File"),10);
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==10){
            if(resultCode==RESULT_OK&&null != data) {
                try (InputStream inputStream = getContentResolver().openInputStream(data.getData())) {
                    uuid=UUID.randomUUID();
                    outputex=directory+uuid.toString()+".xlsx";
                    pdf = new Document(inputStream);
                    pdf.save(outputex, SaveFormat.Excel);
                    text6.setText("File converted and saved in Downloads folder. You can convert more files by pressing upload button.");
                    progressBar.setVisibility(View.GONE);
                }catch (Exception e){
                    text6.setText("Error. Check your file type and Storage permissions");
                    progressBar.setVisibility(View.GONE);
                }

            }
        }
    }

}