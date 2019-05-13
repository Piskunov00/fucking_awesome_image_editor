package com.example.image_editor;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

public class Segmentation extends Conductor{

    ImageView imageView;
    MainActivity activity;
    Bitmap bitmap;
    private FaceDetector detector;
    Bitmap editedBitmap;
    private String TAG = "Detect Faces";

    Segmentation(MainActivity activity) {
        super(activity);
        // work only with activity_main.xml
        this.activity = activity;
        this.imageView = activity.getImageView();
        this.bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        imageView.setImageBitmap(bitmap);
    }

    void touchToolbar() {
        super.touchToolbar();
        PrepareToRun(R.layout.segmentatioon_menu);

        ConfigBtnFacesDetect((Button)activity.findViewById(R.id.faces));

        detector = new FaceDetector.Builder(activity)
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();

    }

    private void ConfigBtnFacesDetect(Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    scanFaces();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i(TAG, "Problem");
                }
            }
        });
    }

    private void scanFaces() {
        if (detector.isOperational() && bitmap != null) {
            editedBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                    .getHeight(), bitmap.getConfig());
            float scale = activity.getResources().getDisplayMetrics().density;
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.rgb(255, 61, 61));
            paint.setTextSize((int) (14 * scale));
            paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(3f);
            Canvas canvas = new Canvas(editedBitmap);
            canvas.drawBitmap(bitmap, 0, 0, paint);
            Frame frame = new Frame.Builder().setBitmap(editedBitmap).build();
            SparseArray<Face> faces = detector.detect(frame);
            for (int index = 0; index < faces.size(); ++index) {
                Face face = faces.valueAt(index);
                canvas.drawRect(
                        face.getPosition().x,
                        face.getPosition().y,
                        face.getPosition().x + face.getWidth(),
                        face.getPosition().y + face.getHeight(), paint);
//                Log.i(TAG, "Face " + (index + 1));
//                Log.i(TAG, "Smile probability:");
//                Log.i(TAG, String.valueOf(face.getIsSmilingProbability()));
//                Log.i(TAG, "Left Eye Open Probability: ");
//                Log.i(TAG, String.valueOf(face.getIsLeftEyeOpenProbability()));
//                Log.i(TAG, "Right Eye Open Probability: ");
//                Log.i(TAG, String.valueOf(face.getIsRightEyeOpenProbability()));
//                Log.i(TAG, "---------");
//
//                for (Landmark landmark : face.getLandmarks()) {
//                    int cx = (int) (landmark.getPosition().x);
//                    int cy = (int) (landmark.getPosition().y);
//                    canvas.drawCircle(cx, cy, 5, paint);
//                }
            }

            if (faces.size() == 0) {
                Log.i(TAG, "Scan Failed: Found nothing to scan");
            } else {
                imageView.setImageBitmap(editedBitmap);
                Log.i(TAG, "No of Faces Detected: ");
                Log.i(TAG, String.valueOf(faces.size()));
                Log.i(TAG, "---------");
            }
        } else {
            Log.i(TAG, "Could not set up the detector!");
        }
    }

}
