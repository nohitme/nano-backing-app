package info.eric.nobaking.util;

import androidx.annotation.NonNull;
import io.reactivex.Scheduler;
import java.util.concurrent.Executor;

public interface ExecutorProvider {

  /** Returns executor for I/O operations */
  @NonNull
  Executor ioExecutor();

  @NonNull
  Scheduler ioScheduler();
}
