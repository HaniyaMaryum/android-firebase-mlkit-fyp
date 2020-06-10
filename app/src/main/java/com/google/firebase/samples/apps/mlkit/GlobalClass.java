package com.google.firebase.samples.apps.mlkit;

import android.app.Application;
import android.media.MediaPlayer;

public class GlobalClass   extends Application {


    public int rightindex=0;
    public int leftindex=0;

    public int rightEyeIndex;
    public int leftEyeIndex;


    public  String[] righteye=new String[80];
    public  String[] lefteye=new String[80];



    public void playSound(int resId){
        MediaPlayer mp = MediaPlayer.create(GlobalClass.this, resId);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.reset();
                mediaPlayer.release();
            }
        });
        mp.start();
    }



//    public void showDialog() {
//        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
//                GlobalClass.this);
//
//// Setting Dialog Title
//        alertDialog2.setTitle("Confirm Delete...");
//
//// Setting Dialog Message
//        alertDialog2.setMessage("Are you sure you want delete this file?");
//
//// Setting Icon to Dialog
////        alertDialog2.setIcon(R.drawable.delete);
//
//// Setting Positive "Yes" Btn
//        alertDialog2.setPositiveButton("YES",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Write your code here to execute after dialog
//                        Toast.makeText(getApplicationContext(),
//                                "You clicked on YES", Toast.LENGTH_SHORT)
//                                .show();
//                    }
//                });
//// Setting Negative "NO" Btn
//        alertDialog2.setNegativeButton("NO",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Write your code here to execute after dialog
//                        Toast.makeText(getApplicationContext(),
//                                "You clicked on NO", Toast.LENGTH_SHORT)
//                                .show();
//                        dialog.cancel();
//                    }
//                });

// Showing Alert Dialog
//     Alert   alertDialog2.show();

    //    }
    public void warningalert() {
        playSound(R.raw.alert);

    }
    public void highalert() {
        playSound(R.raw.alert1);
    }
}


