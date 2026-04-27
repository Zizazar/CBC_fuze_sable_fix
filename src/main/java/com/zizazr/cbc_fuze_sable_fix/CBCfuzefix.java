package com.zizazr.cbc_fuze_sable_fix;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(CBCfuzefix.MODID)
public class CBCfuzefix {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "cbc_fuze_sable_fix";


    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public CBCfuzefix(IEventBus modEventBus, ModContainer modContainer) { }
}
