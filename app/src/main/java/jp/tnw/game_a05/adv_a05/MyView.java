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
    int cnt = 0;
    boolean busy;

    //◆インスタンス実体化
    ADVBackground BG = new ADVBackground();
    ADVMessage Message = new ADVMessage(new ADVMessage_Data());
    Enter Arrow = new Enter();

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
            Message.newText(Message.data.Bun_01[cnt]);
            //busy = Message.update();
            Message.update2();
            if (!busy)
                Arrow.update();

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchX = (int) event.getX();
        touchY = (int) event.getY();
        touchEvent = (int) event.getAction();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                if (!busy && cnt < Message.data.Bun_01.length - 1) cnt++;
                break;
            //default:                   touchEvent=0;
        }
        return false;
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

            Message.drawImage(c, Xx, Yy);
            if (!busy)
                Arrow.drawImage(c, screenWidth, screenHeight, Xx, Yy);

            int touchOld = touchEvent;
            //Message.drawImage(c, Xx, Yy, Message.data.Bun_01[0]);
            //if(st_fl==1)
            c.drawText("A05_エミル", (int) ((960 - 28 * 6) * Xx), (int) (28 * 1 * Yy), p);
            /*c.drawText(Message.newString + " " + Message.newString.length(), (int) ((0 + 28 * 1) * Xx), (int) (28 * 1 * Yy), p);
            if (Message.cnt<=3)
                c.drawText(Message.Text1[Message.cnt] + " " + Message.Text1[Message.cnt].length(), (int) ((0 + 28 * 1) * Xx), (int) (28 * 2 * Yy), p);
            else
                c.drawText(Message.Text1[3] + " " + Message.Text1[3].length(), (int) ((0 + 28 * 1) * Xx), (int) (28 * 2 * Yy), p);
            */
            c.drawText("X= " + screenWidth, (int) ((960 - 28 * 6) * Xx), (int) (28 * 2 * Yy), p);
            c.drawText("Y= " + screenHeight, (int) ((960 - 28 * 6) * Xx), (int) (28 * 3 * Yy), p);

            Message.drawImage2(c,Xx,Yy);

            /*c.drawText("tchCnt= " + cnt, (int) ((960 - 28 * 6) * Xx), (int) (28 * 4 * Yy), p);
            c.drawText("typeCnt= " + Message.cnt, (int) ((960 - 28 * 6) * Xx), (int) (28 * 5 * Yy), p);
            c.drawText("timer= " + (int)Message.timer, (int) ((960 - 28 * 6) * Xx), (int) (28 * 6 * Yy), p);
            c.drawText("length= " + Message.newString.length(), (int) ((960 - 28 * 6) * Xx), (int) (28 * 7 * Yy), p);
            */
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