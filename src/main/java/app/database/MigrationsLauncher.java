package app.database;

import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;
import utils.EnvironmentVariablesUtils;
import utils.LoggerUtils;

import java.util.Scanner;

/**
 * Lanceur des migrations via {@link Flyway}.
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
      execute(flyway);
      LOGGER.info("Processus terminée.");
    } catch (Exception e) {
      e.printStackTrace();
      LOGGER.error("Migration impossible.");
    }
  }

  private static void execute(Flyway flyway) {
    Scanner sc = new Scanner(System.in);
    System.out.print("CMD > ");
    String cmd = sc.nextLine();
    switch (cmd.toLowerCase().trim()) {
      case "migrate":
        LOGGER.info("RUN : Migrate.");
        flyway.migrate();
        break;
      case "repair":
        LOGGER.info("RUN : Repair.");
        flyway.repair();
        break;
      case "clean":
        System.out.print("Confirm (yes/no) > ");
        if (sc.nextLine().toLowerCase().trim().equals("yes")) {
          LOGGER.info("RUN : Clean.");
          flyway.clean();
        }
        break;
      default:
        LOGGER.error("Commande inconnue.");
        break;
    }
  }
}
