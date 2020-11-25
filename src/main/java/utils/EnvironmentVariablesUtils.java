package utils;

import org.apache.logging.log4j.Logger;

import javax.naming.ConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

import static utils.LoggerUtils.buildLogger;

/**
 * Accès aux variables d'environnement du programme.
 */
public class EnvironmentVariablesUtils {

  public static final String LOG_LEVEL = "LOG_LEVEL";
  public static final String LOG_FORMAT = "LOG_FORMAT";

  private static final Logger LOGGER = buildLogger(EnvironmentVariablesUtils.class);
  private static final String ENVIRONMENT_VARIABLES_FILE = "/ENVIRONMENT.properties";

  private static final String ERR_GET_INT = "La variable d'environnement '{}' ne peut pas être convertie en Integer";
  private static final String ERR_GET_BOOLEAN = "La variable d'environnement '{}' ne peut pas être convertie en Boolean";
  private static final String ERR_READ_FILE = "Impossible de lire le fichier de variables d'environnement.";
  private static final String ERR_FIND_VAR = "La variable d'environnement '{}' est introuvable";
  private static final String ERR_NO_FILE = "Le fichier de configuration des variables d'environnement est introuvable.";

  /**
   * Accès à une variable d'environnement de type {@link Integer}.
   *
   * @param variable nom de la variable
   * @return variable d'environnement
   */
  public static int getInt(String variable) {
    try {
      return Integer.parseInt(get(variable));
    } catch (NumberFormatException e) {
      e.printStackTrace();
      LOGGER.error(ERR_GET_INT, variable);
      System.exit(ErrorCodesUtils.ENVIRONMENT_VARIABLE);
    }
    return 0;
  }

  /**
   * Accès à une variable d'environnement de type {@link Boolean}.
   *
   * @param variable nom de la variable
   * @return variable d'environnement
   */
  public static boolean getBoolean(String variable) {
    String var = get(variable).toLowerCase();
    if (var.equals("true")) {
      return true;
    } else if (var.equals("false")) {
      return false;
    } else {
      try {
        throw new ConfigurationException();
      } catch (ConfigurationException e) {
        e.printStackTrace();
        LOGGER.error(ERR_GET_BOOLEAN, variable);
        System.exit(ErrorCodesUtils.ENVIRONMENT_VARIABLE);
      }
    }
    return false;
  }

  /**
   * Accès à une variable d'environnement de type {@link String}.
   *
   * @param variable nom de la variable
   * @return variable d'environnement
   */
  public static String getString(String variable) {
    return get(variable);
  }

  private static String get(String variable) {
    String v = System.getenv(variable);
    return Objects.nonNull(v) ? v : getFromFile(variable);
  }

  private static String getFromFile(String variable) {
    String envFile = getFile();
    try (FileInputStream inputStream = new FileInputStream(envFile)) {
      Properties prop = new Properties();
      prop.load(inputStream);
      return Optional.ofNullable(prop.getProperty(variable))
        .orElseThrow(ConfigurationException::new);
    } catch (IOException ioException) {
      ioException.printStackTrace();
      LOGGER.error(ERR_READ_FILE);
      System.exit(ErrorCodesUtils.ENVIRONMENT_VARIABLE);
    } catch (ConfigurationException configurationException) {
      configurationException.printStackTrace();
      LOGGER.error(ERR_FIND_VAR, variable);
      System.exit(ErrorCodesUtils.ENVIRONMENT_VARIABLE);
    }
    return null;
  }

  private static String getFile() {
    String envFile = null;
    try {
      envFile = EnvironmentVariablesUtils.class.getResource(ENVIRONMENT_VARIABLES_FILE).getFile();
    } catch (NullPointerException e) {
      e.printStackTrace();
      LOGGER.error(ERR_NO_FILE);
      System.exit(ErrorCodesUtils.ENVIRONMENT_VARIABLE);
    }
    return envFile;
  }
}
