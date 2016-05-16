package com.lothrazar.cyclicmagic.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.lothrazar.cyclicmagic.IHasConfig;
import com.lothrazar.cyclicmagic.IHasRecipe;
import com.lothrazar.cyclicmagic.ModMain;
import com.lothrazar.cyclicmagic.item.BaseItem;
import com.lothrazar.cyclicmagic.item.ItemChestSack;
import com.lothrazar.cyclicmagic.item.ItemChestSackEmpty;
import com.lothrazar.cyclicmagic.item.ItemCyclicWand;
import com.lothrazar.cyclicmagic.item.ItemEmeraldArmor;
import com.lothrazar.cyclicmagic.item.ItemEmeraldAxe;
import com.lothrazar.cyclicmagic.item.ItemEmeraldHoe;
import com.lothrazar.cyclicmagic.item.ItemEmeraldPickaxe;
import com.lothrazar.cyclicmagic.item.ItemEmeraldSpade;
import com.lothrazar.cyclicmagic.item.ItemEmeraldSword;
import com.lothrazar.cyclicmagic.item.ItemEnderBook;
import com.lothrazar.cyclicmagic.item.ItemEnderPearlReuse;
import com.lothrazar.cyclicmagic.item.ItemFoodAppleMagic;
import com.lothrazar.cyclicmagic.item.ItemFoodHorse;
import com.lothrazar.cyclicmagic.item.ItemInventoryStorage;
import com.lothrazar.cyclicmagic.item.ItemPaperCarbon;
import com.lothrazar.cyclicmagic.item.ItemToolHarvest;
import com.lothrazar.cyclicmagic.item.ItemToolPull;
import com.lothrazar.cyclicmagic.item.ItemToolPush;
import com.lothrazar.cyclicmagic.item.ItemToolRotate;
import com.lothrazar.cyclicmagic.item.projectile.ItemProjectileBlaze;
import com.lothrazar.cyclicmagic.item.projectile.ItemProjectileDungeon;
import com.lothrazar.cyclicmagic.item.projectile.ItemProjectileFishing;
import com.lothrazar.cyclicmagic.item.projectile.ItemProjectileHarvest;
import com.lothrazar.cyclicmagic.item.projectile.ItemProjectileLightning;
import com.lothrazar.cyclicmagic.item.projectile.ItemProjectileSnow;
import com.lothrazar.cyclicmagic.item.projectile.ItemProjectileTNT;
import com.lothrazar.cyclicmagic.item.projectile.ItemProjectileTorch;
import com.lothrazar.cyclicmagic.item.projectile.ItemProjectileWater;
import com.lothrazar.cyclicmagic.item.projectile.ItemProjectileWool;
import com.lothrazar.cyclicmagic.util.Const;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemRegistry {

	public static Map<String,Item> itemMap	= new HashMap<String,Item>();

	private static void addItem(BaseItem i, String key){ 
		itemMap.put(key, i);
	}
	
	private static boolean emeraldGearEnabled ; 
	public static ToolMaterial		MATERIAL_EMERALD;
	public static ArmorMaterial		ARMOR_MATERIAL_EMERALD;
	public final static Item		REPAIR_EMERALD = Items.emerald;

	// only because theyre private, with no getters
	private static final int		diamondDurability				= 33;
	private static final int[]	diamondreductionAmounts	= new int[] { 3, 6, 8, 3 };
 
	public static void syncConfig(Configuration config) {
		Property prop;
		Item item;
		for (String key : itemMap.keySet()) {
			item = itemMap.get(key);
			if (item instanceof IHasConfig) {
				((IHasConfig) item).syncConfig(config);
			}
		}
		
		ItemCyclicWand.syncConfig(config);

		String category = Const.ConfigCategory.items;
		prop = config.get(category, "emeraldGear", true, "Full set of emerald gear with similar properties as diamond");
		prop.setRequiresMcRestart(true);
		emeraldGearEnabled  =   prop.getBoolean();

		ItemFoodHorse.syncConfig(config);
		
		ItemFoodAppleMagic.syncConfig(config);
	}
	
	private static void registerRecipes() {

		Item item;
		for (String key : itemMap.keySet()) {
			item = itemMap.get(key);
			if (item instanceof IHasRecipe) {
				((IHasRecipe) item).addRecipe();
			}
		}
	}

	public static void register() {
		registerMaterials();
		
		addItem(new ItemEnderPearlReuse(),"ender_pearl_reuse"); 
		addItem(new ItemPaperCarbon(),"carbon_paper"); 
		addItem(new ItemToolHarvest(),"tool_harvest");
		addItem(new ItemToolPull(),"tool_pull");
		addItem(new ItemToolPush(),"tool_push");
		addItem(new ItemToolRotate(),"tool_rotate");
		addItem(new ItemInventoryStorage(),"storage_bag"); 
		addItem(new ItemChestSack().setHidden(),"chest_sack");
		addItem(new ItemChestSackEmpty(),"chest_sack_empty");
		addItem(new ItemProjectileBlaze(),"ender_blaze");
		addItem(new ItemProjectileDungeon(),"ender_dungeon");
		addItem(new ItemProjectileFishing(),"ender_fishing");
		addItem(new ItemProjectileWool(),"ender_wool");
		addItem(new ItemProjectileTorch(),"ender_torch");
		addItem(new ItemProjectileWater(),"ender_water");
		addItem(new ItemProjectileSnow(),"ender_snow");
		addItem(new ItemProjectileHarvest(),"ender_harvest");
		addItem(new ItemProjectileLightning(),"ender_lightning");
		addItem(new ItemProjectileTNT(1),"ender_tnt_1");
		addItem(new ItemProjectileTNT(2),"ender_tnt_2");
		addItem(new ItemProjectileTNT(3),"ender_tnt_4");
		addItem(new ItemProjectileTNT(4),"ender_tnt_6");
		addItem(new ItemFoodHorse(new ItemStack(Items.emerald)),"horse_upgrade_type");
		addItem(new ItemFoodHorse(new ItemStack(Items.dye, 1, Const.dye_lapis)),"horse_upgrade_variant");
		addItem(new ItemFoodHorse(new ItemStack(Items.diamond)),"horse_upgrade_health");
		addItem(new ItemFoodHorse(new ItemStack(Items.redstone)),"horse_upgrade_speed");
		addItem(new ItemFoodHorse(new ItemStack(Items.ender_eye)),"horse_upgrade_jump");
		addItem(new ItemEnderBook(),"book_ender"); 
 
		//maybe one day it will be all base items
		Item item;
		for (String key : itemMap.keySet()) {
			item = itemMap.get(key);
			if (item instanceof BaseItem) {
				((BaseItem) item).register(key);
			}
//			else{
//				System.out.println("WARN: unregistered item"+item.getClass()); 
//				//registerItem(item);//NAME??
//			}
		}


		if (ItemCyclicWand.sceptersEnabled) {

			ItemCyclicWand cyclic_wand_build = new ItemCyclicWand();
			registerItem(cyclic_wand_build, "cyclic_wand_build");
			GameRegistry.addRecipe(new ItemStack(cyclic_wand_build), "sds", " o ", "gog", 'd', new ItemStack(Blocks.diamond_block), 'g', Items.ghast_tear, 'o', Blocks.obsidian, 's', Items.nether_star);

			ItemCyclicWand cyclic_wand_range = new ItemCyclicWand();
			registerItem(cyclic_wand_range, "cyclic_wand_range");
			GameRegistry.addRecipe(new ItemStack(cyclic_wand_range), "sds", " o ", "gog", 'd', new ItemStack(Blocks.emerald_block), 'g', Items.ghast_tear, 'o', Blocks.obsidian, 's', Items.nether_star);

			ItemCyclicWand cyclic_wand_fly = new ItemCyclicWand();
			registerItem(cyclic_wand_fly, "cyclic_wand_fly");
			GameRegistry.addRecipe(new ItemStack(cyclic_wand_fly), "sds", " o ", "gog", 'd', new ItemStack(Blocks.redstone_block), 'g', Items.ghast_tear, 'o', Blocks.obsidian, 's', Items.nether_star);
		}

		// thanks for help:
		// http://bedrockminer.jimdo.com/modding-tutorials/basic-modding-1-7/custom-tools-swords/

		
		
		if (emeraldGearEnabled) {

			Item emerald_sword = new ItemEmeraldSword();
			registerItem(emerald_sword, ItemEmeraldSword.name);

			Item emerald_pickaxe = new ItemEmeraldPickaxe();
			registerItem(emerald_pickaxe, ItemEmeraldPickaxe.name);

			Item emerald_axe = new ItemEmeraldAxe();
			registerItem(emerald_axe, ItemEmeraldAxe.name);

			Item emerald_spade = new ItemEmeraldSpade();
			registerItem(emerald_spade, ItemEmeraldSpade.name);

			Item emerald_hoe = new ItemEmeraldHoe();
			registerItem(emerald_hoe, ItemEmeraldHoe.name);

			// ..yeah.. kind of breaks the pattern. i could make one class for each i
			// guess.
			Item emerald_helmet = new ItemEmeraldArmor(EntityEquipmentSlot.HEAD);
			registerItem(emerald_helmet, "emerald_helmet");

			Item emerald_chestplate = new ItemEmeraldArmor(EntityEquipmentSlot.CHEST);
			registerItem(emerald_chestplate, "emerald_chestplate");

			Item emerald_leggings = new ItemEmeraldArmor(EntityEquipmentSlot.LEGS);
			registerItem(emerald_leggings, "emerald_leggings");

			Item emerald_boots = new ItemEmeraldArmor(EntityEquipmentSlot.FEET);
			registerItem(emerald_boots, "emerald_boots");

		}
 

		int I = PotionRegistry.I;
		int II = PotionRegistry.II;
	////	int III = PotionRegistry.III;
	//	int IV = PotionRegistry.IV;
		int V = PotionRegistry.V;


		 
		final int hunger		= 4;
		final int time			= 8 * 60; // 8:00
		
		if (ItemFoodAppleMagic.apple_chorus_enabled) {
			ItemFoodAppleMagic apple_chorus = new ItemFoodAppleMagic(hunger, false, new ItemStack(Items.chorus_fruit));

			apple_chorus.addEffect(MobEffects.levitation, 30, I);
			ItemRegistry.registerItem(apple_chorus, "apple_chorus");
		}

		if (ItemFoodAppleMagic.apple_ender_enabled) {
			ItemFoodAppleMagic apple_ender = new ItemFoodAppleMagic(hunger, false, new ItemStack(Items.ender_pearl));

			apple_ender.addEffect(PotionRegistry.ender, time, I);
			ItemRegistry.registerItem(apple_ender, "apple_ender");
		}

		if (ItemFoodAppleMagic.apple_emerald_enabled) {
			ItemFoodAppleMagic apple_emerald = new ItemFoodAppleMagic(hunger, false, new ItemStack(Items.emerald));
		
			apple_emerald.addEffect(MobEffects.healthBoost, time, V);
			ItemRegistry.registerItem(apple_emerald, "apple_emerald"); 
		}

		if (ItemFoodAppleMagic.apple_chocolate_enabled) {
			ItemFoodAppleMagic apple_chocolate = new ItemFoodAppleMagic(hunger, false, new ItemStack(Items.dye, 1, Const.dye_cocoa));
			apple_chocolate.addEffect(MobEffects.luck, time, II);
			ItemRegistry.registerItem(apple_chocolate, "apple_chocolate"); 
		}

		if (ItemFoodAppleMagic.apple_lapis_enabled) {
			ItemFoodAppleMagic apple_lapis = new ItemFoodAppleMagic(hunger, false, new ItemStack(Items.dye, 1, Const.dye_lapis));
			apple_lapis.addEffect(MobEffects.digSpeed, time, II); // Haste
			ItemRegistry.registerItem(apple_lapis, "apple_lapis"); 
		}
		
		if (ItemFoodAppleMagic.apple_diamond_enabled) {
			ItemFoodAppleMagic apple_diamond = new ItemFoodAppleMagic(hunger, false, new ItemStack(Items.diamond),true);
			ItemRegistry.registerItem(apple_diamond, "apple_diamond");
			apple_diamond.addEffect(MobEffects.resistance, time, I);
		}

		if (ItemFoodAppleMagic.apple_bone_enabled) {
			ItemFoodAppleMagic apple_bone = new ItemFoodAppleMagic(hunger, false, new ItemStack(Items.bone));
			ItemRegistry.registerItem(apple_bone, "apple_bone");
			apple_bone.addEffect(MobEffects.glowing, time, I);
		}

		if (ItemFoodAppleMagic.apple_netherwart_enabled) {
			ItemFoodAppleMagic apple_netherwart = new ItemFoodAppleMagic(hunger, false, new ItemStack(Items.nether_wart));
			ItemRegistry.registerItem(apple_netherwart, "apple_netherwart");
			apple_netherwart.addEffect(PotionRegistry.magnet, time, I);
		}

		if (ItemFoodAppleMagic.apple_prismarine_enabled) {
			ItemFoodAppleMagic apple_prismarine = new ItemFoodAppleMagic(hunger, false, new ItemStack(Items.prismarine_shard));
			ItemRegistry.registerItem(apple_prismarine, "apple_prismarine");
			apple_prismarine.addEffect(PotionRegistry.waterwalk, time, I); 
		}

		if (ItemFoodAppleMagic.apple_clownfish_enabled) {
			ItemFoodAppleMagic apple_slowfall = new ItemFoodAppleMagic(hunger, false, new ItemStack(Items.fish, 1, Const.clownfish));
			ItemRegistry.registerItem(apple_slowfall, "apple_slowfall");
			apple_slowfall.addEffect(PotionRegistry.slowfall, time, I);
		}

		registerRecipes();
	}

	private static void registerMaterials() {

		ARMOR_MATERIAL_EMERALD = EnumHelper.addArmorMaterial("emerald", Const.MODID + ":emerald", diamondDurability, diamondreductionAmounts, ArmorMaterial.DIAMOND.getEnchantability(), ArmorMaterial.DIAMOND.getSoundEvent());

		MATERIAL_EMERALD = ToolMaterial.DIAMOND;
		// TODO: addToolMat causes a bug/crash, not sure if forge will fix.

		// EnumHelper.addToolMaterial("emerald", 3, harvestLevel 3 same as diamond
		// 1600,3.5F, 5+25 );

	}
	 
	public static void registerItem(Item item, String name) {
		registerItem(item, name, false);// default is not hidden
	}

	public static void registerItem(Item item, String name, boolean isHidden) {

		item.setUnlocalizedName(name);

		GameRegistry.register(item, new ResourceLocation(Const.MODID, name));

		if (isHidden == false) {
			item.setCreativeTab(ModMain.TAB);
		}

		itemMap.put(name, item);
	}
}
