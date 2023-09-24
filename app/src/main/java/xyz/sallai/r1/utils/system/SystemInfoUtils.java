package xyz.sallai.r1.utils.system;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;

import com.phicomm.speaker.device.ExampleApp;

import java.text.DecimalFormat;

import xyz.sallai.r1.bean.system.DiskVo;
import xyz.sallai.r1.bean.system.MemoryVo;

/**
 * 获取系统信息
 * author:sallai
 */
public class SystemInfoUtils {
    private static final String TAG = SystemInfoUtils.class.getSimpleName();

    /**
     * 获取系统内存信息
     */
    public static MemoryVo getMemeryInfo() {
        ActivityManager activityManager = (ActivityManager) ExampleApp.context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        long availableMemory = memoryInfo.availMem;
        long totalMemory = memoryInfo.totalMem;
        MemoryVo memeryVo = MemoryVo.builder().available(unitConvert(availableMemory))
                .total(unitConvert(totalMemory)).build();
        return memeryVo;
    }

    public static DiskVo getDiskInfo() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath(); // 获取SD卡路径
        StatFs statFs = new StatFs(path);
        long blockSize = statFs.getBlockSizeLong(); // 块大小
        long totalBlocks = statFs.getBlockCountLong(); // 总块数
        long availableBlocks = statFs.getAvailableBlocksLong(); // 可用块数
        long totalSize = totalBlocks * blockSize; // 总大小（字节）
        long availableSize = availableBlocks * blockSize; // 可用大小（字节）
        DiskVo diskVo = DiskVo.builder().available(unitConvert(availableSize))
                .total(unitConvert(totalSize)).build();
        return diskVo;

    }

    /**
     * 读取系统平均负载
     * @return
     */
    public static String getLoadAvg() {
        ShellUtils.CommandResult commandResult = ShellUtils.execCommand("cat /proc/loadavg", false, true);
        //1.08 2.05 2.26 3/753 16688
        String successMsg = commandResult.successMsg;
        return successMsg;
    }


    public static String unitConvert(long size) {
        String value = "";
        DecimalFormat df = new DecimalFormat("#.00");
        double gigaBytes = (double) size / (1024 * 1024 * 1024);
        if (gigaBytes >= 1) {
            value = df.format(gigaBytes) + "G";
        } else {
            double megaBytes = (double) size / (1024 * 1024);
            value = df.format(megaBytes) + "M";
        }
        return value;
    }

    /**
     * 获取开机时间
     * @param context
     * @return
     */
    public static String getBootTime(Context context) {
        // 获取系统启动时间（开机时间）的毫秒数
        long uptimeMillis = SystemClock.elapsedRealtime();

        // 计算已启动的天数
        long days = uptimeMillis / (1000 * 60 * 60 * 24);

        // 计算已启动的小时数
        long hours = (uptimeMillis % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);

        // 计算已启动的分钟数
        long minutes = (uptimeMillis % (1000 * 60 * 60)) / (1000 * 60);

        // 构建字符串表示已启动的时间
        String bootTime = days + "天 " + hours + "小时 " + minutes + "分钟";

        return bootTime;
    }

}
