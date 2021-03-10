package process.schedule.data;

import models.Model;
import models.Session;

import java.util.List;

/**
 * Process de supression des cours mis à jour.
 */
public class SessionsPurgeProcess {

  /**
   * Supprime les cours ayant le statut 'mis à jour'.
   */
  public void purge() {
    List<Session> sessions = Model.readAll(Session.class);
    sessions.stream()
      .filter(Session::isUpdated)
      .forEach(Session::delete);
  }
}
