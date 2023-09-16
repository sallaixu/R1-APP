package xyz.sallai.r1.service.scrcpy;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.SurfaceControl;

import com.phicomm.speaker.device.ui.MainActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Description: [对类的简单描述]
 * <p>
 * Author:
 * Date: 2023/8/14
 */
public class ScreenShot {

    public static Bitmap screenshot(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();

        String surfaceClassName = "";
        surfaceClassName = "android.view.SurfaceControl";

        try {
            Class<?> c = Class.forName(surfaceClassName);
            Method method = c.getMethod("screenshot", new Class[]{int.class, int.class});
//            Method method = c.getDeclaredMethod("screenshot", Rect.class, int.class, int.class, int.class);
            method.setAccessible(true);
//            Rect sourceCrop = new Rect(0, 0, dm.widthPixels, dm.heightPixels); // 设置截图区域
//            int jpegQuality = 60; // 设置JPEG质量（0-100之间）
//            int rotation = 0; // 设置旋转角度（0、90、180或270）

//            return (Bitmap) method.invoke(null, sourceCrop, 350, 583, jpegQuality, rotation);
            return (Bitmap) method.invoke(null,  720, 480);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


}
