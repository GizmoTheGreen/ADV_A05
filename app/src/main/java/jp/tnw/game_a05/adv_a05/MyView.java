package jp.tnw.game_a05.adv_a05;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * 表示Main
 */


//-------------------------
//表示メイン
//-------------------------
public class MyView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private final double FrameTime = 1.0 / 60.0;//fps=1/60[sec]
    public Bitmap BG_PNG, BG2_PNG;//画像を読み込むID
    private Thread thread;//スレッド
    private SurfaceHolder holder;//情報の元
    Context context;//Android情報を共有
    public double Xx, Yy;//画面比率保存
    public int touchX, touchY, touchEvent;
    private double screenWidth, screenHeight;//ﾃﾞﾊﾞｲｽの縦横ｻｲｽﾞ保存
    private boolean mLoop;//メイン処理とｲﾍﾞﾝﾄ同期ﾌﾗｸﾞ
    private final Object mLock = new Object();//初期化
    //double Mx, My;

    //◆インスタンス実体化
    ADVBackground BG = new ADVBackground();
    ADVCharacter  CG = new ADVCharacter();
    ADVMessage Message = new ADVMessage(new ADVMessage_Data(), BG, CG);
    Cursor Arrow = new Cursor();

    //---------------------------
    //	ｺﾝｽﾄﾗｸﾀ
    //---------------------------
    public MyView(Context context) {
        super(context);
        this.context = context;
        holder = getHolder();
        holder.addCallback(this);
        holder.setFixedSize(getWidth(), getHeight());

        Resources res = this.context.getResources();
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inScaled = false;
        //  G_PNG= BitmapFactory.decodeResource(res, R.drawable.waku,opt);//BG
        BG.loadImage(res);
        Message.loadImage(res);
        CG.loadImage(res);
        Arrow.loadImage(res);
        //My=100;
        //Mx=0;
    }

    //---------------------------
    //	メイン処理
    //---------------------------
    private void doUpdate() {
        Resources res = context.getResources();
        synchronized (mLock) {//メイン処理とｲﾍﾞﾝﾄの同期
            //My += FrameTime * 100;
            //Message.newText(Message.data.Bun_01[cnt]);
            //busy = Message.update();
            //BG.update();
            Message.update2();
            Arrow.update();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchX = (int) event.getX();
        touchY = (int) event.getY();
        touchEvent = event.getAction();
        touchEvent=0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if(Message.flag==1) {
                    Message.mj_clear();
                    Message.flag=0;
                }
                touchEvent=1;
                break;
            }
            case MotionEvent.ACTION_MOVE:
                touchEvent=2;
                break;
            case MotionEvent.ACTION_UP:
                //if (Message.wait) Message.wait=false;
                break;
            //default:                   touchEvent=0;
        }
        return true;
    }

    //---------------------------
    //	表示
    //---------------------------
    private void doDraw(SurfaceHolder holder) {
        Canvas c = holder.lockCanvas();//-----------------
        if (c != null) {
            c.drawColor(Color.LTGRAY);

            Paint p = new Paint();
            p.setTextSize(28 * (float) Xx);
            p.setColor(Color.RED);

            BG.drawImage(c, Xx, Yy);
            CG.drawImage(c, Xx, Yy);


            int touchOld = touchEvent;

            c.drawText("A05_エミル", (int) ((960 - 28 * 6) * Xx), (int) (28 * 1 * Yy), p);

            c.drawText("X= " + screenWidth, (int) ((960 - 28 * 6) * Xx), (int) (28 * 2 * Yy), p);
            c.drawText("Y= " + screenHeight, (int) ((960 - 28 * 6) * Xx), (int) (28 * 3 * Yy), p);
            c.drawText("Touch= " + touchEvent, (int) ((960 - 28 * 6) * Xx), (int) (28 * 4 * Yy), p);
            c.drawText("" + touchX + "x"+ touchY, (int) ((960 - 28 * 6) * Xx), (int) (28 * 5 * Yy), p);

            Message.drawImage2(c,Xx,Yy);
            if (Message.flag==1) {
                Arrow.drawImage(c, screenWidth, screenHeight, Xx, Yy);
            }
            holder.unlockCanvasAndPost(c);//-------------------------------
        }
    }


    //---------------------------
    //	生成＆スレッド開始
    //---------------------------
    public void surfaceCreated(SurfaceHolder arg0) {
    }

    //---------------------------
    //	生成＆スレッド開始
    //---------------------------
    public void surfaceChanged(SurfaceHolder holder, int f, int w, int h) {
        screenWidth = w;
        screenHeight = h;
        Xx = screenWidth / 960f;
        Yy = screenHeight / 540f;
        thread = new Thread(this);
        thread.start();
    }

    //---------------------------
    //	破棄 lol comment
    //---------------------------
    public void surfaceDestroyed(SurfaceHolder arg0) {
        thread = null;
    }

    //---------------------------
    //	無限ループ
    //---------------------------
    public void run() {
        while (thread != null) {
            doUpdate();
            doDraw(holder);
        }

    }
}//class end