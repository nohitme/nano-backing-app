package info.eric.nobaking.ui;

import android.net.Uri;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import butterknife.BindView;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import info.eric.nobaking.R;
import info.eric.nobaking.model.VideoUrl;
import info.eric.nobaking.recyclerview.AbstractViewHolder;

@AutoFactory
public class VideoViewHolder extends AbstractViewHolder<VideoUrl> implements
    DefaultLifecycleObserver {

  private LifecycleOwner lifecycleOwner;
  private final ExtractorMediaSource.Factory sourceFactory;
  private final SimpleExoPlayer simpleExoPlayer;

  @BindView(R.id.step_player_view) PlayerView playerView;

  public VideoViewHolder(
      @NonNull View itemView,
      @NonNull LifecycleOwner lifecycleOwner,
      @Provided ExtractorMediaSource.Factory sourceFactory) {
    super(itemView);
    this.lifecycleOwner = lifecycleOwner;
    this.sourceFactory = sourceFactory;
    simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(itemView.getContext());
  }

  @Override public void bind(VideoUrl item) {
    lifecycleOwner.getLifecycle().addObserver(this);

    ExtractorMediaSource mediaSource = sourceFactory.createMediaSource(Uri.parse(item.url()));
    playerView.setPlayer(simpleExoPlayer);
    simpleExoPlayer.prepare(mediaSource);
    simpleExoPlayer.setPlayWhenReady(false);
  }

  @Override public void unbind() {
    simpleExoPlayer.setPlayWhenReady(false);
  }

  @Override public void onPause(@NonNull LifecycleOwner owner) {
    simpleExoPlayer.setPlayWhenReady(false);
  }

  @Override public void onDestroy(@NonNull LifecycleOwner owner) {
    simpleExoPlayer.release();
  }
}
