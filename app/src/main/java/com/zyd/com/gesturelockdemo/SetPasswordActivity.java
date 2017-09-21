package com.zyd.com.gesturelockdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

/**
 * Created by 张玉栋 on 2017/9/19.
 */

public class SetPasswordActivity extends Activity implements View.OnClickListener{


    private GestureLockViewGroup group;
    private Button mRedraw;
    private Button mConfirm;

    private static final int RESET = 0;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case RESET:
                    group.reset();
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        group = findViewById(R.id.set_password);
        mRedraw = findViewById(R.id.redraw);
        mRedraw.setOnClickListener(this);
        mConfirm = findViewById(R.id.confim);

        group.setAnswer(new int[] { 1, 2, 3, 4,5 });
        group.setOnGestureLockViewListener(new GestureLockViewGroup.OnGestureLockViewListener()
                {

                    @Override
                    public void onUnmatchedExceedBoundary()
                    {
                    }

                    @Override
                    public void onGestureEvent(List<Integer> list, boolean matched)
                    {
                        if(list.size() < 4){
                            Toast.makeText(getApplicationContext(), "密码不能少于4", Toast.LENGTH_SHORT).show();
                            handler.sendEmptyMessageAtTime(RESET, 3000);
                        }
                    }

                    @Override
                    public void onBlockSelected(int cId)
                    {
                    }
                });
        group.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
         switch (view.getId()){
             case R.id.redraw:
                 group.reset();
                 break;
         }
    }
}
