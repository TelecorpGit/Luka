package com.telecorp.teledev.lukaapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.telecorp.teledev.lukaapp.LukaFirebaseInstanceIDService.TAG;

public class DialogActivity extends Activity {

    private static MediaPlayer mediaplayer ;
    private String msgTitle;
    private String msgBody;
    private String msgLink;
    private String msgRefrigUID;
    private String msgCode;
    private static SoundPool soundPool;
    public static boolean isplayingAudio=false;
    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;
    int statusSound;
    private Dialog dialog ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_dialog);
        msgTitle = getIntent().getStringExtra("title");
        msgBody = getIntent().getStringExtra("body");
        msgLink = getIntent().getStringExtra("link");
        msgRefrigUID = getIntent().getStringExtra("RefrigUID");
        msgCode = getIntent().getStringExtra("code");

        SharedPreferences shCheckSound = getSharedPreferences("STATUS_SOUND",MODE_PRIVATE);
        statusSound = shCheckSound.getInt("firstTime", 0);

        if (statusSound == 0){
            dialogDisplay();
        }else {
            mediaplayer.stop();
            dialogDisplay();
        }

    }

    private void dialogDisplay() {
        dialog = new Dialog(DialogActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_notification);
        dialog.setCancelable(false);
        mediaplayer  = MediaPlayer.create(this, R.raw.emergencyalarm);
        mediaplayer.setLooping(true);


        Button  btnOk = dialog.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onDestroy();
                Intent i = new Intent(DialogActivity.this, MainActivity.class);
                i.putExtra("link", msgLink);
                i.putExtra("RefrigUID", msgRefrigUID);
                i.putExtra("code", msgCode);
                startActivity(i);
//                    finish();
                dialog.cancel();

            }
        });

        TextView titel = dialog.findViewById(R.id.txt_title);
        titel.setText(msgTitle);
        TextView body = dialog.findViewById(R.id.txt_body);
        body.setText(msgBody);

        dialog.show();


        SharedPreferences shCheckSound = getSharedPreferences("STATUS_SOUND",MODE_PRIVATE);
        statusSound = shCheckSound.getInt("firstTime", 0);

        if (statusSound == 0){
            playSound();
            SharedPreferences shStatusSound = getSharedPreferences("STATUS_SOUND", MODE_PRIVATE);
            SharedPreferences.Editor edSound = shStatusSound.edit();
            edSound.putInt("firstTime", 1);
            edSound.commit();
        }else {
            SharedPreferences shStatusSound = getSharedPreferences("STATUS_SOUND", MODE_PRIVATE);
            SharedPreferences.Editor edSound = shStatusSound.edit();
            edSound.putInt("firstTime", 0);
            edSound.commit();
            refreshDisplay();
        }
    }

    private void refreshDisplay() {
        try {
            stopSound();
            mediaplayer.prepare();
            playRestart();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void playRestart(){
        dialogDisplay();
    }

    private void stopSound() {
        mediaplayer.stop();
    }

    private void playSound() {
        mediaplayer.start();
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
//        mediaplayer.stop();
        SharedPreferences clearSound = getSharedPreferences("STATUS_SOUND", MODE_PRIVATE);
        SharedPreferences.Editor edClear = clearSound.edit();
        edClear.clear();
        edClear.commit();
        stopSound();

    }
}


