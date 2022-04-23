package andrews.table_top_craft.mixins;

import andrews.table_top_craft.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Screen.class)
public class ScreenMixin
{
    @Inject(at = @At(value = "HEAD"), method = "isPauseScreen", cancellable = true)
    public void onIsPauseScreen(CallbackInfoReturnable<Boolean> cir)
    {
        // We check if we are on the AdvancementsScreen as we only want to affect stuff in that class
        if(((Screen)(Object)this) instanceof AdvancementsScreen advancementsScreen)
        {
            // We only run the code when on Single Player as on servers the game isn't paused while in any menu
            if(Minecraft.getInstance().getSingleplayerServer() != null)
                if(advancementsScreen.selectedTab != null) // We make sure there is an active tab
                    if(advancementsScreen.selectedTab.getAdvancement().getId().equals(Reference.TAB_ID))
                        cir.setReturnValue(false); // If there is a tab and the tabs title is ^^^ we return false
        }
    }
}