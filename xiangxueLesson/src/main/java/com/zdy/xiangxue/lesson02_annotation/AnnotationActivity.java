package com.zdy.xiangxue.lesson02_annotation;

import android.os.Bundle;
import android.widget.TextView;

import com.zdy.xiangxue.R;

import androidx.fragment.app.FragmentActivity;

public class AnnotationActivity extends FragmentActivity {
    
    @InjectValue()
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_annotation);
        InjectValueUtil.injectView(this);
        TextView textView = (TextView) findViewById(R.id.tv_name);
        textView.setText(name);
        dosth(Status.COMPLETED);

    }

    public void dosth(@Status int status){
        switch (status){
            case Status.COMPLETED:
                break;
        }

    }
}
