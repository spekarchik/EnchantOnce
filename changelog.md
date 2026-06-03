## 📦 Version 2.5.2

### ✨ New
- **Flint** can now downgrade *enchanted books* by decreasing the level of their enchantments by 1.
    - Enchantments at their minimum level are removed.
    - *Curse* enchantments are preserved.

- A vanilla behavior has been overridden: when combining two items with the same enchantment level, the resulting enchantment level no longer increases. This change is intentional to preserve balance and prevent enchantment scaling (see the description for details).

- The XP cost calculation algorithm for combining items and books has been adjusted.
  - It now accounts for cases where vanilla increases an enchantment level but the mod intentionally does not.
  - In all other cases, the XP cost matches vanilla or differs only negligibly.
  - **Wind Burst 🔒** marker enchantment doesn't increas the XP cost.

- **Warped Fungus on a Stick** and **Carrot on a Stick** can now be repaired with **String** using the fixed repair cost mechanics.

#### Wind Burst enchantment
- Added a **controlled scaling exception** for the Wind Burst enchantment.
- Introduced an internal **Wind Burst 🔒 lock marker** to distinguish vanilla-origin books from duplicated ones.
- Wind Burst enchantment level can now increase **only when combining unmodified vanilla books**.
- The *Wind Burst* enchantment level is now increased:
  - When combining a **Mace** with an **Enchanted Book**.
  - When combining two books and the right book does not contain the **Wind Burst 🔒** marker, regardless of whether the left one does.  
    In this case, the marker is moved to the result item.
- Prevented Wind Burst level scaling on **duplicated or extracted enchanted books**.
- Ensured Wind Burst progression remains possible without reintroducing infinite scaling exploits.
- Duplicating an enchanted book with Wind Burst now applies the 🔒 lock to the original and all copies.
- Extracting Wind Burst from an item onto a book applies the 🔒 lock to the resulting book.
- The 🔒 lock is preserved during enchantment downgrading with Flint.
- The 🔒 lock exists **only on books** and is never transferred to items.
- Book downgrading removes the **Wind Burst 🔒 lock marker** if no *Wind Burst* enchantment remains on the book.

#### ⚙️ Technical Changes
- Vanilla enchantment combination logic selectively restored **only for Wind Burst** under strict conditions.
- Enchantment scaling rules updated to support Wind Burst’s unique vanilla generation mechanics.

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

### 📝 Improvements
- Now multiple **flints** can be consumed at once.

### 📝 Changes
- Updated mod metadata (home URL and description).


## 📦 Version 1.1.2

- Initial publishing
