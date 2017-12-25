package com.lothrazar.cyclicmagic.module;
import com.lothrazar.cyclicmagic.config.IHasConfig;
import com.lothrazar.cyclicmagic.data.Const;
import com.lothrazar.cyclicmagic.registry.FluidsRegistry;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidsModule extends BaseModule implements IHasConfig {
  private boolean enableMilk;
  private boolean enablePoison;
  @Override
  public void syncConfig(Configuration config) {
    enableMilk = config.getBoolean("FluidMilk", Const.ConfigCategory.content, true, Const.ConfigCategory.contentDefaultText);
    enablePoison = config.getBoolean("FluidPoison", Const.ConfigCategory.content, true, Const.ConfigCategory.contentDefaultText);
  }
  @Override
  public void onPreInit() {
    // xpjuice fluid is added by the Exp Pylon block in /components/ 
    if (enableMilk) {
      FluidsRegistry.registerMilk();
    }
    if (enablePoison) {
      FluidsRegistry.registerPoison();
    }
  }
}
