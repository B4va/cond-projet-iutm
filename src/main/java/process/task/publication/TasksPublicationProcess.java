package process.task.publication;

import models.Model;
import models.Server;
import models.Task;
import process.commons.Publication;
import process.task.data.TasksSelectionProcess;

import java.util.List;

import static java.util.Objects.nonNull;

/**
 * Process de publication de la liste des tâches.
 */
public class TasksPublicationProcess extends Publication {

  /**
   * Publie la liste des tâches en cours.
   *
   * @param channel   channel dans lequel le message doit être publié
   * @param serverRef serveur associé aux tâches
   * @param daysAfter période à publier, en jours à partir de la date d'aujourd'hui
   * @return true si le message a pu être publié
   */
  public boolean sendPublication(String channel, String serverRef, int daysAfter) {
    boolean res = false;
    Server server = Model.readAll(Server.class).stream()
      .filter(s -> s.getReference().equals(serverRef))
      .findAny()
      .orElse(null);
    if (nonNull(server)) {
      List<Task> tasks = new TasksSelectionProcess().select(server, daysAfter);
      String message = new TasksFormattingProcess().format(tasks);
      if (sendMessage(message, server, channel)) res = true;
    }
    return res;
  }

  /**
   * Publie la liste des tâches en cours.
   *
   * @param channel   channel dans lequel le message doit être publié
   * @param serverRef serveur associé aux tâches
   * @return true si le message a pu être publié
   */
  public boolean sendPublication(String channel, String serverRef) {
    return sendPublication(channel, serverRef, -1);
  }
}
