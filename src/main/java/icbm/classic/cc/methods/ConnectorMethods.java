package icbm.classic.cc.methods;

import dan200.computercraft.api.lua.ArgumentHelper;
import dan200.computercraft.api.lua.LuaException;
import icbm.classic.api.actions.cause.IActionCause;
import icbm.classic.api.actions.status.IActionStatus;
import icbm.classic.api.missiles.parts.IMissileTarget;
import icbm.classic.cc.PeripheralProvider;
import icbm.classic.cc.builder.Peripheral;
import icbm.classic.content.blocks.launcher.network.ILauncherComponent;
import icbm.classic.content.blocks.launcher.network.LauncherEntry;
import icbm.classic.content.blocks.launcher.network.LauncherNode;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

public class ConnectorMethods {
    public static <T extends TileEntity> Object[] getLaunchers(@Nonnull Peripheral<T> peripheral, @Nonnull Object[] objects) throws LuaException {
        final TileEntity tile = peripheral.getTile();
        if (tile instanceof ILauncherComponent) {

            if(((ILauncherComponent) tile).getNetworkNode().getNetwork() == null) {
                return new Object[] {null, null, "No Network"};
            }

            final List<LauncherEntry> launchers =  ((ILauncherComponent) tile).getNetworkNode().getNetwork().getLaunchers();

            final HashMap<Object, Object> table = new HashMap<>();
            for(int i = 0; i < launchers.size(); i++) {
                final LauncherEntry launcher = launchers.get(i);
                final HashMap<Object, Object> entry = new HashMap<>();
                loadLauncherData(peripheral, launcher, entry);
                table.put(i, entry);
            }

            return new Object[] {table, launchers.size(), null};
        }
        throw new LuaException("tile doesn't support launcher component");
    }

    private static <T extends TileEntity> void loadLauncherData(@Nonnull Peripheral<T> peripheral, LauncherEntry launcher, Map<Object, Object> entry) {
        entry.put("GROUP_ID", launcher.getLauncher().getLauncherGroup());
        entry.put("GROUP_INDEX", launcher.getLauncher().getLaunchIndex());
        entry.put("X", launcher.getHost().getPos().getX());
        entry.put("Y", launcher.getHost().getPos().getY());
        entry.put("Z", launcher.getHost().getPos().getZ());
        entry.put("LAUNCHER", PeripheralProvider.launcher.build(launcher.getHost(), peripheral.getComputerPos()));
    }

    public static <T extends TileEntity> Object[] launch(@Nonnull Peripheral<T> peripheral, @Nonnull Object[] args) throws LuaException {
        final TileEntity tile = peripheral.getTile();
        if (tile instanceof ILauncherComponent) {
            final LauncherNode node = ((ILauncherComponent) tile).getNetworkNode();
            if(node.getNetwork() == null) {
                return new Object[] {null, null, new Object[] {false, false, "icbmclassic:error.network.none", "Firing Safety; No network"}}; //TODO move to ICBM
            }

            final Map<?, ?> table = ArgumentHelper.getTable(args, 0);
            final boolean simulate = ArgumentHelper.getBoolean(args, 1);
            final IMissileTarget targetData = LauncherMethods.getTarget(table);
            final Integer firingGroup = Optional.ofNullable(table.get("group")).map((s) -> (int)s).orElse(null);

            // Filter launchers by group
            final List<LauncherEntry> launchers = node.getNetwork()
                .getLaunchers().stream()
                .filter(entry -> firingGroup == null || firingGroup == entry.getLauncher().getLauncherGroup())
                .collect(Collectors.toList());
            final int count = launchers.size();

            if(count == 0) {
                return new Object[] {null, null, new Object[] {false, false, "icbmclassic:error.group.empty", "Firing Safety; No missiles in group"}}; //TODO move to ICBM
            }

            final IActionCause cause = LauncherMethods.createLaunchCause(peripheral);

            final Map<Object, Object> results = new TreeMap();
            for(int i = 0; i < count; i++) {
                final LauncherEntry launcher = launchers.get(i);
                final IActionStatus status = launcher.getLauncher().launch(targetData, cause, simulate);

                final Map<Object, Object> resultEntry = new TreeMap();
                resultEntry.put("RESULT", LauncherMethods.convertStatus(status));
                loadLauncherData(peripheral, launcher, resultEntry);
                results.put(i, resultEntry);
            }
            return new Object[] {results, count, null};
        }
        throw new LuaException("tile doesn't support launcher component");
    }
}
