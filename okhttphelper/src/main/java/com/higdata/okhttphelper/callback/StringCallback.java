package com.higdata.okhttphelper.callback;

import java.io.IOException;

import okhttp3.Response;

/**
 * 请求基类 {@link BaseCallback} 的抽象实现
 */
public abstract class StringCallback extends BaseCallback<String> {

    /**
     * 将响应结果解析为字符串
     *
     * @param response 响应
     * @return 响应结果
     * @throws IOException 解析失败时抛出错误
     */
    @Override
    public String parseResponse(Response response) throws IOException {
        String result = response.body().string();
        response.body().close();
        return result;
    }
}
