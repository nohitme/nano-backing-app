package info.eric.nobaking.ui;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import butterknife.BindView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import info.eric.nobaking.R;
import info.eric.nobaking.model.VideoUrl;
import info.eric.nobaking.recyclerview.AbstractViewHolder;
import javax.annotation.Nullable;

@AutoFactory
public class VideoViewHolder extends AbstractViewHolder<VideoUrl> implements
    DefaultLifecycleObserver {

  private final LifecycleOwner lifecycleOwner;
  private final PlayerViewPresenterFactory playerViewPresenterFactory;

  @BindView(R.id.step_player_view) PlayerView playerView;

  @Nullable private PlayerViewPresenter playerViewPresenter;

  public VideoViewHolder(
      @NonNull View itemView,
      @NonNull LifecycleOwner lifecycleOwner,
      @Provided PlayerViewPresenterFactory playerViewPresenterFactory) {
    super(itemView);
    this.lifecycleOwner = lifecycleOwner;
    this.playerViewPresenterFactory = playerViewPresenterFactory;
  }

  @Override public void bind(VideoUrl item) {
    lifecycleOwner.getLifecycle().addObserver(this);
    playerViewPresenter = playerViewPresenterFactory.create(playerView, lifecycleOwner);
    playerViewPresenter.create(item.url());
  }

  @Override public void unbind() {
    if (playerViewPresenter != null) {
      playerViewPresenter.destroy();
    }
  }
}
