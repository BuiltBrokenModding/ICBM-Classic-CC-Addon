package icbm.classic.cc;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import icbm.classic.ICBMConstants;
import icbm.classic.cc.methods.*;
import icbm.classic.content.blocks.launcher.base.TileLauncherBase;
import icbm.classic.content.blocks.launcher.cruise.TileCruiseLauncher;
import icbm.classic.content.blocks.radarstation.TileRadarStation;
import icbm.classic.cc.builder.PeripheralBuilder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class PeripheralProvider implements IPeripheralProvider {

    private final PeripheralBuilder<TileEntity> machine = new PeripheralBuilder<TileEntity>(ICBMConstants.PREFIX + "machine")
        // Common Machine
        .withMethod("getEnergyData", EnergyMethods::getEnergyData)
        .withMethod("getEnergy", EnergyMethods::getEnergy)
        .withMethod("getEnergyLimit", EnergyMethods::getEnergyLimit)
        .withMethod("getMachineInfo", CommonMethods::getMachineInfo);


    private final PeripheralBuilder<TileRadarStation> radarBuilder = machine.copy(ICBMConstants.PREFIX + "radar.station", TileRadarStation.class)
        .withMethod("getDetectionRange", RadarMethods::getDetectionRange)
        .withMethod("setDetectionRange", RadarMethods::setDetectionRange)
        .withMethod("getTriggerRange", RadarMethods::getTriggerRange)
        .withMethod("setTriggerRange", RadarMethods::setTriggerRange)
        .withMethod("getContacts", RadarMethods::getContacts);

    private final PeripheralBuilder<TileEntity> launcher = machine.copy(ICBMConstants.PREFIX + "launcher", TileEntity.class)
        .withMethod("getMissiles", LauncherMethods::getMissiles)
        .withMethod("launch", LauncherMethods::launchMissile)
        .withMethod("getInaccuracy", LauncherMethods::getInaccuracy)
        .withMethod("getStatus", LauncherMethods::getStatus)
        .withMethod("preCheckLaunch", LauncherMethods::preLaunchCheck);

    private final PeripheralBuilder<TileCruiseLauncher> cruiseLauncher = launcher.copy(ICBMConstants.PREFIX + "launcher.cruise", TileCruiseLauncher.class)
        .withMethod("getTarget", CruiseLauncherMethods::getTarget)
        .withMethod("setTarget", CruiseLauncherMethods::setTarget)
        .withMethod("isAimed", CruiseLauncherMethods::isAimed)
        .withMethod("getAimCurrent", CruiseLauncherMethods::getCurrentAim)
        .withMethod("getAimTarget", CruiseLauncherMethods::getCurrentAim);

    private final PeripheralBuilder<TileLauncherBase> baseLauncher = launcher.copy(ICBMConstants.PREFIX + "launcher.pad", TileLauncherBase.class)
        .withMethod("getLockHeight", BaseLauncherMethods::getLockHeight)
        .withMethod("setLockHeight", BaseLauncherMethods::setLockHeight)
        .withMethod("getFiringDelay", BaseLauncherMethods::getFiringDelay)
        .withMethod("setFiringDelay", BaseLauncherMethods::setFiringDelay);

    @Override
    public IPeripheral getPeripheral(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull EnumFacing side) {
        final TileEntity tile = world.getTileEntity(pos);
        final EnumFacing connectedSide = side.getOpposite();
        if(tile instanceof TileRadarStation) {
            return radarBuilder.build((TileRadarStation) tile, connectedSide);
        }
        else if(tile instanceof TileLauncherBase) {
            return baseLauncher.build((TileLauncherBase) tile, connectedSide);
        }
        else if(tile instanceof TileCruiseLauncher) {
            return cruiseLauncher.build((TileCruiseLauncher) tile, connectedSide);
        }
        return null;
    }
}
