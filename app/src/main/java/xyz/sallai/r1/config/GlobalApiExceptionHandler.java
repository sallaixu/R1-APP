package xyz.sallai.r1.config;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.yanzhenjie.andserver.annotation.Resolver;
import com.yanzhenjie.andserver.error.BasicException;
import com.yanzhenjie.andserver.error.HttpException;
import com.yanzhenjie.andserver.framework.ExceptionResolver;
import com.yanzhenjie.andserver.framework.body.JsonBody;
import com.yanzhenjie.andserver.http.HttpRequest;
import com.yanzhenjie.andserver.http.HttpResponse;
import com.yanzhenjie.andserver.http.ResponseBody;
import com.yanzhenjie.andserver.http.StatusCode;
import com.yanzhenjie.andserver.util.MediaType;

import xyz.sallai.r1.module.enums.ErrorEnum;
import xyz.sallai.r1.utils.RR;

/**
 * Description: 全局api异常处理
 * Author: sallai
 * Date: 2023/9/23
 * Email: sallai@aliyun.com
 */
@Resolver
public class GlobalApiExceptionHandler implements ExceptionResolver {

    @Override
    public void onResolve(@NonNull HttpRequest request, @NonNull HttpResponse response, @NonNull Throwable e) {
        if (e instanceof HttpException) {
            HttpException ex = (HttpException)e;
            response.setStatus(ex.getStatusCode());
        } else {
            response.setStatus(StatusCode.SC_INTERNAL_SERVER_ERROR);
        }
        RR rr = RR.errorEnum(ErrorEnum.SERVER_ERROR).setData(e.getLocalizedMessage());
        ResponseBody responseBody = CustomMsgConverter.convertBody(rr, MediaType.APPLICATION_JSON_UTF8);
        response.setBody(responseBody);
    }
}
