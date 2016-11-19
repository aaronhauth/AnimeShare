package cj1098.animeshare.network;

import cj1098.animeshare.userList.AnimeObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by chris on 11/18/16.
 */

public interface NetworkCommand {
    @GET("/anime/{id}")
    Call<AnimeObject> callAnimeById(@Path("id") int id);
}
