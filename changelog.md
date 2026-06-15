## 📦 Version 2.6.0

### ✨ New
- The **Too Expensive** anvil limitation is now also removed for enchanting operations (previously, only repairs with materials were affected).
- Can be changed in config.


## 📦 Version 2.5.2

### ✨ New
- **Flint** can now downgrade *enchanted books* by decreasing the level of their enchantments by 1.
    - Enchantments at their minimum level are removed.
    - *Curse* enchantments are preserved.

- A vanilla behavior has been overridden: when combining two items with the same enchantment level, the resulting enchantment level no longer increases. This change is intentional to preserve balance and prevent enchantment scaling (see the description for details).

- The XP cost calculation algorithm for combining items and books has been adjusted.
  - It now accounts for cases where vanilla increases an enchantment level but the mod intentionally does not.
  - In all other cases, the XP cost matches vanilla or differs only negligibly.

- **Warped Fungus on a Stick** and **Carrot on a Stick** can now be repaired with **String** using the fixed repair cost mechanics.

#### ⚙️ Configuration
- Added configurable system to enable or disable individual mod features:
  - `allowBookCopying` – allow creating copies of enchanted books
  - `allowGearCopying` – allow copying enchantments directly between gear items
  - `allowMoveEnchantmentsToBook` – allow moving enchantments from gear to books
  - `allowDecreaseEnchantmentLevel` – allow decreasing enchantment level on books using flint
  - `allowFixedRepairCost` – enable fixed XP cost for repairing items with materials
  - `fixedRepairCost` – set XP cost (in levels) for material repairs when fixed repair cost is enabled
  - `preventIncreaseEnchantmentLevel` – prevent combining items from increasing enchantment levels beyond original values
  - `allowNonstandardRepairs` – allow repairing normally non-repairable items with materials (Trident, Shears etc.)
  - `moveEnchantmentsToBookCost` – XP cost (in levels) for moving enchantments from gear to books
  - `keepItemWhenMovingEnchantmentsToBook` - an option to keep gear items when moving enchantments to a book (disabled by default)
  - `gearCopyingCost` – XP cost (in levels) for copying enchantments between gear
  - `maxBookCopies` – maximum number of copies allowed per enchanted book copy operation

#### Console commands
- Replaced the `damageMainHandGear` console command with the following new commands:
  - `damageMainHand [half | <damageValue>]` – set damage of main-hand item
  - `damageArmor [half | <damageValue>]` – set damage of worn armor
  - `repairMainHand [half | <durabilityValue>]` – set durability of main-hand item
  - `repairArmor [half | <durabilityValue>]` – set durability of worn armor
  - `hp [<hpValue>]` – set player health
  - `food [<foodLevel>]` – set hunger level (resets saturation)
  - `enchantMax [all | basic | clear]` – apply max compatible enchantments to main-hand item
  - `enchantArmorMax [all | basic | clear]` – apply max compatible enchantments to worn armor
  - `dayLock [night|cancel]` — set clear weather, set time (day or night), and freeze the weather & day/night cycle.
  - `xp500 [level]` — set player experience to 500 levels by default.

### 📝 Changes
- Updated mod metadata (home URL and description).


## 📦 Version 1.1.2

- Initial publishing
