package net.marshall.mccourse;

import com.mojang.logging.LogUtils;
import net.marshall.mccourse.block.ModBlocks;
import net.marshall.mccourse.effect.ModEffects;
import net.marshall.mccourse.enchantment.ModEnchantments;
import net.marshall.mccourse.item.ModCreativeModeTabs;
import net.marshall.mccourse.item.ModItemProperties;
import net.marshall.mccourse.item.ModItems;
import net.marshall.mccourse.loot.ModLootModifiers;
import net.marshall.mccourse.painting.ModPaintings;
import net.marshall.mccourse.particle.ModParticles;
import net.marshall.mccourse.potion.BetterBrewingRecipe;
import net.marshall.mccourse.potion.ModPotionsClass;
import net.marshall.mccourse.sound.ModSounds;
import net.marshall.mccourse.villager.ModVillagers;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.EventBus;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MCCourseMod.MOD_ID)
public class MCCourseMod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "mccourse";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace


    public MCCourseMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);

        ModBlocks.register(modEventBus);

        ModEnchantments.register(modEventBus);

        ModSounds.register(modEventBus);

        ModLootModifiers.LOOT_MODIFIER_SERIALIZERS.register(modEventBus);

        ModPaintings.PAINTING_VARIANTS.register(modEventBus);

        ModEffects.MOB_EFFECTS.register(modEventBus);

        ModPotionsClass.register(modEventBus);

        ModVillagers.register(modEventBus);

        ModParticles.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
      event.enqueueWork(() -> {
                  ComposterBlock.COMPOSTABLES.put(ModItems.KOHLRABI.get(), 0.35f);
                  ComposterBlock.COMPOSTABLES.put(ModItems.KOHLRABI_SEEDS.get(), 0.20f);

          ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.SNAPDRAGON.getId(), ModBlocks.POTTED_SNAPDRAGON);
              });

        BrewingRecipeRegistry.addRecipe(new BetterBrewingRecipe(Potions.AWKWARD, Items.SLIME_BALL, ModPotionsClass.SLIMEY_POTION.get()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                ModItemProperties.addCustomItemProperties();

            });
        }
    }
}