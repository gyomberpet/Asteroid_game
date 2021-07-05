package kakaoscsiga.model;

import kakaoscsiga.model.entity.Robot;

import java.util.List;

public interface Build {
    /**
     * A teleportkapu epiteset valositja meg
     * @return megepitett telepotkapuk
     */
    List<TeleportGatePair> buildTeleportGate();

    /**
     * Robot epiteset valositja meg
     * @return megepitett robot
     */
    Robot buildRobot();

    /**
     * Bazis epiteset valositja meg
     */
    boolean buildBase();
}
