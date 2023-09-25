package com.secdavid.tpmonitoring.entsoe;

import com.secdavid.tpmonitoring.test.Note.Note;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jaxb.JaxbConverterFactory;
import retrofit2.http.GET;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Dependent
public class XMLTestRestClient implements Serializable {

    private static final String BASE_API_URL = "https://www.w3schools.com/";

    private EntsoeRestService restService;

    @PostConstruct
    public void init() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS);

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        httpClient.addInterceptor(httpLoggingInterceptor);
        //httpClient.addInterceptor(fakeResponseInterceptor);

//        for testing purposes only
//        httpClient.addInterceptor(new FakeInterceptor());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addConverterFactory(JaxbConverterFactory.create())
                .client(httpClient.build())
                .build();

        restService = retrofit.create(EntsoeRestService.class);
    }

    public EntsoeRestService getService() {
        return restService;
    }

    public interface EntsoeRestService {
        @GET("/xml/note.xml")
        Call<Note> testGet();

    }
}