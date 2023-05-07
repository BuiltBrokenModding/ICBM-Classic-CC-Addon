package icbm.classic.cc.methods;

import dan200.computercraft.api.lua.ArgumentHelper;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import icbm.classic.api.ICBMClassicAPI;
import icbm.classic.api.caps.IMissileHolder;
import icbm.classic.api.launcher.IActionStatus;
import icbm.classic.api.launcher.IMissileLauncher;
import icbm.classic.api.missiles.ICapabilityMissileStack;
import icbm.classic.api.missiles.cause.IMissileCause;
import icbm.classic.api.missiles.parts.IMissileTarget;
import icbm.classic.cc.builder.Peripheral;
import icbm.classic.content.missile.logic.source.cause.BlockCause;
import icbm.classic.content.missile.logic.targeting.BasicTargetData;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.NONE)
public final class LauncherMethods {
    //"getMissiles", "launch", "getInaccuracy", "getStatus", "preCheckLaunch"



    public static <T extends TileEntity> Object[] getMissiles(Peripheral<T> peripheral) {
        if (peripheral.getTile().hasCapability(ICBMClassicAPI.MISSILE_HOLDER_CAPABILITY, peripheral.getAccessedSide())) {
            final IMissileHolder holder = peripheral.getTile().getCapability(ICBMClassicAPI.MISSILE_HOLDER_CAPABILITY, peripheral.getAccessedSide());
            if (holder != null) {
                final ItemStack missileStack = holder.getMissileStack();
                if (missileStack.isEmpty()) {
                    return new Object[]{"empty"};
                } else if (missileStack.hasCapability(ICBMClassicAPI.MISSILE_STACK_CAPABILITY, peripheral.getAccessedSide())) {
                    final ICapabilityMissileStack stackCap = missileStack.getCapability(ICBMClassicAPI.MISSILE_STACK_CAPABILITY, peripheral.getAccessedSide());
                    if (stackCap != null) {
                        return new Object[]{stackCap.getMissileId()};
                    }
                }
                return new Object[]{missileStack.toString()};
            }
        }
        return new Object[]{"not supported"}; //TODO maybe don't include in method list?
    }

    public static <T extends TileEntity> Object[] getStatus(Peripheral<T> peripheral) throws LuaException {
        final IMissileLauncher launcher = getLauncher(peripheral);
        return convertStatus(launcher.getStatus());
    }

    public static <T extends TileEntity> Object[] launchMissile(@Nonnull Peripheral<T> peripheral, @Nonnull IComputerAccess computer, @Nonnull ILuaContext context, @Nonnull Object[] args) throws LuaException, InterruptedException {
        //TODO once ported over to addon, add a delay into the launcher network to force player to do launcherNetwork.fire(launchers) vs one at a time
        final Map<?, ?> table = ArgumentHelper.getTable(args, 0);
        final boolean simulate = ArgumentHelper.getBoolean(args, 1);
        final IMissileTarget targetData = getTarget(table);
        final IMissileLauncher launcher = getLauncher(peripheral);

        return context.executeMainThreadTask(() -> {
            final IMissileCause cause = createLaunchCause(peripheral);
            final IActionStatus status = launcher.launch(targetData, cause, simulate);
            return convertStatus(status);
        });
    }

    public static <T extends TileEntity> IMissileLauncher getLauncher(@Nonnull Peripheral<T> peripheral) throws LuaException {
        if(!peripheral.getTile().hasCapability(ICBMClassicAPI.MISSILE_LAUNCHER_CAPABILITY, peripheral.getAccessedSide())) {
            throw new LuaException("tile is missing launcher capability support");
        }

        final IMissileLauncher launcher = peripheral.getTile().getCapability(ICBMClassicAPI.MISSILE_LAUNCHER_CAPABILITY, peripheral.getAccessedSide());
        if(launcher == null) {
            throw new LuaException("tile is missing launcher capability");
        }
        return launcher;
    }

    public static <T extends TileEntity> Object[]  getInaccuracy(@Nonnull Peripheral<T> peripheral, @Nonnull Object[] objects) throws LuaException {
        final Map<?, ?> table = ArgumentHelper.getTable(objects, 0);
        final IMissileTarget targetData = getTarget(table);
        final int launcherCount = ArgumentHelper.optInt(objects, 1, 1);
        final IMissileLauncher launcher = getLauncher(peripheral);
        return new Object[] {launcher.getInaccuracy(targetData.getPosition(), launcherCount)};
    }

    public static <T extends TileEntity> Object[]  preLaunchCheck(@Nonnull Peripheral<T> peripheral, @Nonnull Object[] objects) throws LuaException {
        final Map<?, ?> table = ArgumentHelper.getTable(objects, 0);
        final IMissileTarget targetData = getTarget(table);
        final IMissileCause cause = createLaunchCause(peripheral);
        final IMissileLauncher launcher = getLauncher(peripheral);
        return convertStatus(launcher.preCheckLaunch(targetData, cause));
    }

    public static <T extends TileEntity> IMissileCause createLaunchCause(@Nonnull Peripheral<T> peripheral) {
        final BlockPos sourceBlock = peripheral.getComputerPos();
        return new BlockCause(peripheral.getTile().getWorld(), sourceBlock, peripheral.getTile().getWorld().getBlockState(sourceBlock));
    }

    public static Object[] convertStatus(IActionStatus status) {
        return new Object[]{status.isError(), status.shouldBlockInteraction(), status.getRegistryName().toString(), status.message().getFormattedText()};
    }

    public static IMissileTarget getTarget(Map<?, ?> table) throws LuaException {
        final double x = MethodHelpers.getNumeric(table, "x", "Error: failed to get x value...");
        final double y = MethodHelpers.getNumeric(table, "y", "Error: failed to get y value...");
        final double z = MethodHelpers.getNumeric(table, "z", "Error: failed to get z value...");
        final int delay = (int)Math.floor(MethodHelpers.getNumeric(table, "delay", "Error: failed to get delay value...", 0));
        return new BasicTargetData(x, y, z).withFiringDelay(delay);
    }
}
