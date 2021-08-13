package com.zdy.xiangxue.lesson_rxJava;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import com.jakewharton.rxbinding2.view.RxView;
import com.zdy.xiangxue.R;
import com.zdy.xiangxue.lesson_rxJava.api.WanAndroidApi;
import com.zdy.xiangxue.lesson_rxJava.bean.ProjectList;
import com.zdy.xiangxue.lesson_rxJava.bean.ProjectTree;
import com.zdy.xiangxue.lesson_rxJava.retrofit_okhttp.HttpUtil;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 *
 * TODO
 *  Retrofit + RxJava
 *  需求：
 *  1.请求服务器注册操作
 *  2.注册完成之后，更新注册UI
 *  3.马上去登录服务器操作
 *  4.登录完成之后，更新登录的UI
 *
 *  wy.RxJava配合Retrofit。
 *  RxJava + Retrofit （请求网络OkHttp  ---- Retorfit  --- Observable）
 *
 *  1.OkHttp 请求网络 （Retorfit）
 *  2.Retorfit 返回一个结果 （Retorfit） --- Observable
 *  * 3.最终的结果 是RxJava中的 被观察者 上游 Observable
 *  * 4.一行代码写完需求流程： 从上往下
 *  *    1.请求服务器，执行注册操作（耗时）切换异步线程
 *  *    2.更新注册后的所有 注册相关UI - main  切换主线程
 *  *    3.请求服务器，执行登录操作（耗时）切换异步线程
 *  *    4.更新登录后的所有 登录相关UI - main  切换主线程
 *  *
 *  * 5.看RxJava另外一种的执行流程
 *  *   初始点 开始点 订阅
 *  *   1.onSubscribe
 *  *   2.registerAction(new RegisterRequest())
 *  *   3..doOnNext 更新注册后的 所有UI
 *  *   4.flatMap执行登录的耗时操作
 *  *   5.订阅的观察者 下游 onNext 方法，更新所有登录后的UI
 *  *   6.progressDialog.dismiss()
 *
 */
public class RxJavaActivity extends AppCompatActivity {

    private static final String TAG = "RxJavaActivity";
    private static final String PATH = "http://pic1.win4000.com/wallpaper/c/53cdd1f7c1f21.jpg";

    private AppCompatButton rxBtn,rxBtnTree,rxBtnList;
    private ImageView rxImageView;
    private ProgressDialog progressDialog;
    private Paint paint = new Paint();
    private WanAndroidApi wanAndroidApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);

        wanAndroidApi = HttpUtil.getOkHttpClient().create(WanAndroidApi.class);

        rxBtn = (AppCompatButton) findViewById(R.id.rx_load_btn);
        rxBtnTree = (AppCompatButton) findViewById(R.id.rx_load_tree);
        rxBtnList = (AppCompatButton) findViewById(R.id.rx_load_list);
//        rxBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loadImageByRxJava();
//            }
//        });

        rxImageView = (ImageView) findViewById(R.id.rx_load_image);

        /**
         * 防抖
         */
        RxView.clicks(rxBtn)
                .throttleFirst(3000, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        loadImageByRxJava();
                    }
                });

        RxView.clicks(rxBtnTree)
                .throttleFirst(3000, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        LoadTree();
                    }
                });

        RxView.clicks(rxBtnList)
                .throttleFirst(3000, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        LoadList();
                    }
                });
    }


    /**
     * 加载图片+打印日志+打印水印
     */
    private void loadImageByRxJava() {
        Observable.just(PATH)
                .map(new Function<String, Bitmap>() {
                    @Override
                    public Bitmap apply(String s) throws Exception {
                        URL url = new URL(PATH);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setConnectTimeout(5000);
                        int responseCode = httpURLConnection.getResponseCode();
                        if (responseCode == HttpURLConnection.HTTP_OK) {//连接OK
                            InputStream inputStream = httpURLConnection.getInputStream();
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            return bitmap;
                        }
                        return null;
                    }
                })
                .map(new Function<Bitmap, Bitmap>() {
                    @Override
                    public Bitmap apply(Bitmap bitmap) throws Exception {
                        Log.i(TAG, "apply: 开始下载图片");
                        return bitmap;
                    }
                })

                .flatMap(new Function<Bitmap, ObservableSource<Bitmap>>() {
                    @Override
                    public ObservableSource<Bitmap> apply(Bitmap bitmap) throws Exception {
                        return Observable.just(bitmap);
                    }
                })

                .flatMap(new Function<Bitmap, ObservableSource<Bitmap>>() {
                    @Override
                    public ObservableSource<Bitmap> apply(Bitmap bitmap) throws Exception {
                        paint.setColor(Color.WHITE);
                        paint.setTextSize(88);
                        Bitmap drawBitmap = drawTextToBitmap(bitmap,"朱冬勇大帅哥",paint,100,100);
                        return Observable.just(drawBitmap);
                    }
                })

//                .map(new Function<Bitmap, Bitmap>() {
//                    @Override
//                    public Bitmap apply(Bitmap bitmap) throws Exception {
//                        paint.setColor(Color.WHITE);
//                        paint.setTextSize(88);
//                        Bitmap drawBitmap = drawTextToBitmap(bitmap,"朱冬勇大帅哥",paint,100,100);
//                        return drawBitmap;
//                    }
//                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bitmap>() {//订阅观察者

                    // 订阅开始
                    @Override
                    public void onSubscribe(Disposable d) {
                        progressDialog = new ProgressDialog(RxJavaActivity.this);
                        progressDialog.setMessage("正在加载图片");
                        progressDialog.show();
                    }

                    // 拿到事件
                    @Override
                    public void onNext(Bitmap bitmap) {
                        rxImageView.setImageBitmap(bitmap);
                    }

                    // 错误事件
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    //结束
                    @Override
                    public void onComplete() {
                        if (null != progressDialog) {
                            progressDialog.dismiss();
                        }
                    }
                });

    }


    /**
     * 加载tree
     */
    private void LoadTree(){
        wanAndroidApi.getProjectTree()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ProjectTree>() {
                    @Override
                    public void accept(ProjectTree projectTree) {
                        Log.i(TAG, "accept: "+projectTree.getData());
                    }
                });
    }

    /**
     * 加载list
     */
    private void LoadList(){
        wanAndroidApi.getProjectList(1,367)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ProjectList>() {
                    @Override
                    public void accept(ProjectList projectList) {
                        Log.i(TAG, "accept: "+projectList.getData());
                    }
                });
    }

    /**
     * 封装我们的操作
     * upstream   上游
     * d
     */
    public final static <UD> ObservableTransformer<UD, UD> rxud() {
        return new ObservableTransformer<UD, UD>() {
            @Override
            public ObservableSource<UD> apply(Observable<UD> upstream) {
                return upstream.subscribeOn(Schedulers.io())     // 给上面代码分配异步线程
                        .observeOn(AndroidSchedulers.mainThread()) // 给下面代码分配主线程;
                        .map(new Function<UD, UD>() {
                            @Override
                            public UD apply(UD ud) throws Exception {
                                Log.d(TAG, "apply: 我监听到你了，居然再执行");
                                return ud;
                            }
                        });
                // .....        ;
            }
        };
    }

    // 图片上绘制文字 加水印
    private final Bitmap drawTextToBitmap(Bitmap bitmap, String text, Paint paint, int paddingLeft, int paddingTop) {
        Bitmap.Config bitmapConfig = bitmap.getConfig();

        paint.setDither(true); // 获取跟清晰的图像采样
        paint.setFilterBitmap(true);// 过滤一些
        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);

        canvas.drawText(text, paddingLeft, paddingTop, paint);
        return bitmap;
    }

}