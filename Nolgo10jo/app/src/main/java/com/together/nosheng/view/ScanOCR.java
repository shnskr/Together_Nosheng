package com.together.nosheng.view;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.together.nosheng.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ScanOCR extends AppCompatActivity {
    private boolean ProgressFlag = false; // 프로그레스바 상태 플래그
    private ProgressCircleDialog m_objProgressCircle; // 원형 프로그레스바
    private Context mContext;
    private TessBaseAPI m_Tess; //Tess API reference
    public final int RESULT_OCR = 1;
    private TextView m_ocrTextView; // 결과 변환 텍스트
    private MessageHandler m_messageHandler;
    private String mDataPath = ""; //언어데이터가 있는 경로
    private final String[] mLanguageList = {"eng","kor"}; // 언어
    private long m_start; // 처리시간 시작지점
    private long m_end; //처리시간 끝지점
    private TextView m_tvTime; // 처리시간 표시 텍스트

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_scan);
        m_ocrTextView = findViewById(R.id.tv_view);
        mContext = this;
        m_messageHandler = new MessageHandler();
        m_tvTime = findViewById(R.id.tv_time);

        Tesseract();

    }

    public void onSelectImageClick(View view) {
        CropImage.activity(null).setGuidelines(CropImageView.Guidelines.ON).start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of CropImageActivity
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                ((ImageView) findViewById(R.id.quick_start_cropped_image)).setImageURI(result.getUri());

                //creating bitmap
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.getUri());
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                OCRThread ocrThread = new OCRThread(bitmap);

                ocrThread.setDaemon(true);
                ocrThread.start();
                m_ocrTextView.setText(getResources().getString(R.string.LoadingMessage));
                Toast.makeText(
                        this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG)
                        .show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    //region Thread
    public class OCRThread extends Thread
    {
        private Bitmap rotatedImage;
        OCRThread(Bitmap rotatedImage)
        {
            this.rotatedImage = rotatedImage;
            if(!ProgressFlag)
                m_objProgressCircle = ProgressCircleDialog.show(mContext, "", "", true);
            ProgressFlag = true;
        }

        @Override
        public void run() {
            super.run();
            // 사진의 글자를 인식해서 옮긴다
            String OCRresult = null;

            m_Tess.setImage(rotatedImage);
            OCRresult = m_Tess.getUTF8Text();

            Message message = Message.obtain();
            message.what = RESULT_OCR;
            message.obj = OCRresult;

            if(message != null){
                Log.i("testing","할 수 있드아..");
                m_messageHandler.sendMessage(message);
            }
            try {
                Thread.sleep(10000000); //인식 중 너무 오래 걸릴 시 강제 종료)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
    //endregion
    //region Handler
    public class MessageHandler extends Handler
    {
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what)
            {
                case RESULT_OCR:
                    TextView OCRTextView = findViewById(R.id.tv_view);
                    OCRTextView.setText(String.valueOf(msg.obj)); //텍스트 변경 이게 우리가 지금 필요한 내용
                    OCRTextView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction()==MotionEvent.ACTION_DOWN){
                                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                                ClipData clipData = ClipData.newPlainText("Id", OCRTextView.getText());
                                clipboardManager.setPrimaryClip(clipData);

                                Toast.makeText(ScanOCR.this, "클립 보드에 복사되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                            return true;
                        }
                    });

                    // 원형 프로그레스바 종료
                    if(m_objProgressCircle.isShowing() && m_objProgressCircle !=null)
                        m_objProgressCircle.dismiss();
                    ProgressFlag = false;

                    //처리 시간 관련 메세지
//                    m_end = System.currentTimeMillis();
//                    long time = (m_end - m_start)/1000;
                    m_tvTime.setText("처리시간 : 안알랴줌");
                    Toast.makeText(mContext,getResources().getString(R.string.CompleteMessage),Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
    //endregion
    public void Tesseract() {
        //언어파일 경로
        mDataPath = getFilesDir() + "/tesseract/";

        //트레이닝데이터가 카피되어 있는지 체크
        String lang = "";
        for (String Language : mLanguageList) {
            checkFile(new File(mDataPath + "tessdata/"), Language);
            lang += Language + "+";
        }
        m_Tess = new TessBaseAPI();
        m_Tess.init(mDataPath, lang);
    }

    //check file on the device
    private void checkFile(File dir, String Language) {
        //디렉토리가 없으면 디렉토리를 만들고 그후에 파일을 카피
        if (!dir.exists() && dir.mkdirs()) {
            copyFiles(Language);
        }
        //디렉토리가 있지만 파일이 없으면 파일카피 진행
        if (dir.exists()) {
            String datafilepath = mDataPath + "tessdata/" + Language + ".traineddata";
            File datafile = new File(datafilepath);
            if (!datafile.exists()) {
                copyFiles(Language);
            }
        }
    }

    //copy file to device
    private void copyFiles(String Language) {
        try {
            String filepath = mDataPath + "/tessdata/" + Language + ".traineddata";
            AssetManager assetManager = getAssets();
            InputStream instream = assetManager.open("tessdata/"+Language+".traineddata");
            OutputStream outstream = new FileOutputStream(filepath);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = instream.read(buffer)) != -1) {
                outstream.write(buffer, 0, read);
            }
            outstream.flush();
            outstream.close();
            instream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
