package controllers.workers.schedule;

import controllers.workers.IntervalWorker;
import process.data.SchedulesUpdateProcess;

/**
 * Gère la mise à jour régulière des données relative aux emplois du temps
 * via les services de l'IUT.
 */
public class ScheduleUpdateWorker extends IntervalWorker {

  /**
   * Constructeur.
   *
   * @param interval délai entre chaque exécution (en millisecondes)
   * @param delay    délai avant lancement (en millisecondes)
   */
  public ScheduleUpdateWorker(long interval, long delay) {
    super(interval, delay);
  }

  @Override
  public void runOne() {
    new SchedulesUpdateProcess().update();
  }

  @Override
  protected String getTask() {
    return "Mise à jour des edt";
  }
}