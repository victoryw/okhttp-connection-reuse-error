package tech.victoryw.okhttp.unexpected.end.stream.fix;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tech.victoryw.okhttp.unexpected.end.stream.fix.controller.User;

import java.io.IOException;
import java.net.SocketException;
import java.util.logging.Logger;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class OkHttpClientConnectionReuseTest {
    private static final Logger logger = Logger.getLogger(OkHttpClientConnectionReuseTest.class.getName());

    @LocalServerPort
    private int port;

    @Autowired
    private OkHttpClientProvider okHttpClientProvider;

    @BeforeEach
    void setUp() {
    }

    @Test
    void should_use_ok_http_get_user_name() throws IOException, InterruptedException {
        final OkHttpClient okHttpClient = okHttpClientProvider.getInstance();

        try( Response response = okHttpClient.newCall(new Request.Builder()
                .url(String.format("http://localhost:%d/users/self", port))
                .get()
                .build()).execute();){
            assertEquals(200, response.code());
        }

        Thread.sleep(60500);
        assertThrows(IOException.class, () -> {
        try( Response response = okHttpClient.newCall(new Request.Builder()
                .url(String.format("http://localhost:%d/users/self", port))
                .get()
                .build()).execute();) {
            logger.info(()-> "this is no execpected throw");
        }
        });
    }

    @Test
    void should_use_ok_http_get_user_name2() throws IOException, InterruptedException {
        final OkHttpClient okHttpClient = okHttpClientProvider.getInstance().
                newBuilder().
                addInterceptor(new RequestConnectionCloseInterceptor()).
                build();
        try( Response response = okHttpClient.newCall(new Request.Builder()
                .url(String.format("http://localhost:%d/users/self", port))
                .get()
                .build()).execute();){
            assertEquals(200, response.code());
        }

        Thread.sleep(60500);
        try( Response response = okHttpClient.newCall(new Request.Builder()
                .url(String.format("http://localhost:%d/users/self", port))
                .get()
                .build()).execute();){
            assertEquals(200, response.code());
        }
    }
}

