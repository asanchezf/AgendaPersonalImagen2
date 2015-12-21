package activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.antonioejemplos.agendapersonalimagen.R;

import java.sql.SQLException;

import controlador.SQLControlador;

//import com.videumcorp.desarrolladorandroid.navigatio.R;

/**
 * Created by Susana on 29/08/2015.
 */
public class Comunes {

    private SQLControlador dbConnection;//CONTIENE LAS CONEXIONES A BBDD (CREADA EN DBHELPER.CLASS) Y LOS M�TODOS INSERT, UPDATE, DELETE, BUSCAR....
    private Context context;



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

    //Va a ser llaamado desde fuera por eso es público.
    public  void borrarTodos(Activity ventana) {
		/*
		 * Borramos el registro y refrescamos la lista
		 */
        AlertDialog.Builder dialogEliminar = new AlertDialog.Builder(ventana);

        dialogEliminar.setIcon(android.R.drawable.ic_dialog_alert);
        dialogEliminar.setTitle(context.getResources().getString(
                R.string.agenda_eliminar_todos_titulo));
        dialogEliminar.setMessage(context.getResources().getString(
                R.string.agenda_eliminar_todos_mensaje));
        dialogEliminar.setCancelable(false);

        dialogEliminar.setPositiveButton(
                context.getResources().getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int boton) {
                        // dbAdapter.delete(id);

                        dbConnection = new SQLControlador(
                                context.getApplicationContext());
                        try {
                            dbConnection.abrirBaseDeDatos(2);
                        } catch (SQLException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }// Escritura. Borrar

                        dbConnection.borrarTodos();

                        /*Toast.makeText(ActivityLista.this,
                                R.string.agenda_eliminar_todos_confirmacion,
                                Toast.LENGTH_SHORT).show();*/
                        dbConnection.cerrar();
                        //Intent intent=new Intent(ventana.this,ActivityLista.class);
                        //startActivity(intent);
                        //consultar();
                    }
                });

        dialogEliminar.setNegativeButton(android.R.string.no, null);

        dialogEliminar.show();
    }


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

}
