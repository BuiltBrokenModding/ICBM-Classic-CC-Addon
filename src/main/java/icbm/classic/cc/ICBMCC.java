package icbm.classic.cc;

import dan200.computercraft.api.ComputerCraftAPI;
import icbm.classic.ICBMConstants;
import icbm.classic.oc.DriverLauncher;
import li.cil.oc.api.Driver;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Mod class for ICBM Classic, contains all loading code and references to objects crated by the mod.
 *
 * @author Dark(DarkGuardsman, Robert).
 * <p>
 * Orginal author and creator of the mod: Calclavia
 */
@Mod(modid = ICBMCC.DOMAIN, name = "ICBM-CC-Addon", dependencies = ICBMCC.DEPS)
@Mod.EventBusSubscriber
public class ICBMCC
{
    public static final String DOMAIN = "icbmcc";
    public static final String DEPS = "required-after:icbmclassic@[6.0.1,);required-after:computercraft@[1.19.2,)";

    private static final Logger logger = LogManager.getLogger(ICBMConstants.DOMAIN);


    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        ComputerCraftAPI.registerPeripheralProvider(new PeripheralProvider());

        Driver.add(new DriverLauncher());
    }

    public static Logger logger()
    {
        return logger;
    }
}
