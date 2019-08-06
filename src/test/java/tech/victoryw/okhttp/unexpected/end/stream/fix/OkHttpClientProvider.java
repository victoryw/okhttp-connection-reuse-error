package tech.victoryw.okhttp.unexpected.end.stream.fix;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class OkHttpClientProvider {
    private OkHttpClient.Builder builder;

    public OkHttpClientProvider() {

        builder = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(6, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .followRedirects(false);

    }

    public OkHttpClient getInstance() {
        OkHttpClient client = builder.build();
        client.dispatcher().setMaxRequestsPerHost(1000);
        client.dispatcher().setMaxRequests(1000);
        return client;
    }
}
