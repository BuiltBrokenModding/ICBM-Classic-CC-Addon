package icbm.classic.cc.methods;

import dan200.computercraft.api.lua.ArgumentHelper;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import icbm.classic.api.missiles.IMissile;
import icbm.classic.cc.builder.Peripheral;
import icbm.classic.content.blocks.radarstation.TileRadarStation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.entity.Entity;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.NONE)
public final class RadarMethods {
    public static <T extends TileRadarStation> Object[] getDetectionRange(Peripheral<T> peripheral) throws LuaException {
        return new Object[]{peripheral.getTile().getDetectionRange()};
    }

    public static <T extends TileRadarStation> Object[] setDetectionRange(@Nonnull Peripheral<T> peripheral, @Nonnull IComputerAccess computer, @Nonnull ILuaContext context, @Nonnull Object[] args) throws LuaException, InterruptedException {
        final int range = ArgumentHelper.getInt(args, 0);
        return context.executeMainThreadTask(() -> {
            peripheral.getTile().setDetectionRange(range);
            return null;
        });
    }

    public static <T extends TileRadarStation> Object[] getTriggerRange(Peripheral<T> peripheral) throws LuaException {
        return new Object[]{peripheral.getTile().getTriggerRange()};
    }

    public static <T extends TileRadarStation> Object[] setTriggerRange(@Nonnull Peripheral<T> peripheral, @Nonnull IComputerAccess computer, @Nonnull ILuaContext context, @Nonnull Object[] args) throws LuaException, InterruptedException {
        final int range = ArgumentHelper.getInt(args, 0);
        return context.executeMainThreadTask(() -> {
            peripheral.getTile().setTriggerRange(range);
            return null;
        });
    }

    public static <T extends TileRadarStation> Object[] getContacts(@Nonnull Peripheral<T> peripheral, @Nonnull IComputerAccess computer, @Nonnull ILuaContext context, @Nonnull Object[] args) throws LuaException, InterruptedException {
        final TileRadarStation tile = peripheral.getTile();

        final boolean triggerArea = ArgumentHelper.getBoolean(args, 0);
        final int limit = ArgumentHelper.optInt(args, 1, 10);
        if(limit <= 0) {
            throw new LuaException("entry limit must be greater than 0");
        }
        return context.executeMainThreadTask(() -> {
            final List<Entity> entities = triggerArea
                ? tile.getIncomingThreats().stream().map(IMissile::getMissileEntity).limit(limit).collect(Collectors.toList())
                : tile.getDetectedThreats();
            final int count = triggerArea ? tile.getIncomingThreats().size() : tile.getDetectedThreats().size();

            // Collect entities
            final Map<Integer, Object> entityTable = new TreeMap<>(Integer::compareTo);
            for(int i = 0; i < entities.size() && i < limit; i++) {
                final Entity entity = entities.get(i);
                final Map<Object, Object> entryTable = new TreeMap<>();

                // Limit information to what a radar IRL can see (position, size, material)
                entryTable.put("SIZE_H", entity.height);
                entryTable.put("SIZE_W", entity.width);
                entryTable.put("X", entity.posX);
                entryTable.put("Y", entity.posY);
                entryTable.put("Z", entity.posZ);

                //add entry
                entityTable.put( i, entryTable);
            }
            return new Object[] {entityTable, entityTable.size(), count};
        });
    }
}
