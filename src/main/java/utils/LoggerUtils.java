package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Gestion du {@link Logger}.
 */
public class LoggerUtils {

  private LoggerUtils() {
  }

  /**
   * Construit une instance du {@link Logger}.
   *
   * @param c classe concernée par l'instance
   * @return logger
   */
  public static Logger buildLogger(Class c) {
    return LogManager.getLogger(c.getSimpleName());
  }

}