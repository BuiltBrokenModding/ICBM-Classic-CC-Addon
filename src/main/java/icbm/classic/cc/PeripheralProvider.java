package icbm.classic.cc;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import dan200.computercraft.api.peripheral.IPeripheralTile;
import icbm.classic.ICBMConstants;
import icbm.classic.api.ICBMClassicAPI;
import icbm.classic.content.blocks.launcher.base.TileLauncherBase;
import icbm.classic.content.blocks.launcher.cruise.TileCruiseLauncher;
import icbm.classic.content.blocks.radarstation.TileRadarStation;
import icbm.classic.cc.builder.PeripheralBuilder;
import icbm.classic.cc.methods.CommonMethods;
import icbm.classic.cc.methods.EnergyMethods;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class PeripheralProvider implements IPeripheralProvider {


    private final PeripheralBuilder<TileRadarStation> radarBuilder = new PeripheralBuilder<TileRadarStation>(ICBMConstants.PREFIX + "radar.station")
        .withMethod("getEnergyData", EnergyMethods::getEnergyData)
        .withMethod("getEnergy", EnergyMethods::getEnergy)
        .withMethod("getEnergyLimit", EnergyMethods::getEnergyLimit)
        .withMethod("getMachineInfo", CommonMethods::getMachineInfo);

    @Override
    public IPeripheral getPeripheral(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull EnumFacing side) {
        final TileEntity tile = world.getTileEntity(pos);
        final EnumFacing connectedSide = side.getOpposite();
        if(tile == null) {
            return null;
        }
        else if(tile instanceof IPeripheralTile) {
            return ((IPeripheralTile) tile).getPeripheral(side);
        }
        else if(tile instanceof TileRadarStation) {
            return radarBuilder.build((TileRadarStation) tile, connectedSide);
        }
        else if(tile instanceof TileLauncherBase) {
            return new LauncherBasePeripheral((TileLauncherBase) tile, ((TileLauncherBase) tile).missileLauncher, connectedSide);
        }
        else if(tile instanceof TileCruiseLauncher) {
            return new LauncherCruisePeripheral((TileCruiseLauncher) tile, ((TileCruiseLauncher) tile).getLauncher(), connectedSide);
        }
        else if (tile.hasCapability(ICBMClassicAPI.MISSILE_LAUNCHER_CAPABILITY, connectedSide)) {
            return new LauncherPeripheral(tile, tile.getCapability(ICBMClassicAPI.MISSILE_LAUNCHER_CAPABILITY, connectedSide), connectedSide);
        }
        return null;
    }
}
