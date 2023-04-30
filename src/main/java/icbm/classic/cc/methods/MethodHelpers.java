package icbm.classic.cc.methods;

import dan200.computercraft.api.lua.LuaException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.NONE)
public final class MethodHelpers {
    public static double getNumeric(Map<?, ?> table, String key, String errorPrefix) throws LuaException {

        if(!table.containsKey(key)) {
            throw new LuaException(errorPrefix + " not contained in table");
        }

        final Object value = table.get(key);

        if (!(value instanceof Number)) {
            throw new LuaException(errorPrefix + " not a numeric type");
        }
        final double d = ((Number) value).doubleValue();
        if (Double.isNaN(d)) {
            throw new LuaException(errorPrefix + " value is NaN");
        }
        if (!Double.isFinite(d)) {
            throw new LuaException(errorPrefix + " value is finite");
        }

        return d;
    }
}
