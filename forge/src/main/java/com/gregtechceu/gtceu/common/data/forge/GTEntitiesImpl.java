package com.gregtechceu.gtceu.common.data.forge;

import com.gregtechceu.gtceu.api.entity.FOUPCartEntity;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.EntityBuilder;

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
            b.clientTrackingRange(8).updateInterval(updateFrequency).setShouldReceiveVelocityUpdates(syncVelocity).sized(width, height);
        });
    }
}
