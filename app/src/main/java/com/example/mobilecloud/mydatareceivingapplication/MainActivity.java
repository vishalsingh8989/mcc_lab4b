package com.example.mobilecloud.mydatareceivingapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private ImageView imageView1;
    private ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_view);
        imageView1 = findViewById(R.id.image_1);
        imageView2 = findViewById(R.id.image_2);

        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            } else if (type.startsWith("image/")) {
                handleSendImage(intent); // Handle single image being sent
            }
        } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                handleSendMultipleImages(intent); // Handle multiple images being sent
            }
        } else {
            // Handle other intents, such as being started from the home screen
        }
    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            textView.setText("Text: " + sharedText);
        }
    }

    void handleSendImage(Intent intent) {
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            imageView1.setImageURI(null);
            imageView1.setImageURI(imageUri);
        }
    }

    void handleSendMultipleImages(Intent intent) {
        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        int n = 0;
        if (imageUris != null) {
            for( Uri imageUri : imageUris ){
                if(n++ == 0 ){
                    imageView1.setImageURI(null);
                    imageView1.setImageURI(imageUri);
                } else {
                    imageView2.setImageURI(null);
                    imageView2.setImageURI(imageUri);
                }
                if(n == 2)
                {
                    break;
                }
            }
        }
    }
}
