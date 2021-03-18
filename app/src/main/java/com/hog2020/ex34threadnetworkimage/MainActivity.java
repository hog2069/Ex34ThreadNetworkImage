package com.hog2020.ex34threadnetworkimage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv=findViewById(R.id.iv);
    }

    public void clickBtn(View view) {

        //MainThread 는 Network 작업 불가
        //네트워크 작업은 반드시 허가(permission)를 받아야된다-AndroidManifest.xml
        Thread t =new Thread(){
            @Override
            public void run() {
                //Network 에 있는 이미지를 읽어와서
                //이미지 뷰에 설정

                String imgUri="https://i.pinimg.com/originals/83/98/84/8398845588570d82f37fd4d2031c00cc.jpg";

                //서버 주소까지 연결되는 무지개로드(Stream) 열기
                try {
                    //객체 생성
                    URL url = new URL(imgUri);
                    InputStream is= url.openStream();

                    //Stream 을 통해 읽어들인 파일 데이터를
                    //이미지를 가지는 객체
                    final Bitmap bm= BitmapFactory.decodeStream(is);

                    //별도의 Thread 는 UI 변경 작업 불가
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            iv.setImageBitmap(bm);
                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }
}