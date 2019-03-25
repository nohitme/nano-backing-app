package info.eric.nobaking.ui;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import javax.annotation.Nullable;

/**
 * A presenter for {@link PlayerView} handles the initialization and lifecycle.
 */
@AutoFactory
public class PlayerViewPresenter {

  @NonNull private final PlayerView playerView;
  @NonNull private final LifecycleOwner lifecycleOwner;
  private final ExtractorMediaSource.Factory sourceFactory;

  @Nullable
  private SimpleExoPlayer simpleExoPlayer;

  public PlayerViewPresenter(
      @NonNull PlayerView playerView,
      @NonNull LifecycleOwner lifecycleOwner,
      @Provided ExtractorMediaSource.Factory sourceFactory) {
    this.playerView = playerView;
    this.lifecycleOwner = lifecycleOwner;
    this.sourceFactory = sourceFactory;
  }

  public void create(@NonNull String videoUrl) {
    lifecycleOwner.getLifecycle().addObserver(lifecycleObserver);

    ExtractorMediaSource mediaSource = sourceFactory.createMediaSource(Uri.parse(videoUrl));
    simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(playerView.getContext());
    playerView.setPlayer(simpleExoPlayer);
    simpleExoPlayer.prepare(mediaSource);
    simpleExoPlayer.setPlayWhenReady(false);
  }

  public void destroy() {
    lifecycleOwner.getLifecycle().removeObserver(lifecycleObserver);
    if (simpleExoPlayer != null) {
      simpleExoPlayer.setPlayWhenReady(false);
    }
  }

  private final LifecycleObserver lifecycleObserver = new DefaultLifecycleObserver() {
    @Override public void onPause(@NonNull LifecycleOwner owner) {
      if (simpleExoPlayer != null) {
        simpleExoPlayer.setPlayWhenReady(false);
      }
    }

    @Override public void onDestroy(@NonNull LifecycleOwner owner) {
      if (simpleExoPlayer != null) {
        simpleExoPlayer.release();
        simpleExoPlayer = null;
      }
    }
  };
}
