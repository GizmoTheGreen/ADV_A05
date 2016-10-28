package jp.tnw.game_a05.adv_a05;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * 背景の処理
 */
public class ADVCharacter {

    final double FrameTime = 1.0 / 60.0;
    public Bitmap[] CHAR_PNG=new Bitmap[11];
    int OK_flag[] = new int[11];
    int CGL=0,CGC=0,CGR=0;
    int NoL=0, NoC=0, NoR=0;
    double timer=5;
    int S_sel=0;

    ADVCharacter() {
        //CGL=1;
        //CGC=9;
        //CGR=5;
    }

    public void loadImage(Resources res) {

        BitmapFactory.Options opt = new BitmapFactory.Options();//ﾌﾗｸﾞを新規で作成
        opt.inScaled = false;//ﾉｰﾏﾙで読み込むために
        CHAR_PNG[0] = BitmapFactory.decodeResource(res, R.drawable.ch01, opt);//実際のファイル読み込み
        CHAR_PNG[1] = BitmapFactory.decodeResource(res, R.drawable.ch01, opt);
        CHAR_PNG[2] = BitmapFactory.decodeResource(res, R.drawable.ch02, opt);
        CHAR_PNG[3] = BitmapFactory.decodeResource(res, R.drawable.ch03, opt);
        CHAR_PNG[4] = BitmapFactory.decodeResource(res, R.drawable.ch04, opt);
        CHAR_PNG[5] = BitmapFactory.decodeResource(res, R.drawable.ch05, opt);
        CHAR_PNG[6] = BitmapFactory.decodeResource(res, R.drawable.ch06, opt);
        CHAR_PNG[7] = BitmapFactory.decodeResource(res, R.drawable.ch07, opt);
        CHAR_PNG[8] = BitmapFactory.decodeResource(res, R.drawable.ch08, opt);
        CHAR_PNG[9] = BitmapFactory.decodeResource(res, R.drawable.ch09, opt);
        CHAR_PNG[10] = BitmapFactory.decodeResource(res, R.drawable.ch10, opt);
    }

    public void drawImage(Canvas c, double Xx, double Yy) {

        Paint p = new Paint();

        if (Xx != 0 && OK_flag[CGL] == 0) {//拡大縮小は1回だけ

            OK_flag[CGL] = 1;
            if(CGL!=6 && CGL!=9)
                CHAR_PNG[CGL] = Bitmap.createScaledBitmap(CHAR_PNG[CGL], 500, 450, true);//BG
            else
                CHAR_PNG[CGL] = Bitmap.createScaledBitmap(CHAR_PNG[CGL], 750, 450, true);//BG

        }
        if (Xx != 0 && OK_flag[CGC] == 0) {//拡大縮小は1回だけ

            OK_flag[CGC] = 1;
            if(CGC!=6 && CGC!=9)
                CHAR_PNG[CGC] = Bitmap.createScaledBitmap(CHAR_PNG[CGC], 500, 450, true);//BG
            else
                CHAR_PNG[CGC] = Bitmap.createScaledBitmap(CHAR_PNG[CGC], 750, 450, true);//BG

        }
        if (Xx != 0 && OK_flag[CGR] == 0) {//拡大縮小は1回だけ

            OK_flag[CGR] = 1;
            if(CGR!=6 && CGR!=9)
                CHAR_PNG[CGR] = Bitmap.createScaledBitmap(CHAR_PNG[CGR], 500, 450, true);//BG
            else
                CHAR_PNG[CGR] = Bitmap.createScaledBitmap(CHAR_PNG[CGR], 750, 450, true);//BG

        }
        
        
        
        
        if (OK_flag[CGL] == 1 && OK_flag[CGC] == 1 && OK_flag[CGR] == 1) {//拡大準備が終わったら表示!!

            //c.drawBitmap(CHAR_PNG[CHAR_sel], 0, 0, p);
            //left
                c.drawBitmap(CHAR_PNG[CGL],
                        new Rect((int) (250*NoL), (int) (0),(int) (250*NoL+250), (int) (450)),
                        new Rect((int) (Xx * 0), (int) (Yy * 0),(int) (Xx * 250), (int) (Yy * 450)),
                        p);
            //middle
                c.drawBitmap(CHAR_PNG[CGC],
                        new Rect((int) (250*NoC), (int) (0),(int) (250*NoC+250), (int) (450)),
                        new Rect((int) (Xx * (960/2 - 250/2)), (int) (Yy * 0),(int) ((960/2-250/2)*Xx+(250*Xx)), (int) (Yy * 450)),
                        p);
            //right
                c.drawBitmap(CHAR_PNG[CGR],
                        new Rect((int) (250*NoR), (int) (0),(int) (250*NoR+250), (int) (450)),
                        new Rect((int) (Xx * (960-250)), (int) (Yy * 0),(int) ((960)*Xx), (int) (Yy * 450)),
                        p);
        }

    }

    public void update() {
    timer-=FrameTime;
        if(timer<=0){
            timer=1;
            //CHAR_sel++;
            //if(CHAR_sel>12){CHAR_sel=1;}
        }
    }
}//class end
