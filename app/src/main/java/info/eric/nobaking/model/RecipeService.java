package info.eric.nobaking.model;

import io.reactivex.Single;
import java.util.List;
import retrofit2.http.GET;

public interface RecipeService {

  @GET("/android-baking-app-json")
  Single<List<Recipe>> recipes();
}
