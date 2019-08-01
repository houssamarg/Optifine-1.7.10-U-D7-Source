package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import optifine.Config;
import optifine.GuiAnimationSettingsOF;
import optifine.GuiDetailSettingsOF;
import optifine.GuiOptionButtonOF;
import optifine.GuiOptionSliderOF;
import optifine.GuiOtherSettingsOF;
import optifine.GuiPerformanceSettingsOF;
import optifine.GuiQualitySettingsOF;
import optifine.Lang;
import optifine.TooltipManager;
import shadersmod.client.GuiShaders;

public class GuiVideoSettings extends GuiScreen
{
    private GuiScreen field_146498_f;
    protected String field_146500_a = "Video Settings";
    private GameSettings field_146499_g;
    private static GameSettings.Options[] field_146502_i = new GameSettings.Options[] {GameSettings.Options.GRAPHICS, GameSettings.Options.RENDER_DISTANCE, GameSettings.Options.AMBIENT_OCCLUSION, GameSettings.Options.FRAMERATE_LIMIT, GameSettings.Options.AO_LEVEL, GameSettings.Options.VIEW_BOBBING, GameSettings.Options.GUI_SCALE, GameSettings.Options.ADVANCED_OPENGL, GameSettings.Options.GAMMA, GameSettings.Options.CHUNK_LOADING, GameSettings.Options.FOG_FANCY, GameSettings.Options.FOG_START};
    private static final String __OBFID = "CL_00000718";
    private TooltipManager tooltipManager = new TooltipManager(this);

    public GuiVideoSettings(GuiScreen par1GuiScreen, GameSettings par2GameSettings)
    {
        this.field_146498_f = par1GuiScreen;
        this.field_146499_g = par2GameSettings;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.field_146500_a = I18n.format("options.videoTitle", new Object[0]);
        this.buttonList.clear();
        int y;

        for (y = 0; y < field_146502_i.length; ++y)
        {
            GameSettings.Options x = field_146502_i[y];

            if (x != null)
            {
                int x1 = this.width / 2 - 155 + y % 2 * 160;
                int y1 = this.height / 6 + 21 * (y / 2) - 12;

                if (x.getEnumFloat())
                {
                    this.buttonList.add(new GuiOptionSliderOF(x.returnEnumOrdinal(), x1, y1, x));
                }
                else
                {
                    this.buttonList.add(new GuiOptionButtonOF(x.returnEnumOrdinal(), x1, y1, x, this.field_146499_g.getKeyBinding(x)));
                }
            }
        }

        y = this.height / 6 + 21 * (field_146502_i.length / 2) - 12;
        boolean var5 = false;
        int var6 = this.width / 2 - 155 + 0;
        this.buttonList.add(new GuiOptionButton(231, var6, y, Lang.get("of.options.shaders")));
        var6 = this.width / 2 - 155 + 160;
        this.buttonList.add(new GuiOptionButton(202, var6, y, Lang.get("of.options.quality")));
        y += 21;
        var6 = this.width / 2 - 155 + 0;
        this.buttonList.add(new GuiOptionButton(201, var6, y, Lang.get("of.options.details")));
        var6 = this.width / 2 - 155 + 160;
        this.buttonList.add(new GuiOptionButton(212, var6, y, Lang.get("of.options.performance")));
        y += 21;
        var6 = this.width / 2 - 155 + 0;
        this.buttonList.add(new GuiOptionButton(211, var6, y, Lang.get("of.options.animations")));
        var6 = this.width / 2 - 155 + 160;
        this.buttonList.add(new GuiOptionButton(222, var6, y, Lang.get("of.options.other")));
        y += 21;
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
    }

    protected void actionPerformed(GuiButton button)
    {
        if (button.enabled)
        {
            int guiScale = this.field_146499_g.guiScale;

            if (button.id < 200 && button instanceof GuiOptionButton)
            {
                this.field_146499_g.setOptionValue(((GuiOptionButton)button).func_146136_c(), 1);
                button.displayString = this.field_146499_g.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
            }

            if (button.id == 200)
            {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.field_146498_f);
            }

            if (this.field_146499_g.guiScale != guiScale)
            {
                ScaledResolution scr = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
                int var4 = scr.getScaledWidth();
                int var5 = scr.getScaledHeight();
                this.setWorldAndResolution(this.mc, var4, var5);
            }

            if (button.id == 201)
            {
                this.mc.gameSettings.saveOptions();
                GuiDetailSettingsOF scr1 = new GuiDetailSettingsOF(this, this.field_146499_g);
                this.mc.displayGuiScreen(scr1);
            }

            if (button.id == 202)
            {
                this.mc.gameSettings.saveOptions();
                GuiQualitySettingsOF scr2 = new GuiQualitySettingsOF(this, this.field_146499_g);
                this.mc.displayGuiScreen(scr2);
            }

            if (button.id == 211)
            {
                this.mc.gameSettings.saveOptions();
                GuiAnimationSettingsOF scr3 = new GuiAnimationSettingsOF(this, this.field_146499_g);
                this.mc.displayGuiScreen(scr3);
            }

            if (button.id == 212)
            {
                this.mc.gameSettings.saveOptions();
                GuiPerformanceSettingsOF scr4 = new GuiPerformanceSettingsOF(this, this.field_146499_g);
                this.mc.displayGuiScreen(scr4);
            }

            if (button.id == 222)
            {
                this.mc.gameSettings.saveOptions();
                GuiOtherSettingsOF scr5 = new GuiOtherSettingsOF(this, this.field_146499_g);
                this.mc.displayGuiScreen(scr5);
            }

            if (button.id == 231)
            {
                if (Config.isAntialiasing() || Config.isAntialiasingConfigured())
                {
                    Config.showGuiMessage(Lang.get("of.message.shaders.aa1"), Lang.get("of.message.shaders.aa2"));
                    return;
                }

                if (Config.isAnisotropicFiltering())
                {
                    Config.showGuiMessage(Lang.get("of.message.shaders.af1"), Lang.get("of.message.shaders.af2"));
                    return;
                }

                if (Config.isFastRender())
                {
                    Config.showGuiMessage(Lang.get("of.message.shaders.fr1"), Lang.get("of.message.shaders.fr2"));
                    return;
                }

                this.mc.gameSettings.saveOptions();
                GuiShaders scr6 = new GuiShaders(this, this.field_146499_g);
                this.mc.displayGuiScreen(scr6);
            }
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int x, int y, float z)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.field_146500_a, this.width / 2, 15, 16777215);
        String ver = Config.getVersion();
        String ed = "HD_U";

        if (ed.equals("HD"))
        {
            ver = "OptiFine HD D7";
        }

        if (ed.equals("HD_U"))
        {
            ver = "OptiFine HD D7 Ultra";
        }

        if (ed.equals("L"))
        {
            ver = "OptiFine D7 Light";
        }

        this.drawString(this.fontRendererObj, ver, 2, this.height - 10, 8421504);
        String verMc = "Minecraft 1.7.10";
        int lenMc = this.fontRendererObj.getStringWidth(verMc);
        this.drawString(this.fontRendererObj, verMc, this.width - lenMc - 2, this.height - 10, 8421504);
        super.drawScreen(x, y, z);
        this.tooltipManager.drawTooltips(x, y, this.buttonList);
    }

    public static int getButtonWidth(GuiButton btn)
    {
        return btn.field_146120_f;
    }

    public static int getButtonHeight(GuiButton btn)
    {
        return btn.field_146121_g;
    }

    public static void drawGradientRect(GuiScreen guiScreen, int left, int top, int right, int bottom, int startColor, int endColor)
    {
        guiScreen.drawGradientRect(left, top, right, bottom, startColor, endColor);
    }
}
