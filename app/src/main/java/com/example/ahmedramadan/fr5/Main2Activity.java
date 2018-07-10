package com.example.ahmedramadan.fr5;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.util.Locale;

public class Main2Activity extends AppCompatActivity {
    public Button btnSearch;
    public ImageView imgview ;
    TextToSpeech t1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btnSearch = (Button)findViewById(R.id.btn1);
        imgview = (ImageView)findViewById(R.id.show);
        final TextView textView = (TextView)findViewById(R.id.textshow);
        Intent intent = getIntent();
        byte[] byteArray = intent.getByteArrayExtra("image");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        imgview.setImageBitmap(bmp);
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.ENGLISH);
                }
            }
        });

        //BitmapFactory.decodeResource(getApplication().getResources() , R.id.show);
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
if (!textRecognizer.isOperational()){
    Toast.makeText(this , "coud not get the text " , Toast.LENGTH_SHORT).show();
}
else {
    Frame frame = new Frame.Builder().setBitmap(bmp).build();
    final SparseArray<TextBlock> items = textRecognizer.detect(frame);

   if ( items.size() != 0 ) {
        textView.post(new Runnable() {
            @Override
            public void run() {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < items.size(); ++i) {
                    TextBlock item = items.valueAt(i);
                    stringBuilder.append(item.getValue());
                    stringBuilder.append("\n");
                }
                textView.setText(stringBuilder.toString());
                String o = textView.getText().toString();
                t1.speak(o, TextToSpeech.QUEUE_FLUSH, null);

            }

    });
}
}
    }

}