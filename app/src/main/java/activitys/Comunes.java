package activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.antonioejemplos.agendapersonalimagen.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

import controlador.SQLControlador;

//import com.videumcorp.desarrolladorandroid.navigatio.R;

/**
 * Created by Susana on 29/08/2015.
 */
public class Comunes {





   /* @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        switch(keyCode) {
            case KeyEvent.KEYCODE_MENU:
                // Toast.makeText(this, "Menú presionado",
                //       Toast.LENGTH_LONG);
                //toolbar.canShowOverflowMenu();
                //toolbar.setFocusable(true);
                //toolbar.collapseActionView();



                return true;
        }

        return super.onKeyUp(keyCode, event);
    }*/



    //Recibe una imagen y la redimensiona redondeando los bordes
    //Se ha modificado pq originariamente tenía que recibir un Drawable
    public static Bitmap getRoundedCornerBitmap( Bitmap drawable, boolean square) {
        int width = 0;
        int height = 0;

       // Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap() ;
        Bitmap bitmap =drawable;

        if(square){
            if(bitmap.getWidth() < bitmap.getHeight()){
                width = bitmap.getWidth();
                height = bitmap.getWidth();
            } else {
                width = bitmap.getHeight();
                height = bitmap.getHeight();
            }
        } else {
            height = bitmap.getHeight();
            width = bitmap.getWidth();
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, width, height);
        final RectF rectF = new RectF(rect);
        final float roundPx = 90;//360 es totalmente redonda

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {
        final float densityMultiplier = context.getResources().getDisplayMetrics().density;
        int h = (int) (newHeight * densityMultiplier);
        int w = (int) (h * photo.getWidth() / ((double) photo.getHeight()));
        photo = Bitmap.createScaledBitmap(photo, w, h, true);
        return photo;
    }

    /**
     * Redimensionar un Bitmap. By TutorialAndroid.com
     * @param Bitmap mBitmap
     * @param float newHeight
     * @param float newHeight
     * @param float newHeight
     * @return Bitmap
     */
    public static  Bitmap redimensionarImagenMaximo(Bitmap mBitmap, float newWidth, float newHeigth){
        //Redimensionamos
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeigth) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
    }

    public static Bitmap decodeFile(File f)
    {
        Bitmap b = null;
        try
        {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            FileInputStream fis = new FileInputStream(f);
            try
            {
                BitmapFactory.decodeStream(fis, null, o);
            }
            finally
            {
                fis.close();
            }

            // In Samsung Galaxy S3, typically max memory is 64mb
            // Camera max resolution is 3264 x 2448, times 4 to get Bitmap memory of 30.5mb for one bitmap
            // If we use scale of 2, resolution will be halved, 1632 x 1224 and x 4 to get Bitmap memory of 7.62mb
            // We try use 25% memory which equals to 16mb maximum for one bitmap
            long maxMemory = Runtime.getRuntime().maxMemory();
            int maxMemoryForImage = (int) (maxMemory / 100 * 25);

            // Refer to
            // http://developer.android.com/training/displaying-bitmaps/cache-bitmap.html
            // A full screen GridView filled with images on a device with
            // 800x480 resolution would use around 1.5MB (800*480*4 bytes)
            // When bitmap option's inSampleSize doubled, pixel height and
            // weight both reduce in half
            int scale = 1;
            while ((o.outWidth / scale) * (o.outHeight / scale) * 4 > maxMemoryForImage)
                scale *= 2;

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            fis = new FileInputStream(f);
            try
            {
                b = BitmapFactory.decodeStream(fis, null, o2);
            }
            finally
            {
                fis.close();
            }
        }
        catch (IOException e)
        {
        }
        return b;
    }

}
