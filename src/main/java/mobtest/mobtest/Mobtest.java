package mobtest.mobtest;

import com.slimeist.server_mobs.api.server_rendering.model.ServerEntityModelLoader;
import eu.pb4.polymer.api.entity.PolymerEntityUtils;
import eu.pb4.polymer.api.resourcepack.PolymerRPUtils;
import mobtest.mobtest.entities.FirstEntity;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static eu.pb4.polymer.impl.PolymerImplUtils.id;


@Environment(EnvType.SERVER)
public class Mobtest implements DedicatedServerModInitializer {

    public static final String MOD_ID = "test";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    //ENTITIES
    public static final EntityType<FirstEntity> FIRST = Registry.register(
            Registry.ENTITY_TYPE,
            id("firstentity"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, FirstEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                    .build()
    );
    private static final ServerEntityModelLoader FIRST_LOADER = new ServerEntityModelLoader(FIRST, false);
    static {
        FirstEntity.setBakedModelSupplier(FIRST_LOADER::getBakedModel);
    }

    @Override
    public void onInitializeServer() {
        if (PolymerRPUtils.addAssetSource(MOD_ID)) {
            LOGGER.info("Successfully marked as asset source");
        } else {
            LOGGER.error("Failed to mark as asset source");
        }
        PolymerRPUtils.markAsRequired();
        PolymerEntityUtils.registerType(FIRST);
    }
}