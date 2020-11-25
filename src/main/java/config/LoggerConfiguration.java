package config;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Order;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import utils.EnvironmentVariablesUtils;

import java.net.URI;

/**
 * Configuration du {@link Logger}.
 */
@Plugin(name = "CustomConfigurationFactory", category = ConfigurationFactory.CATEGORY)
@Order(50)
public class LoggerConfiguration extends ConfigurationFactory {

  private static final String DEFAULT_LEVEL = "WARN";
  private static final String DEFAULT_PATTERN = "%d{dd-MM-yyyy - HH:mm:ss} [%t] %-6level| %logger{36} : %msg%n";

  private static Configuration createConfiguration(final String name, ConfigurationBuilder<BuiltConfiguration> builder) {
    final String level = EnvironmentVariablesUtils
      .getString(EnvironmentVariablesUtils.LOG_LEVEL, DEFAULT_LEVEL).toUpperCase();
    final String pattern = EnvironmentVariablesUtils
      .getString(EnvironmentVariablesUtils.LOG_FORMAT, DEFAULT_PATTERN);
    final String appender = "stdout";
    builder.setConfigurationName(name);
    builder.setStatusLevel(Level.ERROR);
    AppenderComponentBuilder appenderBuilder = builder.newAppender(appender, "console").
      addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT);
    appenderBuilder.add(builder.newLayout("PatternLayout")
      .addAttribute("pattern", pattern));
    builder
      .add(appenderBuilder)
      .add(builder.newRootLogger(Level.getLevel(level))
        .add(builder.newAppenderRef(appender)));
    return builder.build();
  }

  @Override
  public Configuration getConfiguration(final LoggerContext loggerContext, final ConfigurationSource source) {
    return getConfiguration(loggerContext, source.toString(), null);
  }

  @Override
  public Configuration getConfiguration(final LoggerContext loggerContext, final String name, final URI configLocation) {
    ConfigurationBuilder<BuiltConfiguration> builder = newConfigurationBuilder();
    return createConfiguration(name, builder);
  }

  @Override
  protected String[] getSupportedTypes() {
    return new String[]{"*"};
  }
}