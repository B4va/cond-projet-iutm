package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Gestion du {@link Logger}.
 */
public class LoggerUtils {

  /**
   * Construit une instance du {@link Logger}.
   *
   * @param c classe concernée par l'instance
   * @return logger
   */
  @SuppressWarnings("rawtypes")
  public static Logger build(Class c) {
    return LogManager.getLogger(c.getSimpleName());
  }
}
