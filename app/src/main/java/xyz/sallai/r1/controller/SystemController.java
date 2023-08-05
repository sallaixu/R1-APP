package xyz.sallai.r1.controller;

import android.util.Log;

import com.yanzhenjie.andserver.annotation.GetMapping;
import com.yanzhenjie.andserver.annotation.RequestMapping;
import com.yanzhenjie.andserver.annotation.RestController;

import xyz.sallai.r1.bean.system.DiskVo;
import xyz.sallai.r1.bean.system.MemeryVo;
import xyz.sallai.r1.bean.system.SystemInfoBean;
import xyz.sallai.r1.utils.RR;
import xyz.sallai.r1.utils.okhttp.SystemInfoUtils;

/**
 * Description: [对类的简单描述]
 * <p>
 * Author: sallai
 * Date: 2023/5/13
 */
@RestController
@RequestMapping("/_api/sys")
public class SystemController {


    private static final String TAG = "SystemController";

    @GetMapping("/info")
    public RR getSysInfo() {
        DiskVo diskInfo = SystemInfoUtils.getDiskInfo();
        MemeryVo memeryInfo = SystemInfoUtils.getMemeryInfo();
        SystemInfoBean systemInfoBean = new SystemInfoBean(memeryInfo, diskInfo);
        Log.d(TAG, "getSysInfo: " + systemInfoBean);
        return RR.ok(systemInfoBean);
    }



}
