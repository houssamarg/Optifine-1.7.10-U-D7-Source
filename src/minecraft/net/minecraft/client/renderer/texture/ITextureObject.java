package net.minecraft.client.renderer.texture;

import java.io.IOException;
import net.minecraft.client.resources.IResourceManager;
import shadersmod.client.MultiTexID;

public interface ITextureObject
{
    void loadTexture(IResourceManager p_110551_1_) throws IOException;

    int getGlTextureId();

    MultiTexID getMultiTexID();
}
