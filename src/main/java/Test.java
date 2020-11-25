import utils.EnvironmentVariablesUtils;
import org.apache.logging.log4j.Logger;

import static utils.LoggerUtils.buildLogger;

/**
 * Classe de test.
 */
public class Test {

  public static void main(String[] args) {
    final Logger LOGGER = buildLogger(Test.class);
    if (EnvironmentVariablesUtils.getBoolean("TEST_B")) {
      LOGGER.info(EnvironmentVariablesUtils.getString("TEST_S"));
      LOGGER.info(EnvironmentVariablesUtils.getInt("TEST_I") * 2);
    }
  }
}
