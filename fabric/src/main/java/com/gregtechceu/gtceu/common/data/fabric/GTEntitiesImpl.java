package com.gregtechceu.gtceu.common.data.fabric;

import com.gregtechceu.gtceu.api.entity.FOUPCartEntity;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.EntityBuilder;
import net.minecraft.world.entity.EntityDimensions;

import java.util.function.Consumer;

/**
 * @author KilaBash
 * @date 2023/8/9
 * @implNote GTEntitiesImpl
 */
public class GTEntitiesImpl {

    public static Consumer<EntityBuilder<FOUPCartEntity, Registrate>> properties(int clientTrackRange, int updateFrequency, boolean syncVelocity, boolean immuneToFire, float width, float height) {
        return builder -> builder.properties(b -> {
            if (immuneToFire) {
                b.fireImmune();
            }
            b.trackRangeChunks(8).trackedUpdateRate(updateFrequency).forceTrackedVelocityUpdates(syncVelocity).dimensions(EntityDimensions.scalable(width, height));
        });
    }
}
