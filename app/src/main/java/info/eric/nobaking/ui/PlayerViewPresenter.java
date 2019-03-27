package info.eric.nobaking.ui;

import android.net.Uri;
import androidx.annotation.NonNull;
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
  private final ExtractorMediaSource.Factory sourceFactory;

  @Nullable
  private SimpleExoPlayer simpleExoPlayer;

  public PlayerViewPresenter(
      @NonNull PlayerView playerView,
      @Provided ExtractorMediaSource.Factory sourceFactory) {
    this.playerView = playerView;
    this.sourceFactory = sourceFactory;
  }

  public void create(@NonNull String videoUrl, long startPosition) {
    ExtractorMediaSource mediaSource = sourceFactory.createMediaSource(Uri.parse(videoUrl));
    simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(playerView.getContext());
    playerView.setPlayer(simpleExoPlayer);
    simpleExoPlayer.prepare(mediaSource);
    simpleExoPlayer.seekTo(startPosition);
    simpleExoPlayer.setPlayWhenReady(false);
  }

  public void destroy() {
    if (simpleExoPlayer != null) {
      simpleExoPlayer.setPlayWhenReady(false);
      simpleExoPlayer.release();
      simpleExoPlayer = null;
    }
  }

  public long getPlayedPosition() {
    if (simpleExoPlayer == null) {
      return 0L;
    }

    return simpleExoPlayer.getCurrentPosition();
  }
}
