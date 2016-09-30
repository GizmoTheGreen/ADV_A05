package jp.tnw.game_a05.adv_a05;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by game01 on 2016/09/09.
 */

//表示クラスの元プログラム

public class MyView2 extends SurfaceView implements SurfaceHolder.Callback, Runnable {


    private Thread thread;                              //スレッド
    private SurfaceHolder holder;                       //情報の元
    Context context;                                    //Android情報を共有
    private boolean mLoop;                              //メイン処理とｲﾍﾞﾝﾄ同期ﾌﾗｸﾞ
    private final Object mLock = new Object();          //初期化

    private final double FrameTime = 1.0 / 60.0;        //fps=1/60[sec]
    public double Xx, Yy;                               //画面比率保存
    private double screenWidth, screenHeight;           //ﾃﾞﾊﾞｲｽの縦横ｻｲｽﾞ保存
    double Mx, My;

    public Bitmap BG_PNG;//画像を読み込むID
    int OK_flag;

    //◆インスタンス実体化
    //Haikei bg = new Haikei();
    //Buta pig = new Buta();
    //Chara chara = new Chara();
    //Enter player = new Enter();
    //Enter hamu = new Enter();
    //Zako zako1 = new Zako();

    //◆Constructor

    public MyView2(Context context) {
        super(context);
        this.context = context; //Context localize
        holder = getHolder();
        holder.addCallback(this);
        holder.setFixedSize(getWidth(), getHeight());

        Resources res = this.context.getResources();//ｺﾝﾃｷｽﾄからﾘｰｿｰｽ情報をGet
//        BitmapFactory.Options opt = new BitmapFactory.Options();//ﾌﾗｸﾞを新規で作成
//        opt.inScaled = false;//ﾉｰﾏﾙで読み込むために
//        BG_PNG = BitmapFactory.decodeResource(res, R.drawable.bg_01, opt);//実際のファイル読み込み
        //bg.loadImage(res);
        //pig.loadImage(res);
        //chara.loadImage(res);
        //player.loadImage(res);
        //hamu.loadImage(res);
        //zako1.loadImage(res);
    }


    private void doUpdate() {
        Resources res = context.getResources();
        synchronized (mLock) {//メイン処理とｲﾍﾞﾝﾄの同期
            My += FrameTime * 100;

            //player.update();
            //hamu.update();
            //zako1.update();
        }
    }

    private void doDraw(SurfaceHolder holder) {
        Canvas c = holder.lockCanvas();//----------------------------------------------------------

        if (c != null) {
            c.drawColor(Color.LTGRAY);
            Paint p = new Paint();
            //bg.drawImage(c, Xx, Yy);
            //pig.drawImage(c, screenWidth, screenHeight, Xx, Yy);
            //chara.drawImage(c, screenWidth, screenHeight, Xx, Yy);
            //player.drawImage(c,screenWidth,screenHeight,Xx,Yy);
            //hamu.drawImage(c,screenWidth,screenHeight,Xx,Yy);
            //zako1.drawImage(c,screenWidth,screenHeight,Xx,Yy);
//            if (Xx != 0 && OK_flag == 0) {//拡大縮小は1回だけ
//
//                OK_flag = 1;
//                BG_PNG = Bitmap.createScaledBitmap(BG_PNG, (int) (Xx * 540), (int) (Yy * 960), true);//BG
//
//            }
//            if (OK_flag == 1) {//拡大準備が終わったら表示!!
//
//                c.drawBitmap(BG_PNG, 0, 0, p);
//
//            }

            p.setTextSize(32 * (float) Xx);
            p.setColor(Color.GREEN);
            //if(st_fl==1)
            c.drawText("A05_モーテンソン　エミル", (int) (450 * Xx), (int) (350 * Yy), p);
            c.drawText("横幅 = " + screenWidth, 10, 300, p);
            c.drawText("縦幅 = " + screenHeight, 10, 350, p);
            c.drawText("縦比率 = " + Xx, 10, 400, p);
            c.drawText("縦比率 = " + Yy, 10, 450, p);

            holder.unlockCanvasAndPost(c);//-------------------------------------------------------
        }
    }


    //◆画面を生成したときに動く

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    //◆画面が切り替わったときに動く、縦画面から横画面に切り替わったときなど

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        screenWidth = width;
        screenHeight = height;
        Xx = screenWidth / 960f;
        Yy = screenHeight / 540f;
        thread = new Thread(this);
        thread.start();
    }


    //◆画面を廃棄したときに動く

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread = null;
    }


    //◆◆◆無限ループ　主循環◆◆◆
    @Override
    public void run() {
        while (thread != null) {
            doUpdate();
            doDraw(holder);
        }
    }
}
