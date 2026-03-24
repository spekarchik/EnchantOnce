package com.pekar.enchantonce.events;

import com.mojang.logging.LogUtils;
import com.pekar.enchantonce.events.handlers.*;
import com.pekar.enchantonce.events.handlers.base.AnvilUpdateEventHandler;
import com.pekar.enchantonce.events.handlers.repair.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import org.slf4j.Logger;

public class AnvilEvents implements IEventHandler
{
    private static final AnvilUpdateEventHandler ANVIL_UPDATE_EVENT_HANDLER_CHAIN =
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
            .attach(new EnchantGearWithBookHandler())
            .getFirst();

    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public void onAnvilUpdateEvent(AnvilUpdateEvent event)
    {
        boolean handled = ANVIL_UPDATE_EVENT_HANDLER_CHAIN.tryHandle(event);

        if (!event.getPlayer().level().isClientSide())
        {
            LOGGER.debug("Handled AnvilUpdateEvent: {}, left: {}, right: {}, result: {}",
                    handled, event.getLeft(), event.getRight(), event.getOutput());
        }
    }
}

