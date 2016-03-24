package gigaherz.survivalist.rocks;

import gigaherz.survivalist.Survivalist;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

import java.util.List;

public class ItemRock extends Item
{
    static final String[] subNames = {
            ".rock", ".rock_andesite", ".rock_diorite", ".rock_granite"
    };

    public ItemRock()
    {
        setHasSubtypes(true);
        setUnlocalizedName(Survivalist.MODID + ".rock");
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        int meta = stack.getMetadata();

        if (meta > subNames.length)
            return getUnlocalizedName();

        return "item." + Survivalist.MODID + subNames[meta];
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
    {
        for (int i = 0; i < subNames.length; i++)
        { subItems.add(new ItemStack(this, 1, i)); }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
        if (!playerIn.capabilities.isCreativeMode)
        {
            --itemStackIn.stackSize;
        }

        worldIn.playSoundAtEntity(playerIn, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!worldIn.isRemote)
        {
            worldIn.spawnEntityInWorld(new EntityRock(worldIn, playerIn));
        }

        playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);

        return itemStackIn;
    }
}
