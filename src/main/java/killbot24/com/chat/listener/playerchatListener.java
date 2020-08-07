package killbot24.com.chat.listener;

import com.google.common.eventbus.Subscribe;
import killbot24.com.chat.Lan;
import killbot24.com.chat.Logic;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.action.InteractEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;
import org.spongepowered.api.event.item.inventory.UpdateAnvilEvent;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.inventory.slot.OutputSlot;
import org.spongepowered.api.network.PlayerConnection;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.ClickAction;
import org.spongepowered.api.text.channel.MessageChannel;

import java.util.HashMap;
import java.util.List;

/**
 * Created by tjbur on 01/06/2020.
 */
public class playerchatListener{

    private String message;
    private Logic logic = new Logic();
    Lan lan=new Lan();


    @Listener
    public void onChat(MessageChannelEvent.Chat event, @Root Player player) {
        message  = event.getRawMessage().toString();
       Cause a= event.getCause();

       boolean checker= logic.Checkchat(player,getMessage(),"chat");
       if (checker==true){// if checkchat finds word blacklisted
           event.setMessageCancelled(true);
       }
    }


    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event, @Root Player player) {

      Boolean pcheck=  player.hasPermission("cf.base");

      if (pcheck==true){
        logic.Listplayers(player);
      }

    }


    @Listener
    public void onItemForge(ClickInventoryEvent event, @First Player player, @Getter("getTargetInventory") Inventory inventory) {

        // If the inventory in question is an anvil, and the player has the appropriate permissions
        if (inventory.getArchetype() == InventoryArchetypes.ANVIL) {

            // Loop through the transactions for this event, there should really only be 1
            event.getTransactions().forEach(slotTransaction -> {

                // If the player clicked on an output slot, then we know they've forged a new item
                if (slotTransaction.getSlot() instanceof OutputSlot &&
                        slotTransaction.getOriginal().equals(event.getCursorTransaction().getDefault())) {

                    // Get the original itemstack from the transaction, as the final should be air if crafting a new item
                    ItemStack originalStack = slotTransaction.getOriginal().createStack();

                    // Check to make sure that the player didn't click on the empty output slot with an item
                    if (originalStack.getType() != ItemTypes.AIR) {

                        // Grab the display name for the item
                        Text itemName = originalStack.get(Keys.DISPLAY_NAME).orElse(Text.of(originalStack.getTranslation()));
                        String itemNamePlain = itemName.toPlain();
                        lan.staffanvil(player,itemNamePlain);
                        boolean checker= logic.Checkchat(player,itemNamePlain,"Anvil");
                        if (checker==true){
                            event.setCancelled(checker);
                        }

                    }}})
        ;}
    }
    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        logic.Serverstart();
    }



    public String getMessage() {
        return message;
    }




}

