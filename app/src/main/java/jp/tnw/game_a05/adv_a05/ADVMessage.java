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
    public Bitmap FRAME, DARK;
    private int OK_flag;
    private int finished = 0;
    int cnt = 0;
    double timer = 0, timeout = 0;

    ADVMessage_Data data;

    String[] Text1 = {"", "", "", ""};
    String newString = "";
    String updateString = "";


    ADVMessage() {
        data = new ADVMessage_Data();
    }

    public void loadImage(Resources res) {

        BitmapFactory.Options opt = new BitmapFactory.Options();//ﾌﾗｸﾞを新規で作成
        opt.inScaled = false;//ﾉｰﾏﾙで読み込むために
        FRAME = BitmapFactory.decodeResource(res, R.drawable.waku, opt);//実際のファイル読み込み
        DARK = BitmapFactory.decodeResource(res, R.drawable.waku2, opt);//実際のファイル読み込み

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

            p.setTextSize(28 * (float) Xx);
            p.setColor(Color.WHITE);
            c.drawText(Text1[0], (int) ((28 * 1) * Xx), (int) ((540 - 175 + 50) * Yy), p);
            c.drawText(Text1[1], (int) ((28 * 1) * Xx), (int) ((540 - 175 + 50 + 28 * 1) * Yy), p);
            c.drawText(Text1[2], (int) ((28 * 1) * Xx), (int) ((540 - 175 + 50 + 28 * 2) * Yy), p);
            c.drawText(Text1[3], (int) ((28 * 1) * Xx), (int) ((540 - 175 + 50 + 28 * 3) * Yy), p);
            //char[] lastRow = new char[Text1[3].length()];
            //lastRow = Text1[3].toCharArray();
            //String concat = "";
            //for(int i=0; i<lastRow.length;i++) {
            //    c.drawText(concat.concat(String.valueOf(lastRow[i])), (int) ((28 * 1) * Xx), (int) ((540 - 175 + 50 + 28 * 3) * Yy), p);

            //}

            //c.drawBitmap(BG_PNG2,
            //      new Rect(0,0,0,0),
            //    new Rect(0,0,0,0),
            //  p);

        }
    }

}//class end
