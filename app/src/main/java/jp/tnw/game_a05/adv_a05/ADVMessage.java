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
    public boolean wait=false;

    int ctr = 0;
    int G_ctr = 0;

    int S_ctr;//文字を表示するカウンター
    int GS_ctr;//行を表示する　0~3

    int flag;//0:流す　1:止まる

    ADVMessage_Data data;

    char[][] mj = new char[4][moji_MAX];

    ADVMessage(ADVMessage_Data MD) {
        this.data = MD;
        mj_clear();
    }

    public void loadImage(Resources res) {

        BitmapFactory.Options opt = new BitmapFactory.Options();//ﾌﾗｸﾞを新規で作成
        opt.inScaled = false;//ﾉｰﾏﾙで読み込むために
        FRAME = BitmapFactory.decodeResource(res, R.drawable.waku, opt);//実際のファイル読み込み
        DARK = BitmapFactory.decodeResource(res, R.drawable.waku2, opt);//実際のファイル読み込み

    }

    public void mj_clear() {
        for (int i = 0; i < moji_MAX; i++) {
            mj[0][i] = '　';
            mj[1][i] = '　';
            mj[2][i] = '　';
            mj[3][i] = '　';
        }
    }

    public void SP_code(char MD2) {
        switch (MD2) {
            case 'E': {
                ctr = 0;
                G_ctr = 0;
                GS_ctr=0;
                flag=1;
                break;
            }
            case 'D': {
                ctr = 0;
                S_ctr = 0;
                G_ctr++;
                GS_ctr++;
                if (GS_ctr > 3) {
                    /*for (int i = 0; i < moji_MAX; i++) {
                        mj[0][i] = mj[1][i];
                        mj[1][i] = mj[2][i];
                        mj[2][i] = mj[3][i];
                        mj[3][i] = '　';
                    }*/
                    GS_ctr = 0;
                    flag=1;
                }
                break;
            }
        }
    }

    public void update2() {

        if(flag==1) return;//表示が止まる

        char MD = data.Bun_01[G_ctr].charAt(ctr);
        char MD2 = data.Bun_01[G_ctr].charAt(ctr + 1);

        if (MD == '@' && (MD2 >= 'A' && MD2 <= 'Z')) {
            SP_code(MD2);
        } else {
            mj[GS_ctr][S_ctr] = data.Bun_01[G_ctr].charAt(ctr);
            ctr++;
            S_ctr++;
        }
    }

    public void drawImage2(Canvas c, double Xx, double Yy) {

        Paint p = new Paint();

        if (Xx != 0 && OK_flag == 0) {//拡大縮小は1回だけ

            OK_flag = 1;
            DARK = Bitmap.createScaledBitmap(DARK, (int) (Xx * 960), (int) (Yy * 175), true);//BG
            FRAME = Bitmap.createScaledBitmap(FRAME, (int) (Xx * 960), (int) (Yy * 175), true);//BG

        }
        if (OK_flag == 1) {//拡大準備が終わったら表示!!
            p.setAlpha(128);//アルファ値　0～２５５
            c.drawBitmap(DARK, 0, (int) ((540 - 175) * Xx), p);
            p.setAlpha(255);
            c.drawBitmap(FRAME, 0, (int) ((540 - 175) * Yy), p);
        }
        p.setTextSize(32 * (float) Xx);
        p.setColor(Color.WHITE);
        c.drawText(String.valueOf(mj[0]), (int) ((32 * 1) * Xx), (int) ((540 - 175 + 50) * Yy), p);
        c.drawText(String.valueOf(mj[1]), (int) ((32 * 1) * Xx), (int) ((540 - 175 + 50 + 32 * 1) * Yy), p);
        c.drawText(String.valueOf(mj[2]), (int) ((32 * 1) * Xx), (int) ((540 - 175 + 50 + 32 * 2) * Yy), p);
        c.drawText(String.valueOf(mj[3]), (int) ((32 * 1) * Xx), (int) ((540 - 175 + 50 + 32 * 3) * Yy), p);//
    }

}//class end
