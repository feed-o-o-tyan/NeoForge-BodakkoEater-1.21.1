package net.feed_o_o.bodakkoeater.client;

import net.feed_o_o.bodakkoeater.menu.NutrientDeviceMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/**
 * GUI of the nutrient device. The background reuses the vanilla chest texture (1 row),
 * so no texture file of our own is needed. The 5 buttons on the right toggle the input of each face.
 */
public class NutrientDeviceScreen extends AbstractContainerScreen<NutrientDeviceMenu> {
    private static final ResourceLocation CONTAINER_BACKGROUND =
            ResourceLocation.withDefaultNamespace("textures/gui/container/generic_54.png");
    private static final int ROWS = 1;
    private static final String[] FACE_KEYS = {
            "gui.bodakkoeater.face.down", "gui.bodakkoeater.face.north", "gui.bodakkoeater.face.south",
            "gui.bodakkoeater.face.west", "gui.bodakkoeater.face.east"};

    private final Button[] faceButtons = new Button[NutrientDeviceMenu.FACE_COUNT];

    public NutrientDeviceScreen(NutrientDeviceMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageHeight = 114 + ROWS * 18;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void init() {
        super.init();
        for (int i = 0; i < faceButtons.length; i++) {
            final int faceIndex = i;
            faceButtons[i] = addRenderableWidget(Button.builder(Component.empty(), button -> onFaceClicked(faceIndex))
                    .bounds(this.leftPos + this.imageWidth + 4, this.topPos + i * 22, 74, 20)
                    .build());
        }
        updateButtons();
    }

    private void onFaceClicked(int faceIndex) {
        if (this.minecraft != null && this.minecraft.gameMode != null) {
            // Handled on the server by NutrientDeviceMenu#clickMenuButton, then synced back.
            this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, faceIndex);
        }
    }

    private void updateButtons() {
        int mask = this.menu.getInputMask();
        for (int i = 0; i < faceButtons.length; i++) {
            boolean on = (mask >> i & 1) != 0;
            faceButtons[i].setMessage(Component.empty()
                    .append(Component.translatable(FACE_KEYS[i]))
                    .append(": ")
                    .append(Component.translatable(on ? "gui.bodakkoeater.on" : "gui.bodakkoeater.off")));
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        updateButtons();
        super.render(graphics, mouseX, mouseY, partialTick); // draws the background, slots and buttons
        graphics.drawString(this.font, Component.translatable("gui.bodakkoeater.input_faces"),
                this.leftPos + this.imageWidth + 4, this.topPos - 11, 0xFFFFFF);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        graphics.blit(CONTAINER_BACKGROUND, x, y, 0, 0, this.imageWidth, ROWS * 18 + 17);
        graphics.blit(CONTAINER_BACKGROUND, x, y + ROWS * 18 + 17, 0, 126, this.imageWidth, 96);
    }
}
