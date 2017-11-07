package network;

import com.google.gson.Gson;
import network.service.MavenSearchService;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.java8.Java8CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import util.Strings;

import java.util.concurrent.Executors;

public class NetworkManager {

    private Retrofit retrofit;

    private Gson gson;

    private OkHttpClient okHttpClient;

    private MavenSearchService mavenSearchService;

    public NetworkManager() {
        initRequirements();

        retrofit = new Retrofit.Builder()
                .baseUrl(Strings.BASE_URL)
                .addCallAdapterFactory(Java8CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .build();

        initServices();
    }

    private void initRequirements() {
        gson = new Gson();
        okHttpClient = initOkHttpClient();
    }

    private OkHttpClient initOkHttpClient() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder();

        clientBuilder.addInterceptor(chain -> {
            Response response = chain.proceed(chain.request());

            String responseBody = response.body().string();

            // workaround for bad return type json
            if (responseBody.contains("\"suggestions\":[\"")) {
                String firstPart = responseBody.substring(0, responseBody.indexOf("\"spellcheck\":{") + 14);
                String secondPart = responseBody.substring(responseBody.indexOf(",{\"numFound\"") + 2, responseBody.length());
                secondPart = secondPart.replaceFirst("]", "");
                secondPart = secondPart.replaceFirst("}", "");
                Response newResponse = response.newBuilder().body(ResponseBody.create(response.body().contentType(), firstPart + secondPart)).build();
                return newResponse;
            }

            return response.newBuilder().body(ResponseBody.create(response.body().contentType(), responseBody)).build();
        });

        return clientBuilder.build();
    }

    private void initServices() {
        mavenSearchService = retrofit.create(MavenSearchService.class);
    }

    public MavenSearchService getMavenSearchService() {
        return mavenSearchService;
    }
}
