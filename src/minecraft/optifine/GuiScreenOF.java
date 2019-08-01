package optifine;

import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiVideoSettings;

public class GuiScreenOF extends GuiScreen
{
    protected void actionPerformedRightClick(GuiButton button) {}

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (mouseButton == 1)
        {
            GuiButton btn = getSelectedButton(this.buttonList, mouseX, mouseY);

            if (btn != null && btn.enabled)
            {
                btn.func_146113_a(this.mc.getSoundHandler());
                this.actionPerformedRightClick(btn);
            }
        }
    }

    public static GuiButton getSelectedButton(List<GuiButton> listButtons, int x, int y)
    {
        for (int i = 0; i < listButtons.size(); ++i)
        {
            GuiButton btn = (GuiButton)listButtons.get(i);

            if (btn.field_146125_m)
            {
                int btnWidth = GuiVideoSettings.getButtonWidth(btn);
                int btnHeight = GuiVideoSettings.getButtonHeight(btn);

                if (x >= btn.field_146128_h && y >= btn.field_146129_i && x < btn.field_146128_h + btnWidth && y < btn.field_146129_i + btnHeight)
                {
                    return btn;
                }
            }
        }

        return null;
    }
}
