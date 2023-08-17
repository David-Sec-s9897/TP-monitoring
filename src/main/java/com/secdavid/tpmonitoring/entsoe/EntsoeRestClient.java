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
import retrofit2.http.Query;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Dependent
public class EntsoeRestClient implements Serializable {

    private static final String BASE_API_URL = "https://web-api.tp.entsoe.eu/";

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
        @GET("/api")
        Call<ResponseBody> getDocumentDefault(@Query("documentType") String documentType,
                                              @Query("controlArea_Domain") String controlArea_Domain,
                                              @Query("periodStart") String periodStart,
                                              @Query("periodEnd") String periodEnd,
                                              @Query("businessType") String businessType,
                                              @Query("processType") String processType,
                                              @Query("securityToken") String securityToken);

        @GET("/api")
        Call<ResponseBody> getDocumentBalancing(@Query("documentType") String documentType,
                                                @Query("area_Domain") String controlArea_Domain,
                                                @Query("periodStart") String periodStart,
                                                @Query("periodEnd") String periodEnd,
                                                @Query("businessType") String businessType,
                                                @Query("processType") String processType,
                                                @Query("securityToken") String securityToken);

        @GET("/api")
        Call<ResponseBody> getDocumentLoad(@Query("documentType") String documentType,
                                           @Query("processType") String processType,
                                           @Query("outBiddingZone_Domain") String controlArea_Domain,
                                           @Query("periodStart") String periodStart,
                                           @Query("periodEnd") String periodEnd,
                                           @Query("businessType") String businessType,
                                           @Query("securityToken") String securityToken);

        @GET("/api")
        Call<ResponseBody> getDocumentGeneration(@Query("documentType") String documentType,
                                                 @Query("in_Domain") String controlArea_Domain,
                                                 @Query("periodStart") String periodStart,
                                                 @Query("periodEnd") String periodEnd,
                                                 @Query("businessType") String businessType,
                                                 @Query("processType") String processType,
                                                 @Query("securityToken") String securityToken);

        @GET("/api")
        Call<ResponseBody> getDocumentTransmission(@Query("documentType") String documentType,
                                                   @Query("in_Domain") String controlArea_Domain,
                                                   @Query("out_Domain") String out_Domain,
                                                   @Query("periodStart") String periodStart,
                                                   @Query("periodEnd") String periodEnd,
                                                   @Query("contract_MarketAgreement.Type") String businessType,
                                                   @Query("processType") String processType,
                                                   @Query("securityToken") String securityToken);
    }
}
