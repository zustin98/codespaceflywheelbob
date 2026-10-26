package frc.robot;

/**
 * Constantes générales du robot.
 *
 * <p>Les constantes propres à un sous-système (moteur, vitesses, gains...) sont rangées dans
 * le dossier de ce sous-système. Exemple : {@code subsystems/flywheel/FlywheelConstants.java}.
 */
public final class Constants {
  private Constants() {}

  /** Constantes liées au pilote et aux manettes. */
  public static final class OperatorConstants {
    private OperatorConstants() {}

    // Numéro du port USB de la manette dans la Driver Station (0 = première manette).
    public static final int DRIVER_CONTROLLER_PORT = 0;
  }
}
