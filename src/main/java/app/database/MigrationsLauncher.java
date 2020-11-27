package app.database;

import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;
import utils.EnvironmentVariablesUtils;
import utils.LoggerUtils;

/**
 * Lanceur des processus migrations via {@link Flyway}.
 */
public class MigrationsLauncher {

  private static final Logger LOGGER = LoggerUtils.buildLogger(MigrationsLauncher.class);

  /**
   * Commandes disponibles : migrate, repair, clean.
   */
  public static void main(String[] args) {
    try {
      final String url = EnvironmentVariablesUtils.getString(EnvironmentVariablesUtils.DB_URL);
      final String user = EnvironmentVariablesUtils.getString(EnvironmentVariablesUtils.DB_USER);
      final String password = EnvironmentVariablesUtils.getString(EnvironmentVariablesUtils.PASSWORD);
      Flyway flyway = Flyway.configure().dataSource(url, user, password).load();
      execute(flyway, args);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      LOGGER.info("Processus terminée.");
    }
  }

  private static void execute(Flyway flyway, String[] args) {
    if (args.length < 1) {
      LOGGER.error("Paramètre manquant.");
    } else {
      switch (args[0]) {
        case "-migrate":
          LOGGER.info("RUN Migrate.");
          flyway.migrate();
          break;
        case "-clean":
          LOGGER.info("RUN Clean.");
          flyway.clean();
          break;
        default:
          LOGGER.error("Paramètre inconnu.");
          break;
      }
    }
  }
}
