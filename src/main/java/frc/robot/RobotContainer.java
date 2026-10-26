package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.OperatorConstants;

/**
 * Le RobotContainer "assemble" le robot : il crée les sous-systèmes et relie les boutons de la
 * manette aux commandes.
 */
public class RobotContainer {
  // Les sous-systèmes du robot.
  // TODO (séance 4) : créer le sous-système du volant ici.

  // La manette du pilote.
  private final CommandXboxController driverController =
      new CommandXboxController(OperatorConstants.DRIVER_CONTROLLER_PORT);

  public RobotContainer() {
    configureBindings();

    // TODO (séance 5) : ajouter un bouton "Lancer le volant" dans le tableau de bord.
  }

  /** Relie les boutons de la manette aux commandes. */
  private void configureBindings() {
    // TODO (séance 5) : quand le bouton A est enfoncé, faire tourner le volant.
  }

  /** La commande à exécuter en mode autonome. Aucune pour l'instant. */
  public Command getAutonomousCommand() {
    return Commands.none();
  }
}
