package icbm.classic.cc.methods;


import dan200.computercraft.api.lua.ArgumentHelper;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import icbm.classic.cc.builder.Peripheral;
import icbm.classic.content.blocks.launcher.cruise.TileCruiseLauncher;
import icbm.classic.lib.transform.rotation.EulerAngle;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nonnull;

@NoArgsConstructor(access = AccessLevel.NONE)
public final class CruiseLauncherMethods {
    public static <T extends TileCruiseLauncher> Object[] getTarget(Peripheral<T> peripheral) {
        final Vec3d target = peripheral.getTile().getTarget();
        return new Object[] {target.x, target.y, target.z};
    }

    public static <T extends TileCruiseLauncher> Object[] setTarget(@Nonnull Peripheral<T> peripheral, @Nonnull IComputerAccess computer, @Nonnull ILuaContext context, @Nonnull Object[] args) throws LuaException, InterruptedException {
        final double x = ArgumentHelper.getDouble(args, 0);
        final double y = ArgumentHelper.getDouble(args, 1);
        final double z = ArgumentHelper.getDouble(args, 2);
        final Vec3d target = new Vec3d(x, y, z);
        return context.executeMainThreadTask(() -> {
            peripheral.getTile().setTarget(target);
            return null;
        });
    }

    public static <T extends TileCruiseLauncher> Object[] isAimed(Peripheral<T> peripheral) {
        return new Object[] {peripheral.getTile().isAimed()};
    }

    public static <T extends TileCruiseLauncher> Object[] getCurrentAim(Peripheral<T> peripheral, Object[] args) throws LuaException {
        final boolean toRadians = ArgumentHelper.optBoolean(args, 0, false);
        final EulerAngle aim = peripheral.getTile().getCurrentAim();
        if(toRadians) {
            return new Object[] {aim.yaw_radian(), aim.pitch_radian(), aim.roll_radian()};
        }
        return new Object[] {aim.yaw(), aim.pitch(), aim.roll()};
    }

    public static <T extends TileCruiseLauncher> Object[] getTargetAim(Peripheral<T> peripheral, Object[] args) throws LuaException {
        final boolean toRadians = ArgumentHelper.optBoolean(args, 0, false);
        final EulerAngle aim = peripheral.getTile().getAim();
        if(toRadians) {
            return new Object[] {aim.yaw_radian(), aim.pitch_radian(), aim.roll_radian()};
        }
        return new Object[] {aim.yaw(), aim.pitch(), aim.roll()};
    }
}
