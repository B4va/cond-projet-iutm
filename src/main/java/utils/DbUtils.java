package utils;

import config.HibernateConfiguration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;

import javax.persistence.EntityManager;
import java.util.Optional;

/**
 * Gestion de l'accès à la base de données.
 */
public class DbUtils {

  private static SessionFactory sessionFactory;

  private DbUtils() {
  }

  /**
   * Instancie et/ou retourne la {@link SessionFactory}.
   *
   * @return singleton SessionFactory
   */
  public static synchronized SessionFactory getSessionFactory() {
    Configuration configuration = HibernateConfiguration.getConfiguration();
    return Optional.ofNullable(sessionFactory)
      .orElse(configuration.buildSessionFactory());
  }
}
