package com.zdy.xiangxue.lesson_UI;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zdy.xiangxue.R;
import com.zdy.xiangxue.lesson_UI.flowLayout.FlowLayout;
import com.zdy.xiangxue.lesson_UI.flowLayout.TagAdapter;
import com.zdy.xiangxue.lesson_UI.flowLayout.TagFlowLayout;
import com.zdy.xiangxue.lesson_UI.utils.VibratorUtil;
import com.zdy.xiangxue.lesson_UI.widget.SnapDrawable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 创建日期：2020/7/12 on 11:42
 * 描述：游动的锦鲤
 * 作者：zhudongyong
 */
public class FishActivity extends AppCompatActivity {

    private ImageView sweepImage;
    private LinearLayout ll;
    private LayoutInflater layoutInflater;
    private TagFlowLayout tagFlowLayout;
    private List<String> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish);
        sweepImage = findViewById(R.id.sweep_image);
        sweepImage.setImageDrawable(new SnapDrawable());

        VibratorUtil.Vibrate(this,1000L);

//        FlowLayout2 myViewGroup = new FlowLayout2(this);
//        String[] str = new String[]{"1123123", "2123123", "1231233", "4", "1235", "6", "7234", "23458", "9", "10", "11", "12", "13", "14", "15", "16", "134567", "18", "134569", "20", "234561", "223456", "345623", "234564"};
//        for (int i = 0; i < str.length; i++) {
//            Button button = new Button(this);
//            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.height = dp2px(50);
//            params.width = dp2px(50);
//            button.setLayoutParams(params);
//            button.setText(str[i]);
//            button.setTextColor(getResources().getColor(R.color.colorAccent));
//            button.setBackground(getResources().getDrawable(R.drawable.blue_block));
//            myViewGroup.addView(button);
//        }
//        ll = findViewById(R.id.ll);
//        ll.addView(myViewGroup);
        list= new ArrayList<>();
        list.add("新闻");
        list.add("美食");
        list.add("体育");
        list.add("生活号");
        list.add("预留");
        list.add("娱乐");
        list.add("杭州市");
        list.add("太行");
        list.add("舞蹈");
        list.add("直播");
        list.add("新闻");
        list.add("美食");
        list.add("体育");
        list.add("生活号");
        list.add("预留");
        list.add("娱乐");
        list.add("杭州市");
        list.add("太行");
        list.add("舞蹈");
        list.add("直播");
        initView();
    }

    public static int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    private void initView() {
        layoutInflater = LayoutInflater.from(this);
        tagFlowLayout = findViewById(R.id.id_flowlayout);

        tagFlowLayout.setAdapter(new TagAdapter<String>(list) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) layoutInflater.inflate(R.layout.item_floor,
                        tagFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });

        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                //得到点击的值
                Toast.makeText(getApplicationContext(), list.get(position), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        tagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                //得到下标的集合
                Toast.makeText(getApplicationContext(), selectPosSet.toString() + "s", Toast.LENGTH_SHORT).show();
            }
        });


    }
}