package dk.via.fleetforward.startup;

import org.springframework.context.ApplicationContext;

public class GlobalContext {

  private static ApplicationContext context;

  private GlobalContext() {
    // prevent instantiation
  }

  public static void setContext(ApplicationContext applicationContext) {
    context = applicationContext;
  }

  public static ApplicationContext getContext() {
    if (context == null) {
      throw new IllegalStateException("Spring ApplicationContext not initialized yet.");
    }
    return context;
  }

  public static <T> T getBean(Class<T> beanClass) {
    return getContext().getBean(beanClass);
  }
}
