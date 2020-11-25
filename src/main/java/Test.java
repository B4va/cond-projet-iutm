import config.EnvironmentVariable;
import org.apache.logging.log4j.Logger;
import utils.LoggerUtils;

/**
 * Classe de test.
 */
public class Test {

  private static final Logger LOGGER = LoggerUtils.build(Test.class);

  public static void main(String[] args) {
    if (EnvironmentVariable.getBoolean("TEST_B")) {
      LOGGER.info(EnvironmentVariable.getString("TEST_S"));
      LOGGER.info(EnvironmentVariable.getInt("TEST_I") * 2);
    }
  }
}
