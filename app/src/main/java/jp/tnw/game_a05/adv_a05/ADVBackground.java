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

    public Bitmap[] BG_PNG=new Bitmap[13];
    int OK_flag[] = new int[13];
    int BG_sel=0;

    ADVBackground() {

    }

    public void loadImage(Resources res) {

        BitmapFactory.Options opt = new BitmapFactory.Options();//ﾌﾗｸﾞを新規で作成
        opt.inScaled = false;//ﾉｰﾏﾙで読み込むために
        BG_PNG[0] = BitmapFactory.decodeResource(res, R.drawable.bg01, opt);//実際のファイル読み込み
        BG_PNG[1] = BitmapFactory.decodeResource(res, R.drawable.bg01, opt);
        BG_PNG[2] = BitmapFactory.decodeResource(res, R.drawable.bg02, opt);
        BG_PNG[3] = BitmapFactory.decodeResource(res, R.drawable.bg03, opt);
        BG_PNG[4] = BitmapFactory.decodeResource(res, R.drawable.bg04, opt);
        BG_PNG[5] = BitmapFactory.decodeResource(res, R.drawable.bg05, opt);
        BG_PNG[6] = BitmapFactory.decodeResource(res, R.drawable.bg06, opt);
        BG_PNG[7] = BitmapFactory.decodeResource(res, R.drawable.bg07, opt);
        BG_PNG[8] = BitmapFactory.decodeResource(res, R.drawable.bg08, opt);
        BG_PNG[9] = BitmapFactory.decodeResource(res, R.drawable.bg09, opt);
        BG_PNG[10] = BitmapFactory.decodeResource(res, R.drawable.bg10, opt);
        BG_PNG[11] = BitmapFactory.decodeResource(res, R.drawable.bg11, opt);
        BG_PNG[12] = BitmapFactory.decodeResource(res, R.drawable.bg12, opt);
    }

    public void drawImage(Canvas c, double Xx, double Yy) {

        Paint p = new Paint();

        if (Xx != 0 && OK_flag[BG_sel] == 0) {//拡大縮小は1回だけ

            OK_flag[BG_sel] = 1;
            BG_PNG[BG_sel] = Bitmap.createScaledBitmap(BG_PNG[BG_sel], (int) (Xx * 960), (int) (Yy * 540), true);//BG

        }
        if (OK_flag[BG_sel] == 1) {//拡大準備が終わったら表示!!

            c.drawBitmap(BG_PNG[BG_sel], 0, 0, p);
            //c.drawBitmap(BG_PNG2,
            //      new Rect(0,0,0,0),
            //    new Rect(0,0,0,0),
            //  p);

        }

    }

    public void update() {

    }
}//class end
