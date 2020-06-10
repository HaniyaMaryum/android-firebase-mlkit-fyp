// Copyright 2018 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.firebase.samples.apps.mlkit.facedetection;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.samples.apps.mlkit.GlobalClass;
import com.google.firebase.samples.apps.mlkit.GraphicOverlay;
import com.google.firebase.samples.apps.mlkit.GraphicOverlay.Graphic;
import com.google.firebase.samples.apps.mlkit.PersonModel.Data;
import com.google.firebase.samples.apps.mlkit.R;

/**
 * Graphic instance for rendering face position, orientation, and landmarks within an associated
 * graphic overlay view.
 */
public class FaceGraphic extends Graphic {
  PopupMenu p;
  final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
  Context context;
  private static final float FACE_POSITION_RADIUS = 10.0f;
  private static final float ID_TEXT_SIZE = 40.0f;
  private static final float ID_Y_OFFSET = 50.0f;
  private static final float ID_X_OFFSET = -50.0f;
  private static final float BOX_STROKE_WIDTH = 5.0f;


  private static final int[] COLOR_CHOICES = {
    Color.BLUE //, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.RED, Color.WHITE, Color.YELLOW
  };
  private static int currentColorIndex = 0;

  private int facing;

  private final Paint facePositionPaint;
  private final Paint idPaint;
  private final Paint boxPaint;


  FirebaseDatabase db = FirebaseDatabase.getInstance();
  DatabaseReference databaseReference;

  private volatile FirebaseVisionFace firebaseVisionFace;

  public FaceGraphic(GraphicOverlay overlay) {
    super(overlay);
    System.out.println("FaceGraphic func");
    currentColorIndex = (currentColorIndex + 1) % COLOR_CHOICES.length;
    final int selectedColor = COLOR_CHOICES[currentColorIndex];

    facePositionPaint = new Paint();
    facePositionPaint.setColor(selectedColor);

    idPaint = new Paint();
    idPaint.setColor(selectedColor);
    idPaint.setTextSize(ID_TEXT_SIZE);

    boxPaint = new Paint();
    boxPaint.setColor(selectedColor);
    boxPaint.setStyle(Paint.Style.STROKE);
    boxPaint.setStrokeWidth(BOX_STROKE_WIDTH);

    databaseReference = db.getReference("EyesValues");


  }



  /**
   * Updates the face instance from the detection of the most recent frame. Invalidates the relevant
   * portions of the overlay to trigger a redraw.
   */
  public void updateFace(FirebaseVisionFace face, int facing) {
    firebaseVisionFace = face;
    this.facing = facing;
    postInvalidate();
    System.out.println("updateFace func");
  }

  /** Draws the face annotations for position on the supplied canvas. */
  @Override
  public void draw(Canvas canvas) {
    FirebaseVisionFace face = firebaseVisionFace;
    if (face == null) {
      return;
    }

    // Draws a circle at the position of the detected face, with the face's track id below.
    float x = translateX(face.getBoundingBox().centerX());
    float y = translateY(face.getBoundingBox().centerY());
    canvas.drawCircle(x, y, FACE_POSITION_RADIUS, facePositionPaint);


    globalVariable.righteye[globalVariable.rightindex]=String.format("%.2f", face.getRightEyeOpenProbability());
    globalVariable.lefteye[globalVariable.leftindex]=String.format("%.2f", face.getRightEyeOpenProbability());
//    System.out.println(globalVariable.s1[globalVariable.i]);


      globalVariable.rightEyeIndex = globalVariable.rightindex++;
      globalVariable.leftEyeIndex = globalVariable.leftindex++;



    String id = databaseReference.push().getKey();

    Data data = new Data(globalVariable.rightEyeIndex, globalVariable.leftEyeIndex, id);
    databaseReference.child(id).setValue(data);
    Toast.makeText(getApplicationContext(), "Data of eyes have been get", Toast.LENGTH_SHORT).show();







//   String s1=String.format("%.2f", face.getRightEyeOpenProbability());
    if((globalVariable.rightindex==80)&&(globalVariable.leftindex==80))
    {
      int warningcheck = 0;
      int leftwarning=0;
      int lefthigh=0;
      int highalert=0;
      String basevalue="0.80";
      for(int k = 0; k <=79; k++){

        if (((globalVariable.righteye[k].compareTo(basevalue)<=-1)&&(globalVariable.righteye[k].compareTo(basevalue)>=-6))&&((globalVariable.lefteye[k].compareTo(basevalue)<=-1)&&(globalVariable.lefteye[k].compareTo(basevalue)>=-6)))
        {
          warningcheck++;
        }
        else if((globalVariable.righteye[k].compareTo(basevalue)<=-7)&&(globalVariable.lefteye[k].compareTo(basevalue)<=-7)){
          highalert++;
        }
//      Toast.makeText(getApplicationContext(),warningcheck+"hi"+highalert, Toast.LENGTH_SHORT).show();
      }
      if (highalert>60){
        highalert=0;
        warningcheck=0;
        Toast.makeText(getApplicationContext(), "high alert ", Toast.LENGTH_SHORT).show();
        globalVariable.highalert();


      }
      else if ((warningcheck >5)&&(highalert>20)){
        warningcheck=0;
        highalert=0;

//      globalVariable.showDialog();

//      AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
//              getApplicationContext());
//
//// Setting Dialog Title
//      alertDialog2.setTitle("Confirm Delete...");
//
//// Setting Dialog Message
//      alertDialog2.setMessage("Are you sure you want delete this file?");
//
//// Setting Icon to Dialog
////        alertDialog2.setIcon(R.drawable.delete);
//
//// Setting Positive "Yes" Btn
//      alertDialog2.setPositiveButton("YES",
//              new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                  // Write your code here to execute after dialog
//                  Toast.makeText(getApplicationContext(),
//                          "You clicked on YES", Toast.LENGTH_SHORT)
//                          .show();
//                }
//              });
//// Setting Negative "NO" Btn
//      alertDialog2.setNegativeButton("NO",
//              new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                  // Write your code here to execute after dialog
//                  Toast.makeText(getApplicationContext(),
//                          "You clicked on NO", Toast.LENGTH_SHORT)
//                          .show();
//                  dialog.cancel();
//                }
//              });

// Showing Alert Dialog
//      alertDialog2.show();
        Toast.makeText(getApplicationContext(), "Warning alert ", Toast.LENGTH_SHORT).show();
        globalVariable.warningalert();
      }



      StringBuilder builder = new StringBuilder();
      for(String i : globalVariable.righteye)
      {
        builder.append("" + i + " ");
      }
//    Toast.makeText(getApplicationContext(), builder, Toast.LENGTH_LONG).show();
      System.out.println(builder);
//    Intent intent=new Intent(getApplicationContext(), TestResult.class);
//    intent.putExtra("Array",globalVariable.s1);
//    Intent intend = new Intent(getApplicationContext(), TestResult.class);
//    i.setClassName("com.google.firebase.samples.apps.mlkit.facedetection", "com.google.firebase.samples.apps.mlkit.facedetection.activity_test_result");
//    getApplicationContext().startActivity(intend);
      globalVariable.rightindex=0;
      globalVariable.leftindex=0;
    }

//  if ((face.getRightEyeOpenProbability()<0.10)&&(face.getLeftEyeOpenProbability())<0.10){
//    Toast.makeText(getApplicationContext(), "high Alert", Toast.LENGTH_SHORT).show();
//
//     }
//  else if(((face.getRightEyeOpenProbability()<0.40)&&(face.getRightEyeOpenProbability()>0.10))&&((face.getLeftEyeOpenProbability()<0.40)&&(face.getLeftEyeOpenProbability()>0.10)))
//      Toast.makeText(getApplicationContext(), "Warning  Alert", Toast.LENGTH_SHORT).show();


//    }


    if (facing == CameraSource.CAMERA_FACING_FRONT) {
      canvas.drawText(
          "right eye: " + String.format("%.2f", face.getRightEyeOpenProbability()),
          x - ID_X_OFFSET,
          y,
          idPaint);
      canvas.drawText(
          "left eye: " + String.format("%.2f", face.getLeftEyeOpenProbability()),
          x + ID_X_OFFSET * 6,
          y,
          idPaint);
    } else {
      canvas.drawText(
          "left eye: " + String.format("%.2f", face.getLeftEyeOpenProbability()),
          x - ID_X_OFFSET,
          y,
          idPaint);
      canvas.drawText(
          "right eye: " + String.format("%.2f", face.getRightEyeOpenProbability()),
          x + ID_X_OFFSET * 6,
          y,
          idPaint);
    }

    // Draws a bounding box around the face.
    float xOffset = scaleX(face.getBoundingBox().width() / 2.0f);
    float yOffset = scaleY(face.getBoundingBox().height() / 2.0f);
    float left = x - xOffset;
    float top = y - yOffset;
    float right = x + xOffset;
    float bottom = y + yOffset;
    canvas.drawRect(left, top, right, bottom, boxPaint);
  }


}
