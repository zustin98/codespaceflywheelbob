package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * La classe Robot est appelée automatiquement par WPILib.
 *
 * <p>Toutes les méthodes "Periodic" sont appelées toutes les 20 millisecondes (50 fois par
 * seconde). Les méthodes "Init" sont appelées une seule fois, quand le robot change de mode
 * (désactivé, autonome, téléopéré, test).
 */
public class Robot extends TimedRobot {
  private final RobotContainer robotContainer;
  private Command autonomousCommand;

  /** Appelé une seule fois, au démarrage du robot. */
  public Robot() {
    // Le RobotContainer crée les sous-systèmes et relie les boutons aux commandes.
    robotContainer = new RobotContainer();

    // En simulation, il n'y a souvent pas de manette branchée : on cache cet avertissement.
    if (isSimulation()) {
      DriverStation.silenceJoystickConnectionWarning(true);
    }
  }

  /** Appelé toutes les 20 ms, peu importe le mode. */
  @Override
  public void robotPeriodic() {
    // Le "planificateur" exécute les commandes actives et la méthode periodic() de chaque
    // sous-système. Sans cette ligne, rien ne bouge !
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void autonomousInit() {
    autonomousCommand = robotContainer.getAutonomousCommand();
    if (autonomousCommand != null) {
      CommandScheduler.getInstance().schedule(autonomousCommand);
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // On arrête la commande autonome quand le pilote prend le contrôle.
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
