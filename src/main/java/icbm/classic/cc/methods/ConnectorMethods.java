package icbm.classic.cc.methods;

import dan200.computercraft.api.lua.ArgumentHelper;
import dan200.computercraft.api.lua.LuaException;
import icbm.classic.api.launcher.IMissileLauncher;
import icbm.classic.api.missiles.parts.IMissileTarget;
import icbm.classic.cc.PeripheralProvider;
import icbm.classic.cc.builder.Peripheral;
import icbm.classic.cc.builder.PeripheralBuilder;
import icbm.classic.content.blocks.launcher.network.ILauncherComponent;
import icbm.classic.content.blocks.launcher.network.LauncherEntry;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectorMethods {
    public static <T extends TileEntity> Object[] getLaunchers(@Nonnull Peripheral<T> peripheral, @Nonnull Object[] objects) throws LuaException {
        final TileEntity tile = peripheral.getTile();
        if (tile instanceof ILauncherComponent) {

            if(((ILauncherComponent) tile).getNetworkNode() == null) {
                return new Object[] {null, null, "No Network"};
            }

            final List<LauncherEntry> launchers =  ((ILauncherComponent) tile).getLaunchers();

            final HashMap<Object, Object> table = new HashMap<>();
            for(int i = 0; i < launchers.size(); i++) {
                final LauncherEntry launcher = launchers.get(i);
                final HashMap<Object, Object> entry = new HashMap<>();

                entry.put("GROUP_ID", launcher.getLauncher().getLauncherGroup());
                entry.put("GROUP_INDEX", launcher.getLauncher().getLaunchIndex());
                entry.put("LAUNCHER", PeripheralProvider.launcher.build(launcher.getHost(), peripheral.getComputerPos()));

                table.put(i, entry);
            }

            return new Object[] {table, launchers.size(), null};
        }
        throw new LuaException("tile doesn't support launcher component");
    }
}
