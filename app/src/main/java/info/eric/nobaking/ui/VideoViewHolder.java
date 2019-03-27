package info.eric.nobaking.ui;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
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

  private final VideoViewCallback callback;
  private final PlayerViewPresenterFactory playerViewPresenterFactory;

  @BindView(R.id.step_player_view) PlayerView playerView;

  @Nullable private PlayerViewPresenter playerViewPresenter;

  public VideoViewHolder(
      @NonNull View itemView,
      @NonNull VideoViewCallback callback,
      @Provided PlayerViewPresenterFactory playerViewPresenterFactory) {
    super(itemView);
    this.callback = callback;
    this.playerViewPresenterFactory = playerViewPresenterFactory;
  }

  @Override public void bind(VideoUrl item) {
    playerViewPresenter = playerViewPresenterFactory.create(playerView);
    playerViewPresenter.create(item.url(), item.position());
  }

  @Override public void unbind() {
    if (playerViewPresenter != null) {
      callback.updatePlayedPosition(playerViewPresenter.getPlayedPosition());
      playerViewPresenter.destroy();
    }
  }

  public interface VideoViewCallback {
    void updatePlayedPosition(long position);
  }
}
