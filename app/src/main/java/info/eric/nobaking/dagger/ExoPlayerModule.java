package info.eric.nobaking.dagger;

import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSourceFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;

@Module
public abstract class ExoPlayerModule {

  @Provides
  @Singleton
  static DataSource.Factory provideDataSourceFactory(OkHttpClient okHttpClient) {
    return new OkHttpDataSourceFactory(okHttpClient, null);
  }

  @Provides
  @Singleton
  static ExtractorMediaSource.Factory provideExtractorFactory(DataSource.Factory factory) {
    return new ExtractorMediaSource.Factory(factory);
  }
}
