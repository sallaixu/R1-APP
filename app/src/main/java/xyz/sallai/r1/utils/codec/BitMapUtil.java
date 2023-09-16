package xyz.sallai.r1.utils.codec;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * Description: [对类的简单描述]
 * <p>
 * Author:
 * Date: 2023/8/15
 */
public class BitMapUtil {
    public static byte[] convertBitmapToByteArray(Bitmap bitmap,int quality) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        return stream.toByteArray();
    }
}
