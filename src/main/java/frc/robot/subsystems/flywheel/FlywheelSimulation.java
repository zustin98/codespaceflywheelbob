package frc.robot.subsystems.flywheel;

import com.revrobotics.sim.SparkMaxSim;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.BatterySim;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;

/**
 * FICHIER FOURNI : tu n'as pas besoin de le modifier.
 *
 * <p>Cette classe imite la physique d'un vrai volant d'inertie pour qu'on puisse tester notre
 * code sans robot. À chaque cycle (20 ms), elle lit la tension que le SPARK MAX envoie au
 * moteur, calcule la nouvelle vitesse du volant, puis renvoie cette vitesse à l'encodeur
 * simulé.
 */
public class FlywheelSimulation {
  // Durée d'un cycle du robot : 20 ms = 0,020 s.
  private static final double LOOP_PERIOD_SECONDS = 0.020;

  private final DCMotor gearbox = DCMotor.getNEO(1);
  private final SparkMaxSim motorSim;
  private final FlywheelSim flywheelSim;

  /**
   * @param motor le SPARK MAX du volant
   * @param gearRatio nombre de tours du moteur pour 1 tour du volant
   * @param momentOfInertiaKgM2 moment d'inertie du volant, en kg·m²
   */
  public FlywheelSimulation(SparkMax motor, double gearRatio, double momentOfInertiaKgM2) {
    motorSim = new SparkMaxSim(motor, gearbox);
    flywheelSim =
        new FlywheelSim(
            LinearSystemId.createFlywheelSystem(gearbox, momentOfInertiaKgM2, gearRatio),
            gearbox);
  }

  /** À appeler toutes les 20 ms, seulement en simulation. */
  public void update() {
    double batteryVolts = RoboRioSim.getVInVoltage();

    // 1. Tension envoyée au moteur = (sortie du SPARK MAX, entre -1 et 1) x tension batterie.
    flywheelSim.setInputVoltage(motorSim.getAppliedOutput() * batteryVolts);

    // 2. On fait avancer la physique de 20 ms.
    flywheelSim.update(LOOP_PERIOD_SECONDS);

    // 3. On donne la nouvelle vitesse du volant au SPARK MAX simulé (son encodeur).
    motorSim.iterate(flywheelSim.getAngularVelocityRPM(), batteryVolts, LOOP_PERIOD_SECONDS);

    // 4. La batterie baisse un peu quand le moteur consomme du courant.
    RoboRioSim.setVInVoltage(
        BatterySim.calculateDefaultBatteryLoadedVoltage(flywheelSim.getCurrentDrawAmps()));
  }
}
