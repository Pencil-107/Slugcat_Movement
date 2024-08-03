package pencil.slugcatmovement.gui.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Shadow;
import pencil.slugcatmovement.RainworldMovementClient;

public class KarmaScreen extends Screen {

    private int scaledHeight;
    private int scaledWidth;
    private int karmaLevel = RainworldMovementClient.karmaLevel;
    private int karmaGuiX = 50;
    private int karmaGuiY = 0;

    public KarmaScreen(Text title) {
        super(title);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (RainworldMovementClient.cycling) {
            super.render(context, mouseX, mouseY, delta);
            boolean glowed = false;
            int karmaLevel = RainworldMovementClient.karmaLevel;
            int karmaGuiX = 50;
            int karmaGuiY = 0;
            int foodLevel = RainworldMovementClient.foodLevel;
            this.scaledWidth = context.getScaledWindowWidth();
            this.scaledHeight = context.getScaledWindowHeight();
            context.drawTexture(new Identifier("slugcatmovement", "textures/gui/background_screen_black.png"), 0, 0, 0, 0, this.scaledWidth, this.scaledHeight, 16, 16);
            context.drawTexture(new Identifier("slugcatmovement", "textures/gui/karma/karma_screen/background_karma_screen.png"), karmaGuiX, karmaGuiY, 0, 0, (int) (this.scaledHeight / 1.8f), this.scaledHeight, (int) (this.scaledHeight / 1.8f), this.scaledHeight);
            context.drawTexture(new Identifier("slugcatmovement", "textures/gui/karma/karma_screen/trim_karma_screen.png"), karmaGuiX, karmaGuiY, 0, 0, (int) (this.scaledHeight / 1.8f), this.scaledHeight, (int) (this.scaledHeight / 1.8f), this.scaledHeight);
            context.drawTexture(new Identifier("slugcatmovement", "textures/gui/karma/karma_screen/karma_list.png"), karmaGuiX, karmaGuiY - ((((((this.scaledHeight / 2)) + (((int) ((this.scaledHeight * 2.536) / 2) - (int) (((int) (this.scaledHeight * 2.536) / 9.1)) + ((int) (this.scaledHeight * 2.536) / 36)))))) + (int) ((this.scaledHeight * 2.536) / 9) - (int) ((this.scaledHeight * 2.536) / 10) * (karmaLevel)), 0, 0, (int) (this.scaledHeight / 1.8f), (int) (this.scaledHeight * 2.53f), (int) (this.scaledHeight / 1.8f), (int) (this.scaledHeight * 2.53f));
            if (RainworldMovementClient.karmaShielded) {
                context.drawTexture(new Identifier("slugcatmovement", "textures/gui/karma/karma_screen/karma_shield.png"), karmaGuiX, karmaGuiY, 0, 0, (int) (this.scaledHeight / 1.8f), this.scaledHeight, (int) (this.scaledHeight / 1.8f), this.scaledHeight);
            }
            context.drawTexture(new Identifier("slugcatmovement", "textures/gui/karma/karma_screen/shadows_karma_screen.png"), karmaGuiX, karmaGuiY, 0, 0, (int) (this.scaledHeight / 1.8f), this.scaledHeight, (int) (this.scaledHeight / 1.8f), this.scaledHeight);
            context.drawTexture(new Identifier("slugcatmovement", "textures/gui/karma/karma_screen/player_karma_screen.png"), (scaledWidth - ((this.scaledHeight/3)*2)), this.scaledHeight/12, 0, 0, (this.scaledHeight/3)*2, (this.scaledHeight/3)*2, (this.scaledHeight/3)*2, (this.scaledHeight/3)*2);
            context.drawTexture(new Identifier("slugcatmovement", "textures/gui/hunger/hunger" + foodLevel + ".png"), scaledWidth - (scaledWidth / 2) - (int) (((this.scaledHeight/13) *  6.737)/2), this.scaledHeight - ((this.scaledHeight/13)*2), 0, 0,(int) ((this.scaledHeight/13) *  6.737), this.scaledHeight/13, (int) ((this.scaledHeight/13) *  6.737), this.scaledHeight/13);
        }
    }
}
