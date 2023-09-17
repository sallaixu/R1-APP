package xyz.sallai.r1.utils.okhttp;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import com.phicomm.speaker.device.ExampleApp;
import com.phicomm.speaker.device.ui.MainActivity;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;

import xyz.sallai.r1.bean.system.DiskVo;
import xyz.sallai.r1.bean.system.MemeryVo;
import xyz.sallai.r1.utils.RR;
import xyz.sallai.r1.utils.system.ShellUtils;

/**
 * 获取系统信息
 * author:sallai
 */
public class SystemInfoUtils {
    private static final String TAG = SystemInfoUtils.class.getSimpleName();

    /**
     * 获取系统内存信息
     */
    public static MemeryVo getMemeryInfo() {
        ActivityManager activityManager = (ActivityManager) ExampleApp.context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        long availableMemory = memoryInfo.availMem;
        long totalMemory = memoryInfo.totalMem;
        MemeryVo memeryVo = MemeryVo.builder().available(unitConvert(availableMemory))
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

}
