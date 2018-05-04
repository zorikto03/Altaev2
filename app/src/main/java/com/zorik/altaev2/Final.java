package com.zorik.altaev2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Final extends AppCompatActivity {
    ImageView IV; BitmapDrawable Bit_Draw;

    int CountOfAccept, CountQuestionAll;
    String F_N, L_N, N_G, OutPrint;
    TextView PrintOut, Notes;
    Bitmap btm;
    File file;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        F_N = getIntent().getStringExtra("FName");
        L_N = getIntent().getStringExtra("LName");
        N_G = getIntent().getStringExtra("NGroup");

        CountOfAccept = getIntent().getIntExtra("CountOfAccept", CountOfAccept);
        CountQuestionAll = getIntent().getIntExtra("CountQuestionAll", CountQuestionAll);
        OutPrint = "Тема :   Hamming code\n" + "Выполнил : \n" + L_N + " " + F_N + "\n\nстудент группы :  " + N_G + "\n\nКоличество правильных ответов : " + CountOfAccept +"\nОбщее число вопросов : " + CountQuestionAll;
        PrintOut = findViewById(R.id.OutText);
        PrintOut.setText(OutPrint);

        Notes=findViewById(R.id.notes);
        String nts="Отправьте скрин формы с результатами теста\n\nжми на кнопку ОТПРАВИТЬ";
        Notes.setText(nts);

    }
    public void Send(View view) {

        btm = screenshote(view);   //скриншот результатов теста

        //Bit_Draw = new BitmapDrawable(getResources(), btm); //проверка создания скриншота
        //IV.setBackground(Bit_Draw);
        UriFile();
        uri = Uri.fromFile(file);
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("image/jpeg");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"altaevaa@mail.ru"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Hamming code");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Выполнил студент группы " + N_G + "\n" + L_N + " " + F_N);
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        try{
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        }catch(android.content.ActivityNotFoundException ex){
            Toast.makeText(this, "there is no email client installed", Toast.LENGTH_SHORT).show();
        }

    }
    public static Bitmap screenshote(View view){
        View v1 = view.getRootView();
        v1.setDrawingCacheEnabled(true);
        return v1.getDrawingCache();
    }

    public void UriFile(){
        file = new File(getExternalFilesDir(null) + "screen.jpeg");
        FileOutputStream output;
        try{
            output = new FileOutputStream(file);
            btm.compress(Bitmap.CompressFormat.JPEG, 100, output);
            output.flush();
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
