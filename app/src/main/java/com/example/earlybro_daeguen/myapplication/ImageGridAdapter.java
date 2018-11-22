package com.example.earlybro_daeguen.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import java.util.ArrayList;
import com.bumptech.glide.Glide;
/**
 * Created by Home on 2017-05-19.
 */

public class ImageGridAdapter extends BaseAdapter {

    Context context = null;
    String shopID;
    ArrayList <Bitmap> getBitmaps = new ArrayList<Bitmap>();

    public ImageGridAdapter(Context context , ArrayList <Bitmap> getBitmaps){
        this.context = context;
        this.getBitmaps = getBitmaps;
    }

    public void addItem(Bitmap bitmap){
        getBitmaps.add(bitmap);
    }
    

    @Override
    public int getCount() {
        if(getBitmaps != null)
            return getBitmaps.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        if(getBitmaps != null)
            return getBitmaps.get(position);
        else
            return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;


        if (null != convertView) {
            imageView = (ImageView) convertView;
            imageView.setImageBitmap(getBitmaps.get(position));
        }
        else {
            //---------------------------------------------------------------
            // GridView 뷰를 구성할 ImageView 뷰의 비트맵을 정의합니다.
            // 그리고 그것의 크기를 320*240으로 줄입니다.
            // 크기를 줄이는 이유는 메모리 부족 문제를 막을 수 있기 때문입니다.
           /* Bitmap bmp
                    = BitmapFactory.decodeResource(context.getResources(), getDrawable.get(position));
            bmp = Bitmap.createScaledBitmap(bmp, 300, 300, false);*/
       /*    byte[] decodedString = Base64.decode(ImgArray[position], Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ImgArray[position] = Bitmap.createScaledBitmap( ImgArray[position],300,300,false);
*/


            //---------------------------------------------------------------320:240 = 100:
            // GridView 뷰를 구성할 ImageView 뷰들을 정의합니다.
            // 뷰에 지정할 이미지는 앞에서 정의한 비트맵 객체입니다.



            imageView = new ImageView(context);
            imageView.setAdjustViewBounds(true);
            imageView.setImageBitmap(getBitmaps.get(position));

            //---------------------------------------------------------------
            // 지금은 사용하지 않는 코드입니다.
            //imageView.setMaxWidth(320);
            //imageView.setMaxHeight(240);
            //imageView.setImageResource(imageIDs[position]);

            //---------------------------------------------------------------
            // 사진 항목들의 클릭을 처리하는 ImageClickListener 객체를 정의합니다.
            // 그리고 그것을 ImageView의 클릭 리스너로 설정합니다.

        }
        return imageView;
    }

}




