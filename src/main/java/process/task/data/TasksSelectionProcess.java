package process.task.data;

import models.Model;
import models.Server;
import models.Task;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Process de sélection des {@link Task} associées à un {@link Server}.
 */
public class TasksSelectionProcess {

  /**
   * Sélectionne les tâches associées à un serveur.
   *
   * @param server serveur associé
   * @param days   période à publier, en jours à partir de la date d'aujourd'hui
   * @return liste des tâches associées au serveur
   */
  public List<Task> select(Server server, int days) {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_MONTH, -1);
    Date after = calendar.getTime();
    calendar.add(Calendar.DAY_OF_MONTH, days + 1);
    Date before = calendar.getTime();
    return Model.readAll(Task.class)
      .stream()
      .filter(t -> t.getServer().getId() == server.getId())
      .filter(t -> t.getDueDate().after(after))
      .filter(t -> days == -1 || t.getDueDate().before(before))
      .collect(Collectors.toList());
  }

  /**
   * Sélectionne les tâches associées à un serveur.
   *
   * @param server serveur associé
   * @return liste des tâches associées au serveur
   */
  public List<Task> select(Server server) {
    return select(server, -1);
  }
}
