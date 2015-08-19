package net.climaxmc.core.utilities;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.*;

public class UtilEntity {
    private static Map<String, EntityType> possibleEntityTypes = new HashMap<String, EntityType>() {{
        put("Arrow", EntityType.ARROW);
        put("Bat", EntityType.BAT);
        put("Blaze", EntityType.BLAZE);
        put("CaveSpider", EntityType.CAVE_SPIDER);
        put("Chicken", EntityType.CHICKEN);
        put("Cow", EntityType.COW);
        put("Creeper", EntityType.CREEPER);
        put("EnderDragon", EntityType.ENDER_DRAGON);
        put("Enderman", EntityType.ENDERMAN);
        put("Endermite", EntityType.ENDERMITE);
        put("Ghast", EntityType.GHAST);
        put("Giant", EntityType.GIANT);
        put("Guardian", EntityType.GUARDIAN);
        put("Horse", EntityType.HORSE);
        put("IronGolem", EntityType.IRON_GOLEM);
        put("Item", EntityType.DROPPED_ITEM);
        put("MagmaCube", EntityType.MAGMA_CUBE);
        put("Mooshroom", EntityType.MUSHROOM_COW);
        put("Ocelot", EntityType.OCELOT);
        put("Pig", EntityType.PIG);
        put("PigZombie", EntityType.PIG_ZOMBIE);
        put("Rabbit", EntityType.RABBIT);
        put("Sheep", EntityType.SHEEP);
        put("Silverfish", EntityType.SILVERFISH);
        put("Skeleton", EntityType.SKELETON);
        put("Slime", EntityType.SLIME);
        put("Snowman", EntityType.SNOWMAN);
        put("Spider", EntityType.SPIDER);
        put("Squid", EntityType.SQUID);
        put("Villager", EntityType.VILLAGER);
        put("Witch", EntityType.WITCH);
        put("Wither", EntityType.WITHER);
        put("WitherSkull", EntityType.WITHER_SKULL);
        put("Wolf", EntityType.WOLF);
        put("Zombie", EntityType.ZOMBIE);
    }};

    public static void removeAI(Entity entity) {
        net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        NBTTagCompound tag = nmsEntity.getNBTTag();
        if (tag == null) {
            tag = new NBTTagCompound();
        }
        nmsEntity.c(tag);
        tag.setInt("NoAI", 1);
        tag.setBoolean("Silent", true);
        nmsEntity.f(tag);
    }

    public static EntityType getTypeFromName(String name) {
        Optional<String> entityTypeName = possibleEntityTypes.keySet().stream().filter(type -> type.equalsIgnoreCase(name)).findFirst();
        if (entityTypeName.isPresent()) {
            return possibleEntityTypes.get(entityTypeName.get());
        }
        return null;
    }
}
