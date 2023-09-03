package com.gregtechceu.gtceu.common.data;


import com.gregtechceu.gtceu.api.entity.FOUPCartEntity;
import com.gregtechceu.gtceu.client.renderer.entity.FOUPEntityRenderer;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.EntityBuilder;
import com.tterrag.registrate.util.entry.EntityEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.registry.GTRegistries.REGISTRATE;

/**
 * @author KilaBash
 * @date 2023/8/9
 * @implNote GTEntities
 */
public class GTEntities {
    public static EntityEntry<FOUPCartEntity> FOUP = register("foup_cart", FOUPCartEntity::new, MobCategory.MISC, () -> FOUPEntityRenderer::new,
            GTEntities.properties(8, 3, true, true, 1, 1));

    public static <T extends Entity> EntityEntry<T> register(String name,
                                                             EntityType.EntityFactory<T> factory,
                                                             MobCategory classification,
                                                             NonNullSupplier<NonNullFunction<EntityRendererProvider.Context, EntityRenderer<? super T>>> renderer,
                                                             Consumer<EntityBuilder<T, Registrate>> builderConsumer) {
        var builder = REGISTRATE.entity(name, factory, classification);
        builder.renderer(renderer);
        builderConsumer.accept(builder);
        return builder.register();
    }

    @ExpectPlatform
    public static Consumer<EntityBuilder<FOUPCartEntity, Registrate>> properties(int clientTrackRange, int updateFrequency, boolean syncVelocity, boolean immuneToFire, float width, float height) {
        throw new AssertionError();
    }

    public static void init() {

    }
}
