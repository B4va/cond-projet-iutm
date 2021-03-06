package controllers.workers;

import controllers.Runner;
import controllers.workers.schedule.ScheduleWorkersHandler;
import controllers.workers.task.TaskWorkersHandler;
import utils.EnvironmentVariablesUtils;

/**
 * Contrôleur permettant d'initialiser la gestion des opérations automatiques.
 */
public class WorkersController extends Runner<WorkersHandler> {

  public static final int DAILY_HOUR = EnvironmentVariablesUtils.getInt(EnvironmentVariablesUtils.DAILY_HOUR, 18);
  public static final int DAILY_MINUTE = EnvironmentVariablesUtils.getInt(EnvironmentVariablesUtils.DAILY_MINUTE, 18);

  @Override
  public Runner<WorkersHandler> init() {
    runnables.add(new ScheduleWorkersHandler().init());
    runnables.add(new TaskWorkersHandler().init());
    return this;
  }
}
