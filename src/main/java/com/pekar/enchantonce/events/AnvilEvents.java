package com.pekar.enchantonce.events;

import com.mojang.logging.LogUtils;
import com.pekar.enchantonce.events.handlers.base.AnvilEventHandler;
import com.pekar.enchantonce.events.handlers.craft.KeepItemAfterMovingEnchsToBookHandler;
import com.pekar.enchantonce.events.handlers.update.*;
import com.pekar.enchantonce.events.handlers.update.repair.*;
import org.slf4j.Logger;

public class AnvilEvents implements IEventHandler
{
    private static final AnvilEventHandler<AnvilUpdateEvent> ANVIL_UPDATE_EVENT_HANDLER_CHAIN =
            new ElytraRepairHandler().asFirst()
            .attach(new ShieldRepairHandler())
            .attach(new VanillaRepairHandler())
            .attach(new ShearsRepairHandler())
            .attach(new BowRepairHandler())
            .attach(new FishingRodRepairHandler())
            .attach(new CrossbowRepairHandler())
            .attach(new BrushRepairHandler())
            .attach(new FlintAndSteelRepairHandler())
            .attach(new TridentRepairHandler())
            .attach(new DecreaseBookEnchantmentsHandler())
            .attach(new CopyEnchantedBookHandler())
            .attach(new MoveEnchantmentsToBookHandler())
            .attach(new CombineEnchantedBooksHandler())
            .attach(new CopyEnchantedGearHandler())
            .attach(new CombineEnchantedItemsHandler())
            .getFirst();

    private static final AnvilEventHandler<AnvilCraftPostEvent> ANVIL_CRAFT_EVENT_HANDLER_CHAIN =
            new KeepItemAfterMovingEnchsToBookHandler();

    private static final Logger LOGGER = LogUtils.getLogger();

//    @SubscribeEvent
    public static void onAnvilUpdateEvent(AnvilUpdateEvent event)
    {
        boolean handled = ANVIL_UPDATE_EVENT_HANDLER_CHAIN.tryHandle(event);

        if (!event.getPlayer().level().isClientSide())
        {
            LOGGER.debug("Handled AnvilUpdateEvent: {}, left: {}, right: {}, result: {}",
                    handled, event.getLeft(), event.getRight(), event.getOutput());
        }
    }

//    @SubscribeEvent
    public void onAnvilCraftEvent(AnvilCraftPostEvent event)
    {
        boolean handled = ANVIL_CRAFT_EVENT_HANDLER_CHAIN.tryHandle(event);

        if (!event.getPlayer().level().isClientSide())
        {
            LOGGER.debug("Handled AnvilCraftEvent: {}, left: {}, right: {}, result: {}",
                    handled, event.getLeft(), event.getRight(), event.getOutput());
        }
    }
}

