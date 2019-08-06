package tech.victoryw.okhttp.unexpected.end.stream.fix;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class RequestConnectionCloseInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request newRequest;

        newRequest = request.newBuilder()
                .addHeader("Connection", "close")
                .build();
        return chain.proceed(newRequest);
    }
}
