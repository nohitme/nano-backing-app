package info.eric.nobaking.util;

import androidx.annotation.NonNull;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ExecutorProviderImpl implements ExecutorProvider {

  private final Executor ioExecutor;
  private final Scheduler ioScheduler;

  @Inject ExecutorProviderImpl() {
    ioExecutor = newIoExecutor();
    ioScheduler = Schedulers.from(ioExecutor);
  }

  private Executor newIoExecutor() {
    final ThreadFactory threadFactory =
        new ThreadFactoryBuilder().setNameFormat("cached-io-%d").build();
    return Executors.newCachedThreadPool(threadFactory);
  }

  @NonNull
  @Override
  public Executor ioExecutor() {
    return ioExecutor;
  }

  @NonNull
  @Override
  public Scheduler ioScheduler() {
    return ioScheduler;
  }
}
