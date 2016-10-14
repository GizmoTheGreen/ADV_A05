package jp.tnw.game_a05.adv_a05;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by game01 on 2016/09/16.
 */
public class Cursor {

    private final double FrameTime = 1.0 / 60.0;        //fps=1/60[sec]
    public Bitmap PNG;
    int OK_flag;
    double No = 0;
    int X,Y;
    int flag;


    Cursor() {
        X = 960 - 50; //where on screen
        Y = 540 - 50;
    }

    public void loadImage(Resources res) {
        BitmapFactory.Options opt = new BitmapFactory.Options();//ﾌﾗｸﾞを新規で作成
        opt.inScaled = false;//ﾉｰﾏﾙで読み込むために
        PNG = BitmapFactory.decodeResource(res, R.drawable.enter, opt);//実際のファイル読み込み
    }

    public void drawImage(Canvas c, double screenWidth, double screenHeight, double Xx, double Yy) {

        //if(flag==0)return;

        Paint p = new Paint();
        if (Xx != 0 && OK_flag == 0) {//拡大縮小は1回だけ

            OK_flag = 1;
            PNG = Bitmap.createScaledBitmap(PNG, (int) (Xx * 128), (int) (Yy * 32), true);//BG

        }
        if (OK_flag == 1) {//拡大準備が終わったら表示!!
            //c.drawBitmap(PNG, (int)X, (int)Y, p);

            //from spritesheet
            int x1 = (int) ((32 * (int) No) * Xx); // X and Y start
            int y1 = (int) ((0) * Yy);
            int x2 = (int) ((32 * (int) No + 32) * Xx); //X and Y + size of image to cut
            int y2 = (int) ((0 + 32) * Yy);

            int Sx1 = (int) ((X) * Xx); //where on screen
            int Sy1 = (int) ((Y) * Yy);
            int Sx2 = (int) ((X + 32) * Xx); //what size (from previous position)
            int Sy2 = (int) ((Y + 32) * Yy);

            c.drawBitmap(PNG,
                    new Rect(x1, y1, x2, y2),            //cut from spritesheet!!!
                    new Rect(Sx1, Sy1, Sx2, Sy2),         //where on screen!!!!
                    p);
        }
    }


    public void update() {
        No += FrameTime * 10;
        if (No >= 4) {
            No = 0;
        }
    }

}
