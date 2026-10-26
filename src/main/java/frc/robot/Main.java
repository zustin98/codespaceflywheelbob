package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;

/**
 * Point de départ du programme.
 *
 * <p>Ne modifie pas ce fichier : il sert seulement à démarrer la classe {@link Robot}.
 */
public final class Main {
  private Main() {}

  public static void main(String... args) {
    RobotBase.startRobot(Robot::new);
  }
}
