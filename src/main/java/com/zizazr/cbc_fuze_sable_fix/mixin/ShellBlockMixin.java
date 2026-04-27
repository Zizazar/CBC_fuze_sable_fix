package com.zizazr.cbc_fuze_sable_fix.mixin;

import com.zizazr.cbc_fuze_sable_fix.ShellCallback;
import dev.ryanhcode.sable.api.block.BlockWithSubLevelCollisionCallback;
import dev.ryanhcode.sable.api.physics.callback.BlockSubLevelCollisionCallback;
import org.spongepowered.asm.mixin.Mixin;
import rbasamoyai.createbigcannons.munitions.big_cannon.SimpleShellBlock;

@Mixin(SimpleShellBlock.class)
public class ShellBlockMixin implements BlockWithSubLevelCollisionCallback {
    @Override
    public BlockSubLevelCollisionCallback sable$getCallback() {
        return ShellCallback.INSTANCE;
    }
}
