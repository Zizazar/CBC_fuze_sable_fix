package com.zizazr.cbc_fuze_sable_fix;

import dev.ryanhcode.sable.companion.math.JOMLConversion;
import dev.ryanhcode.sable.physics.callback.FragileBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3d;
import rbasamoyai.createbigcannons.munitions.big_cannon.*;
import rbasamoyai.createbigcannons.munitions.fuzes.ImpactFuzeItem;
import rbasamoyai.createbigcannons.munitions.fuzes.InertiaFuzeItem;

public class ShellCallback extends FragileBlockCallback {
    public static final ShellCallback INSTANCE = new ShellCallback();

    public ShellCallback() {}

    @Override
    public boolean shouldTriggerFor(final BlockState state) {
        return state.getBlock() instanceof SimpleShellBlock;
    }

    @Override
    public double getTriggerVelocity() {
        return 5.0;
    }

    @Override
    public CollisionResult onHit(final ServerLevel level, final BlockPos pos, final BlockState state, final Vector3d hitPos) {

        if (state.getBlock() instanceof FuzedProjectileBlock fuzedProjectileBlock) {
            BlockEntity be = fuzedProjectileBlock.getBlockEntity(level, pos);

            if (be instanceof FuzedBlockEntity fuzedProjectileBlockEntity) {
                if (fuzedProjectileBlockEntity.getFuze().getItem() instanceof ImpactFuzeItem ||
                        fuzedProjectileBlockEntity.getFuze().getItem() instanceof InertiaFuzeItem) {

                    fuzedProjectileBlock.detonateProjectileOnTheSpot(level, pos, state, Direction.DOWN);
                    return new CollisionResult(JOMLConversion.ZERO, true);
                }
            }
        }

        return new CollisionResult(JOMLConversion.ZERO, false);
    }
}
