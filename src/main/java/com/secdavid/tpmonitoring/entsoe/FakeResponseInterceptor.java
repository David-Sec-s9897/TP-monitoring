package com.secdavid.tpmonitoring.entsoe;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FakeResponseInterceptor implements Interceptor {

    public static final String RESPONSE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<note>\n" +
            "  <to>Tove</to>\n" +
            "  <from>Jani</from>\n" +
            "  <heading>Reminder</heading>\n" +
            "  <body>Don't forget me this weekend!</body>\n" +
            "</note>";

    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request request = chain.request();
        final Response response = chain.proceed(request);
        final ResponseBody body = response.body();
        return response.newBuilder()
                .body(ResponseBody.create(
                        body.contentType(), RESPONSE.getBytes(StandardCharsets.UTF_8)))
                .build();
    }

}
