package shadersmod.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import optifine.Config;
import optifine.ConnectedParser;
import optifine.MatchBlock;
import optifine.PropertiesOrdered;
import optifine.StrUtils;

public class BlockAliases
{
    private static BlockAlias[][] blockAliases = (BlockAlias[][])null;

    public static int getMappedBlockId(int blockId, int metadata)
    {
        if (blockAliases == null)
        {
            return blockId;
        }
        else if (blockId >= 0 && blockId < blockAliases.length)
        {
            BlockAlias[] aliases = blockAliases[blockId];

            if (aliases == null)
            {
                return blockId;
            }
            else
            {
                for (int i = 0; i < aliases.length; ++i)
                {
                    BlockAlias ba = aliases[i];

                    if (ba.matches(blockId, metadata))
                    {
                        return ba.getBlockId();
                    }
                }

                return blockId;
            }
        }
        else
        {
            return blockId;
        }
    }

    public static void update(IShaderPack shaderPack)
    {
        reset();
        String path = "/shaders/block.properties";

        try
        {
            InputStream e = shaderPack.getResourceAsStream(path);

            if (e == null)
            {
                return;
            }

            PropertiesOrdered props = new PropertiesOrdered();
            props.load(e);
            e.close();
            Config.dbg("[Shaders] Parsing block mappings: " + path);
            ArrayList listBlockAliases = new ArrayList();
            ConnectedParser cp = new ConnectedParser("Shaders");
            Set keys = props.keySet();
            Iterator it = keys.iterator();

            while (it.hasNext())
            {
                String key = (String)it.next();
                String val = props.getProperty(key);
                String prefix = "block.";

                if (!key.startsWith(prefix))
                {
                    Config.warn("[Shaders] Invalid block ID: " + key);
                }
                else
                {
                    String blockIdStr = StrUtils.removePrefix(key, prefix);
                    int blockId = Config.parseInt(blockIdStr, -1);

                    if (blockId < 0)
                    {
                        Config.warn("[Shaders] Invalid block ID: " + key);
                    }
                    else
                    {
                        MatchBlock[] matchBlocks = cp.parseMatchBlocks(val);

                        if (matchBlocks != null && matchBlocks.length >= 1)
                        {
                            BlockAlias ba = new BlockAlias(blockId, matchBlocks);
                            addToList(listBlockAliases, ba);
                        }
                        else
                        {
                            Config.warn("[Shaders] Invalid block ID mapping: " + key + "=" + val);
                        }
                    }
                }
            }

            if (listBlockAliases.size() <= 0)
            {
                return;
            }

            blockAliases = toArrays(listBlockAliases);
        }
        catch (IOException var15)
        {
            Config.warn("[Shaders] Error reading: " + path);
        }
    }

    private static void addToList(List<List<BlockAlias>> blocksAliases, BlockAlias ba)
    {
        int[] blockIds = ba.getMatchBlockIds();

        for (int i = 0; i < blockIds.length; ++i)
        {
            int blockId = blockIds[i];

            while (blockId >= blocksAliases.size())
            {
                blocksAliases.add((List<BlockAlias>)null);
            }

            Object blockAliases = (List)blocksAliases.get(blockId);

            if (blockAliases == null)
            {
                blockAliases = new ArrayList();
                blocksAliases.set(blockId, (List<BlockAlias>) blockAliases);
            }

            ((List)blockAliases).add(ba);
        }
    }

    private static BlockAlias[][] toArrays(List<List<BlockAlias>> listBlocksAliases)
    {
        BlockAlias[][] bas = new BlockAlias[listBlocksAliases.size()][];

        for (int i = 0; i < bas.length; ++i)
        {
            List listBlockAliases = (List)listBlocksAliases.get(i);

            if (listBlockAliases != null)
            {
                bas[i] = (BlockAlias[])((BlockAlias[])listBlockAliases.toArray(new BlockAlias[listBlockAliases.size()]));
            }
        }

        return bas;
    }

    public static void reset()
    {
        blockAliases = (BlockAlias[][])null;
    }
}
