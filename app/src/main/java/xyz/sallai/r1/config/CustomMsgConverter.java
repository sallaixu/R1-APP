package xyz.sallai.r1.config;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.yanzhenjie.andserver.annotation.Converter;
import com.yanzhenjie.andserver.framework.MessageConverter;
import com.yanzhenjie.andserver.framework.body.JsonBody;
import com.yanzhenjie.andserver.http.ResponseBody;
import com.yanzhenjie.andserver.util.MediaType;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * Description: [对类的简单描述]
 * <p>
 * Author:
 * Date: 2023/8/6
 */
@Converter
public class CustomMsgConverter implements MessageConverter {
    private static final String TAG = "CustomMsgConverter";

    @Override
    public ResponseBody convert(@Nullable Object output, @Nullable MediaType mediaType) {
        String json = JSON.toJSONString(output);
        Log.d(TAG, "convert: " + json);
        JsonBody jsonBody = new JsonBody(json);
        return jsonBody;
    }

    @Nullable
    @Override
    public <T> T convert(@NonNull InputStream stream, @Nullable MediaType mediaType, Type type) throws IOException {
        return null;
    }
}
