package ru.alastar.minedonate.network.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayerMP;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.network.packets.AccountInfoPacket;
import ru.alastar.minedonate.network.packets.AddMerchPacket;
import ru.alastar.minedonate.network.packets.NeedShopCategoryPacket;
import ru.alastar.minedonate.network.packets.NeedUpdatePacket;
import ru.alastar.minedonate.network.packets.SupportedFeaturesPacket;

public class NeedShopCategoryServerPacketHandler implements IMessageHandler<NeedShopCategoryPacket, IMessage> {
    
	public NeedShopCategoryServerPacketHandler(){

    }
    
    @Override 
    public IMessage onMessage(NeedShopCategoryPacket message, MessageContext ctx) {
    	
		if (MineDonate.m_Enabled) {
			
            EntityPlayerMP serverPlayer = ctx.getServerHandler().playerEntity;
            if ( ! MineDonate . shops . containsKey ( message . shopId ) ) {
            	
            	MineDonate . loadUserShop ( message.shopId ) ;
            	
            }
            
            if ( MineDonate.shops.containsKey(message.shopId) && MineDonate.shops.get(message.shopId).cats.length > message.cat ) {
            	
                for (int j = 0; j < MineDonate.shops.get(message.shopId).cats[message.cat].getMerch().length; ++j) {
                	
                    AddMerchPacket packet = new AddMerchPacket(MineDonate.shops.get(message.shopId).cats[message.cat].getMerch()[j]);
                    MineDonate.networkChannel.sendTo(packet, serverPlayer);
                    
                }
            
            }
            
            MineDonate.networkChannel.sendTo(new NeedUpdatePacket(2), serverPlayer);

        }
		
        return null;
        
    }
}