package carpentersblocks.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import carpentersblocks.CarpentersBlocks;
import carpentersblocks.api.ICarpentersChisel;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCarpentersChisel extends ItemBase implements ICarpentersChisel {

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon(CarpentersBlocks.MODID + ":" + "chisel");
    }
    
    @Override
    public void onChiselUse(World world, EntityPlayer entityPlayer)
    {
        entityPlayer.getCurrentEquippedItem().damageItem(1, entityPlayer);
    }
    
    @Override
    public boolean canUseChisel(World world, EntityPlayer entityPlayer)
    {
        return true;
    }
    
}
