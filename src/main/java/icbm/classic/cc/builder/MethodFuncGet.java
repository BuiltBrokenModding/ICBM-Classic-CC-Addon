package icbm.classic.cc.builder;

import dan200.computercraft.api.lua.LuaException;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface MethodFuncGet<T extends TileEntity> {

    Object[] apply(@Nonnull Peripheral<T> peripheral) throws LuaException, InterruptedException;
}
