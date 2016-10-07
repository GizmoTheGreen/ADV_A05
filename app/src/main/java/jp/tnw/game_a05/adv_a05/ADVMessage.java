package jp.tnw.game_a05.adv_a05;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * 文字処理
 */
public class ADVMessage {

    private final double FrameTime = 1.0 / 60.0;        //fps=1/60[sec]
    final int moji_MAX = 32;
    public Bitmap FRAME, DARK;
    private int OK_flag;
    private int finished = 0;
    int cnt = 0;
    int ctr=0;
    int G_ctr=0;
    double timer = 0, timeout = 0;



    ADVMessage_Data data;

    String[] Text1 = {"", "", "", ""};
    String newString = "";
    char[][] mj=new char[4][moji_MAX];

    ADVMessage(ADVMessage_Data MD) {
        this.data=MD;
        mj_clear();
        clear();
    }

    public void loadImage(Resources res) {

        BitmapFactory.Options opt = new BitmapFactory.Options();//ﾌﾗｸﾞを新規で作成
        opt.inScaled = false;//ﾉｰﾏﾙで読み込むために
        FRAME = BitmapFactory.decodeResource(res, R.drawable.waku, opt);//実際のファイル読み込み
        DARK = BitmapFactory.decodeResource(res, R.drawable.waku2, opt);//実際のファイル読み込み

    }


    public void mj_clear(){
        for(int i=0;i<moji_MAX;i++){
            mj[0][i]='　';
            mj[1][i]='　';
            mj[2][i]='　';
            mj[3][i]='　';
        }
    }


    public void clear() {
        Text1[0] = "";
        Text1[1] = "";
        Text1[2] = "";
        Text1[3] = "";
        cnt = 0;
        finished = 0;
    }

    public void newText(String newText) {
        if (newString.equals(newText)) {

        } else {
            if (cnt <= 3) {
                //Text1[cnt] = newText;
                newString = newText;
                timer = 0;
                finished = 0;
                //cnt++;
            } else {
                Text1[0] = Text1[1];
                Text1[1] = Text1[2];
                Text1[2] = Text1[3];
                Text1[3] = "";
                timer = 0;
                finished = 0;
                newString = newText;
            }
        }
    }

    public void SP_code(char MD2){
        switch(MD2){
            case 'D':{
                ctr=0;
                G_ctr++;
                mj_clear();
                break;
            }
            case 'E':{
                G_ctr=0;
                ctr=0;
                mj_clear();
                break;
            }
        }

    }

    public void update2(){

        char MD=data.Bun_01[G_ctr].charAt(ctr);
        char MD2=data.Bun_01[G_ctr].charAt(ctr+1);

        if(MD=='@' && (MD2>='A' && MD2<='Z')){

            SP_code(MD2);


        }else{
            mj[0][ctr]=data.Bun_01[G_ctr].charAt(ctr);
            ctr++;
        }
    }

    public boolean update() {
        if (cnt <= 3) {
            if (Text1[cnt].length() == newString.length()) {
                return false;
            }
        } else {
            if (Text1[3].length() == newString.length()) {
                return false;
            }
        }


        if (newString.equals("")) {
            return false;
        }

        if (cnt <= 3) {
            if (timer + FrameTime * 33 <= newString.length() + 1) {
                if ((int) timer + 1 <= (timer += FrameTime * 33)) {
                    Text1[cnt] += newString.charAt((int) timer - 1);
                }
                if (Text1[cnt].length() == newString.length() && finished == 0) {
                    cnt++;
                    finished = 1;
                }
                //timer+=FrameTime;
                return true;
            }

        } else {
            if (Text1[3].length() == newString.length()) {
                return false;
            } else {
                if (timer + FrameTime * 33 <= newString.length() + 1) {
                    if ((int) timer + 1 <= (timer += FrameTime * 33)) {
                        Text1[3] += newString.charAt((int) timer - 1);
                    }
                    //timer+=FrameTime;
                    return true;
                }
            }
        }
        return false;
    }


    public void drawImage(Canvas c, double Xx, double Yy) {

        Paint p = new Paint();

        if (Xx != 0 && OK_flag == 0) {//拡大縮小は1回だけ

            OK_flag = 1;
            FRAME = Bitmap.createScaledBitmap(FRAME, (int) (Xx * 960), (int) (Yy * 175), true);//BG
            DARK = Bitmap.createScaledBitmap(DARK, (int) (Xx * 960), (int) (Yy * 175), true);//BG

        }
        if (OK_flag == 1) {//拡大準備が終わったら表示!!

            c.drawBitmap(DARK, 0, (int) ((540 - 175) * Xx), p);
            c.drawBitmap(FRAME, 0, (int) ((540 - 175) * Yy), p);

            p.setTextSize(32 * (float) Xx);
            p.setColor(Color.WHITE);
            c.drawText(Text1[0], (int) ((32 * 1) * Xx), (int) ((540 - 175 + 50) * Yy), p);
            c.drawText(Text1[1], (int) ((32 * 1) * Xx), (int) ((540 - 175 + 50 + 32 * 1) * Yy), p);
            c.drawText(Text1[2], (int) ((32 * 1) * Xx), (int) ((540 - 175 + 50 + 32 * 2) * Yy), p);
            c.drawText(Text1[3], (int) ((32 * 1) * Xx), (int) ((540 - 175 + 50 + 32 * 3) * Yy), p);
        }
    }

    public void drawImage2(Canvas c, double Xx, double Yy) {

        Paint p = new Paint();

        if (Xx != 0 && OK_flag == 0) {//拡大縮小は1回だけ

            OK_flag = 1;
            FRAME = Bitmap.createScaledBitmap(FRAME, (int) (Xx * 960), (int) (Yy * 175), true);//BG
            DARK = Bitmap.createScaledBitmap(DARK, (int) (Xx * 960), (int) (Yy * 175), true);//BG

        }
        if (OK_flag == 1) {//拡大準備が終わったら表示!!

            c.drawBitmap(DARK, 0, (int) ((540 - 175) * Xx), p);
            c.drawBitmap(FRAME, 0, (int) ((540 - 175) * Yy), p);

            p.setTextSize(32 * (float) Xx);
            p.setColor(Color.WHITE);
            c.drawText(String.valueOf(mj[0]), (int) ((32 * 1) * Xx), (int) ((540 - 175 + 50) * Yy), p);
            c.drawText(String.valueOf(mj[1]), (int) ((32 * 1) * Xx), (int) ((540 - 175 + 50 + 32 * 1) * Yy), p);
            c.drawText(String.valueOf(mj[2]), (int) ((32 * 1) * Xx), (int) ((540 - 175 + 50 + 32 * 2) * Yy), p);
            c.drawText(String.valueOf(mj[3]), (int) ((32 * 1) * Xx), (int) ((540 - 175 + 50 + 32 * 3) * Yy), p);
        }
    }

}//class end
