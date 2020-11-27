import models.Test;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import utils.DbUtils;
import utils.EnvironmentVariablesUtils;

import static utils.LoggerUtils.buildLogger;

/**
 * Classe de test.
 */
public class MainTest {

  private static final Logger LOGGER = buildLogger(MainTest.class);

  public static void main(String[] args) {
    if (EnvironmentVariablesUtils.getBoolean("TEST_B", true)) {
      LOGGER.info(EnvironmentVariablesUtils.getString("TEST_S", "défaut"));
      LOGGER.info(EnvironmentVariablesUtils.getInt("TEST_I", 1) * 2);
    }
    Session session = DbUtils.getSessionFactory().openSession();
    Test test = session.get(Test.class, 1);
    LOGGER.info("ID : " + test.getId() + " ; TEXT : " + test.getText());
    session.close();
  }
}
