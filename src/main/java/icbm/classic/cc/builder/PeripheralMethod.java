package icbm.classic.cc.builder;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import lombok.Data;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;

@Data
public abstract class PeripheralMethod<T> {
    private final String name;

    /**
     * Method to invoke
     *
     * @param peripheral this method was called against
     * @param computer to access, may be null if peripheral is being used as an {@link dan200.computercraft.api.lua.ILuaObject}
     * @param context of the method call
     * @param args pass into the call
     * @return data
     *
     * @throws LuaException
     * @throws InterruptedException
     */
    public abstract Object[] invoke(@Nonnull Peripheral<T> peripheral, IComputerAccess computer, @Nonnull ILuaContext context, @Nonnull Object[] args) throws LuaException, InterruptedException;
}
