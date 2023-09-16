package xyz.sallai.r1.utils.codec;

import android.media.MediaCodecInfo;
import android.media.MediaFormat;

/**
 * Description: [对类的简单描述]
 * <p>
 * Author:
 * Date: 2023/8/12
 */
public class VideoUtil {
    // 360*640(1000_000) 540*960(高清 1500_000) 720*1280(超清 1800_000) 1080*1920(蓝光 2500_000)
    // MIMETYPE_VIDEO_AVC   MIMETYPE_VIDEO_MPEG4
    public static final String MIME_TYPE = MediaFormat.MIMETYPE_VIDEO_AVC;
    public static final int DECODE_HEIGHT = 1080;
    public static final int DECODE_WIDTH = 1920;
    public static final int BIT_RATE = 2500_000; // 码率
    // 码率控制方式 CQP(恒定质量)，CRF(恒定码率)，VBR(平均码率)。 VBR好一些，不会出现花点
    public static final int BITRATE_MODE = MediaCodecInfo.EncoderCapabilities.BITRATE_MODE_VBR;
    public static final int FRAME_RATE = 30; // I帧刷新频率
    public static final int I_FRAME_INTERVAL = 1; // 每秒刷新
}
