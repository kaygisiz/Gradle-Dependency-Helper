/**
 *  Copyright (C) 2017 Necati Caner Gaygisiz
 *  Copyright (C) 2019 Mehmet Sirin Usanmaz
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
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
