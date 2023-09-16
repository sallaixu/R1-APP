package xyz.sallai.r1.service.scrcpy;

/**
 * Description: [对类的简单描述]
 * <p>
 * Author:
 * Date: 2023/8/12
 */

import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.media.projection.MediaProjection;
import android.util.Log;
import android.view.Surface;

import java.nio.ByteBuffer;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import xyz.sallai.r1.utils.codec.VideoUtil;

/**
 * H264视频编码
 * MediaProjection（录屏数据） + MediaCodec H264编码
 */
public class MediaVideoEncoder implements Runnable {
    private static final String MIME_TYPE = VideoUtil.MIME_TYPE;
    private AtomicBoolean isStart = new AtomicBoolean(false);
    private MediaProjection mediaProjection;
    private MediaCodec mediaCodec;
    private Surface surface;
    // 录频相关
    private VirtualDisplay virtualDisplay;
    private byte[] sps;
    private byte[] pps;
    private OnFrameListener onFrameListener;
    private int timeoutUs = 30;


    public MediaVideoEncoder(MediaProjection mediaProjection) {
        this.mediaProjection = mediaProjection;
        initMediaCode();
    }

    private void initMediaCode() {
        try {
            MediaFormat format = MediaFormat.createVideoFormat(MIME_TYPE, VideoUtil.DECODE_WIDTH, VideoUtil.DECODE_HEIGHT);
            // 码率控制方式 CQP(恒定质量)，CRF(恒定码率)，VBR(平均码率)。 VBR好一些，不会出现花点
            format.setInteger(MediaFormat.KEY_BITRATE_MODE, VideoUtil.BITRATE_MODE);
            format.setInteger(MediaFormat.KEY_BIT_RATE, VideoUtil.BIT_RATE);
            format.setInteger(MediaFormat.KEY_FRAME_RATE, VideoUtil.FRAME_RATE);
            format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, VideoUtil.I_FRAME_INTERVAL);
            format.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
            mediaCodec = MediaCodec.createEncoderByType(MIME_TYPE);
            mediaCodec.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
            surface = mediaCodec.createInputSurface();
            // 录频关联MediaCodec
            virtualDisplay = mediaProjection.createVirtualDisplay(
                    "VirtualDisplay", VideoUtil.DECODE_WIDTH, VideoUtil.DECODE_HEIGHT, 1,
                    DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC,
                    surface, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            isStart.set(false);
        }
    }

    public void start() {
        if (isStart.compareAndSet(false, true)) {
            Executors.newSingleThreadExecutor().execute(this);
        }
    }

    public void stop() {
        isStart.set(false);
    }

    @Override
    public void run() {
        if (mediaCodec == null) {
            Log.e("TAG", "initMediaCode 失败...");
            return;
        }
        Log.e("TAG", "start h264 encode...");
        mediaCodec.start();
        MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
        // 计算PTS
        // DTS表示解码时间戳，在什么时候解码这一帧的数据.
        // PTS表示显示时间戳 ，在什么时候显示这一帧。
        long pts = 0;
        while (isStart.get()) {
            int index = mediaCodec.dequeueOutputBuffer(info, timeoutUs);

            if (index == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                // sps pps
                MediaFormat outputFormat = mediaCodec.getOutputFormat();
                ByteBuffer csd0 = outputFormat.getByteBuffer("csd-0");
                ByteBuffer csd1 = outputFormat.getByteBuffer("csd-1");
                if (csd1 != null) {
                    sps = new byte[csd0.remaining()];
                    pps = new byte[csd1.remaining()];
                    csd0.get(sps, 0, csd0.remaining());
                    csd1.get(pps, 0, csd1.remaining());
                }
            } else if (index >= 0) {
                // 其它帧
                ByteBuffer buffer = mediaCodec.getOutputBuffer(index);
                buffer.position(info.offset);
                buffer.limit(info.size);
                if (pts == 0) {
                    pts = info.presentationTimeUs;
                }
                info.presentationTimeUs -= pts;
                // 处理帧数据
                frameData(info, buffer, info.presentationTimeUs);
                mediaCodec.releaseOutputBuffer(index, false);
            }
        }
        // 回收
        release();
        Log.e("TAG", "stop h264 encode...");
    }

    // 回收
    private void release() {
        if (mediaCodec != null) {
            mediaCodec.stop();
            mediaCodec.release();
        }
        if (virtualDisplay != null) {
            virtualDisplay.release();
        }
        if (mediaProjection != null) {
            mediaProjection.stop();
        }
        mediaCodec = null;
        virtualDisplay = null;
        mediaProjection = null;
        onFrameListener = null;
        sps = null;
        pps = null;
    }


    private void frameData(MediaCodec.BufferInfo info, ByteBuffer buffer, long time) {
        // I B P及其它帧
        byte[] data = new byte[info.size];
        buffer.get(data);
        int type = info.flags;
        // 1. 如果是I帧就发sps pps
        if (type == MediaCodec.BUFFER_FLAG_KEY_FRAME) {
            // 1. 发个sps pps
            if (sps != null && pps != null) {
                if (onFrameListener != null) {
                    onFrameListener.onSpsPpsListener(type, sps, sps.length, pps, pps.length);
                }
            }
        }
        // 2. 发帧数据
        if (onFrameListener != null) {
            onFrameListener.onFameListener(type, data, data.length, time);
        }
    }

    public interface OnFrameListener {
        //  sps pps 回调
        void onSpsPpsListener(int type, byte[] sps, int spsLen, byte[] pps, int ppsLen);

        // I B P 帧回调 type = 1 时是I帧
        void onFameListener(int type, byte[] data, int len, long time);
    }

    public void setOnFrameListener(OnFrameListener onFrameListener) {
        this.onFrameListener = onFrameListener;
    }
}
