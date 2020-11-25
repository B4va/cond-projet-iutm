import utils.EnvironmentVariablesUtils;
import org.apache.logging.log4j.Logger;

import static utils.LoggerUtils.buildLogger;

/**
 * Classe de test.
 */
public class Test {

  private static final Logger LOGGER = buildLogger(Test.class);

  public static void main(String[] args) {
    if (EnvironmentVariablesUtils.getBoolean("TEST_B", true)) {
      LOGGER.info(EnvironmentVariablesUtils.getString("TEST_S", "défaut"));
      LOGGER.info(EnvironmentVariablesUtils.getInt("TEST_I", 1) * 2);
    }
  }
}
