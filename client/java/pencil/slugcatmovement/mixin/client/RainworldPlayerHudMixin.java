package pencil.slugcatmovement.mixin.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pencil.slugcatmovement.RainworldMovementClient;

import static net.minecraft.client.gui.screen.ingame.InventoryScreen.drawEntity;

@Mixin(InGameHud.class)
public class RainworldPlayerHudMixin implements HudRenderCallback {

    @Shadow private int scaledHeight;
    @Shadow private int scaledWidth;
    private int timerLevel = 25;
    private int guiY = 74;
    private int guiX = 0;

    private int lastScaleY = 0;
    private boolean iterating = false;
    private float iterations = 200f;
    private int karmaGuiX = 50;
    private int karmaGuiY = 0;
    private int karmaLevel = RainworldMovementClient.karmaLevel;
    private boolean setPos = false;
    private int listYPos = karmaGuiY - ((((((this.scaledHeight / 2)) + (((int) ((this.scaledHeight * 2.536) / 2) - (int) (((int) (this.scaledHeight * 2.536) / 9.1)) + ((int) (this.scaledHeight * 2.536) / 36)))))) + (int) ((this.scaledHeight * 2.536) / 9) - (int) ((this.scaledHeight * 2.536) / 10) * (karmaLevel));

    @Inject(method = "render", at = @At("RETURN"), cancellable = true)
    public void onRender (DrawContext context, float tickDelta, CallbackInfo ci) {
        this.scaledWidth = context.getScaledWindowWidth();
        this.scaledHeight = context.getScaledWindowHeight();
        karmaLevel = RainworldMovementClient.karmaLevel;
    }


    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
    }

    @Inject(method = "renderHotbar", at = @At("HEAD"), cancellable = true)
    public void onRenderHotbar (float tickDelta, DrawContext context, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "renderStatusEffectOverlay", at = @At("HEAD"), cancellable = true)
    public void onRenderStatusEffect (DrawContext context, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "renderHealthBar", at = @At("HEAD"), cancellable = true)
    public void onRenderHealth (DrawContext context, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "renderExperienceBar", at = @At("HEAD"), cancellable = true)
    public void onRenderXP (DrawContext context, int x, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "renderStatusBars", at = @At("HEAD"), cancellable = true)
    public void onRenderStatus (DrawContext context, CallbackInfo ci) {
        int foodLevel = RainworldMovementClient.foodLevel;
        if (!RainworldMovementClient.cycling) {
            context.drawTexture(new Identifier("slugcatmovement", "textures/gui/background_edge.png"), 0, this.scaledHeight-guiY, 0, 0, 307, 74, 307, 74);
            context.drawTexture(new Identifier("slugcatmovement", "textures/gui/background_corner.png"), 0, this.scaledHeight - guiY, 0, 0, 307, 74, 307, 74);
            context.drawTexture(new Identifier("slugcatmovement", "textures/gui/hunger/hunger"+foodLevel+".png"), guiX+47, this.scaledHeight-(18+(guiY/2)), 0, 0, 256, 38, 256, 38);
            context.drawTexture(new Identifier("slugcatmovement", "textures/gui/timer/timer"+timerLevel+".png"), guiX-8, this.scaledHeight-(guiY + 18), 1, 0, 108, 109, 108, 109);
            context.drawTexture(new Identifier("slugcatmovement", "textures/gui/karma/karma"+RainworldMovementClient.karmaLevel+".png"), guiX-8, this.scaledHeight-(guiY + 18), 1, 0, 108, 109, 108, 109);
        }
        ci.cancel();
    }
}
