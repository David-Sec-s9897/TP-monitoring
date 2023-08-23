package com.secdavid.tpmonitoring.entsoe;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jaxb.JaxbConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Dependent
public class TransparencyPortalRestClient implements Serializable {

    private static final String BASE_API_URL = "http://localhost:8080/logic-mapper/rest/";

    private TransparencyPortalRestService restService;

    @PostConstruct
    public void init() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS);

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        httpClient.addInterceptor(httpLoggingInterceptor);

//        for testing purposes only
//        httpClient.addInterceptor(new FakeInterceptor());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addConverterFactory(JaxbConverterFactory.create())
                .client(httpClient.build())
                .build();

        restService = retrofit.create(TransparencyPortalRestService.class);
    }

    public TransparencyPortalRestService getService() {
        return restService;
    }

    public interface TransparencyPortalRestService {
        @GET("/timeseries/jobs")
        Call<ResponseBody> getJobs();

        @GET("/masterdata/get/{name}")
        Call<ResponseBody> getFile(@Path("name") String name);

    }
}
