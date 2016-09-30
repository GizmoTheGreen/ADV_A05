package jp.tnw.game_a05.adv_a05;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 背景の処理
 */
public class ADVBackground {

    public Bitmap BG_PNG;
    int OK_flag;

    ADVBackground() {

    }

    public void loadImage(Resources res) {

        BitmapFactory.Options opt = new BitmapFactory.Options();//ﾌﾗｸﾞを新規で作成
        opt.inScaled = false;//ﾉｰﾏﾙで読み込むために
        BG_PNG = BitmapFactory.decodeResource(res, R.drawable.bg03, opt);//実際のファイル読み込み

    }

    public void drawImage(Canvas c, double Xx, double Yy) {

        Paint p = new Paint();

        if (Xx != 0 && OK_flag == 0) {//拡大縮小は1回だけ

            OK_flag = 1;
            BG_PNG = Bitmap.createScaledBitmap(BG_PNG, (int) (Xx * 960), (int) (Yy * 540), true);//BG

        }
        if (OK_flag == 1) {//拡大準備が終わったら表示!!

            c.drawBitmap(BG_PNG, 0, 0, p);
            //c.drawBitmap(BG_PNG2,
            //      new Rect(0,0,0,0),
            //    new Rect(0,0,0,0),
            //  p);

        }

    }

    public void update() {

    }
}//class end
