package icbm.classic.cc.methods;

import icbm.classic.cc.builder.Peripheral;
import icbm.classic.lib.data.IMachineInfo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.NONE)
public final class CommonMethods {

    /**
     * Information about the machine that may not be needed as single methods in CC
     *
     * @param peripheral containing machine information
     * @return data
     * @param <T> tile
     */
    public static <T extends TileEntity> Object[] getMachineInfo(Peripheral<T> peripheral) {
        final T tile = peripheral.getTile();
        final Map<Object, Object> table = new HashMap<>();
        if(tile instanceof IMachineInfo) {
           ((IMachineInfo) tile).provideInformation(table::put);
        }
        if(tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, peripheral.getAccessedSide())) {
           final IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, peripheral.getAccessedSide());
           if(handler != null) {
               table.put("INVENTORY_SIZE", handler.getSlots());
           }
        }
        return new Object[]{table};
    }
}
