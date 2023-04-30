package icbm.classic.cc.methods;

import dan200.computercraft.api.lua.ArgumentHelper;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import icbm.classic.cc.builder.Peripheral;
import icbm.classic.content.blocks.launcher.base.TileLauncherBase;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;

@NoArgsConstructor(access = AccessLevel.NONE)
public final class BaseLauncherMethods {

    public static <T extends TileLauncherBase> Object[] getLockHeight(Peripheral<T> peripheral) {
        return new Object[] {peripheral.getTile().getLockHeight()};
    }

    public static <T extends TileLauncherBase> Object[] setLockHeight(@Nonnull Peripheral<T> peripheral, @Nonnull IComputerAccess computer, @Nonnull ILuaContext context, @Nonnull Object[] args) throws LuaException, InterruptedException {
        final int height = ArgumentHelper.getInt(args, 0);
        return context.executeMainThreadTask(() -> {
            peripheral.getTile().setLockHeight(height);
            return null;
        });
    }

    public static <T extends TileLauncherBase> Object[] getFiringDelay(Peripheral<T> peripheral) {
        return new Object[] {peripheral.getTile().getFiringDelay()};
    }

    public static <T extends TileLauncherBase> Object[] setFiringDelay(@Nonnull Peripheral<T> peripheral, @Nonnull IComputerAccess computer, @Nonnull ILuaContext context, @Nonnull Object[] args) throws LuaException, InterruptedException {
        final int height = ArgumentHelper.getInt(args, 0);
        return context.executeMainThreadTask(() -> {
            peripheral.getTile().setFiringDelay(height);
            return null;
        });
    }
}
