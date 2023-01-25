package mobtest.mobtest.entities;

import com.slimeist.server_mobs.api.server_rendering.entity.IServerRenderedEntity;
import com.slimeist.server_mobs.api.server_rendering.model.BakedServerEntityModel;
import eu.pb4.polymer.api.entity.PolymerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.math.EulerAngle;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class FirstEntity extends Entity implements PolymerEntity, IServerRenderedEntity {
    private static Supplier<BakedServerEntityModel> bakedModelSupplier;
    private BakedServerEntityModel.Instance modelInstance;

    public FirstEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        super.tick();
        this.getModelInstance().updateHologram();//this needs to be called at least once to initialize the model, and continuously if you want the model to animate
    }

    @Override
    public BakedServerEntityModel.Instance createModelInstance() {
        return getBakedModel().createInstance(this);
    }
    @Override
    public BakedServerEntityModel.Instance getModelInstance() {
        if (modelInstance == null) {
            modelInstance = createModelInstance();
        }
        return modelInstance;
    }
    @Override
    public BakedServerEntityModel getBakedModel() {
        return bakedModelSupplier.get();
    }

    @Override
    public void initAngles() {
        this.getModelInstance().setPartPivot("base", Vec3d.ZERO); //I didn't center my model in BlockBench, so correct for it here
    }
    @Override
    public void updateAngles() {
        this.getModelInstance().setPartRotation("base", new EulerAngle(-this.getPitch(), -this.getYaw(), 0)); //rotate model to follow entity
    }

    public static void setBakedModelSupplier(Supplier<BakedServerEntityModel> bakedModel) {
        bakedModelSupplier = bakedModel;
    }
    @Override
    public EntityType<?> getPolymerEntityType() {
        return EntityType.MARKER;
    }

    @Override
    protected void initDataTracker() {}

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {}

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {}

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}