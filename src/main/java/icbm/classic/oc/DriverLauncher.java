package icbm.classic.oc;

import dan200.computercraft.api.lua.LuaException;
import icbm.classic.api.launcher.IMissileLauncher;
import icbm.classic.cc.builder.Peripheral;
import icbm.classic.cc.methods.LauncherMethods;
import icbm.classic.content.blocks.launcher.base.TileLauncherBase;
import li.cil.oc.api.driver.NamedBlock;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.ManagedEnvironment;
import li.cil.oc.api.prefab.DriverSidedTileEntity;
import li.cil.oc.integration.ManagedTileEntityEnvironment;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DriverLauncher extends DriverSidedTileEntity {
    @Override
    public Class<?> getTileEntityClass() {
        return TileLauncherBase.class;
    }


    @Override
    public ManagedEnvironment createEnvironment(World world, BlockPos blockPos, EnumFacing enumFacing) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if(tileEntity instanceof TileLauncherBase) {
            return new Enviroment((TileLauncherBase) tileEntity);
        }
        return null;
    }

    static final class Enviroment extends ManagedTileEntityEnvironment<TileLauncherBase> implements NamedBlock {

        Enviroment(TileLauncherBase tileLauncherBase) {
            super(tileLauncherBase, "icbm_launcher_base");
        }

        @Override
        public int priority() {
            return 100;
        }

        @Override
        public String preferredName() {
            return "icbm_launcher_base";
        }

        @Callback(value = "getLauncherStatus", doc = "function():boolean,boolean,string,string -- Checks the status of the launcher returning isError,isBlocking,messageKey,messageTranslated")
        public <T extends TileEntity> Object[] getStatus(final Context context, final Arguments args) {
            return LauncherMethods.convertStatus(this.tileEntity.missileLauncher.getStatus());
        }
    }
}
