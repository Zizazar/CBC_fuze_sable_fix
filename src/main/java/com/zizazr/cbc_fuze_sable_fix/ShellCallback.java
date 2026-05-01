package com.zizazr.cbc_fuze_sable_fix;

import dev.ryanhcode.sable.companion.math.JOMLConversion;
import dev.ryanhcode.sable.physics.callback.FragileBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.joml.Vector3d;
import rbasamoyai.createbigcannons.munitions.AbstractCannonProjectile;
import rbasamoyai.createbigcannons.munitions.big_cannon.*;
import rbasamoyai.createbigcannons.munitions.fuzes.FuzeItem;

public class ShellCallback extends FragileBlockCallback {
    public static final ShellCallback INSTANCE = new ShellCallback();

    public ShellCallback() {}

    @Override
    public boolean shouldTriggerFor(final BlockState state) {
        return state.getBlock() instanceof SimpleShellBlock;
    }

    @Override
    public double getTriggerVelocity() {
        return 6.0;
    }

    @Override
    public CollisionResult onHit(final ServerLevel level, final BlockPos pos, final BlockState state, final Vector3d hitPos) {

        if (!(state.getBlock() instanceof FuzedProjectileBlock projectileBlock)) {
            return new CollisionResult(JOMLConversion.ZERO, false);
        }

        BlockEntity be = projectileBlock.getBlockEntity(level, pos);
        if (!(be instanceof FuzedBlockEntity fuzedBe)) {
            return new CollisionResult(JOMLConversion.ZERO, false);
        }

        ItemStack fuzeStack = fuzedBe.getFuze();
        if (!(fuzeStack.getItem() instanceof FuzeItem fuze)) {
            return new CollisionResult(JOMLConversion.ZERO, false);
        }
        var projectile = projectileBlock.getProjectile(level, pos, state);

        BlockHitResult hitResult = new BlockHitResult(JOMLConversion.toMojang(hitPos), Direction.DOWN, pos, false);
        AbstractCannonProjectile.ImpactResult impactResult = new AbstractCannonProjectile.ImpactResult(
                AbstractCannonProjectile.ImpactResult.KinematicOutcome.STOP,
                false
        );

        // Take into account the detonation chance on impact
        if (fuze.onProjectileImpact(fuzeStack, projectile, hitResult, impactResult, projectileBlock.isBaseFuze())) {
            projectileBlock.detonateProjectileOnTheSpot(level, pos, state, Direction.DOWN);
            return new CollisionResult(JOMLConversion.ZERO, true);
        }

        return new CollisionResult(JOMLConversion.ZERO, false);
    }
}
