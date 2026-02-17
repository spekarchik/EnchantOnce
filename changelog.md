## 📦 Version 2.2.2

### ✨ New
- **Flint** can now downgrade *enchanted books* by decreasing the level of their enchantments by 1.
    - Enchantments at their minimum level are removed.
    - *Curse* enchantments are preserved.
- A vanilla behavior has been overridden: when combining two items with the same enchantment level, the resulting enchantment level no longer increases. This change is intentional to preserve balance and prevent enchantment scaling (see the description for details).

#### Wind Burst enchantment
- Added a **controlled scaling exception** for the Wind Burst enchantment.
- Introduced an internal **Wind Burst 🔒 lock marker** to distinguish vanilla-origin books from duplicated ones.
- Wind Burst enchantment level can now increase **only when combining unmodified vanilla books**.
- Prevented Wind Burst level scaling on **duplicated, extracted, or modified enchanted books**.
- Ensured Wind Burst progression remains possible without reintroducing infinite scaling exploits.
- Duplicating an enchanted book with Wind Burst now applies the 🔒 lock to the original and all copies.
- Extracting Wind Burst from an item onto a book applies the 🔒 lock to the resulting book.
- The 🔒 lock is preserved during enchantment downgrading with Flint.
- The 🔒 lock exists **only on books** and is never transferred to items.
- Book downgrading removes the **Wind Burst 🔒 lock marker** if no *Wind Burst* enchantment remains on the book.

### ⚙️ Technical Changes
- Vanilla enchantment combination logic selectively restored **only for Wind Burst** under strict conditions.
- Enchantment scaling rules updated to support Wind Burst’s unique vanilla generation mechanics.

### 📝 Improvements
- Now multiple **flints** can be consumed at once.


## 📦 Version 1.1.2
- Code optimizations related to `damageMainHandGear` command.
- No impact on gameplay.


## 📦 Version 1.1.0

### 📝 Improvements
- Allow consuming the required number of repair materials in a single operation.
- Added `damageMainHandGear` console command.

### 🐞 Fixed
- Undamaged staff can no longer be "repaired" or consume resources.
- Number of consumed materials is calculated correctly now. **Trident** now consumes up to **4** items to be completely repaired.


## 📦 Version 1.0.5

### 🐞 Fixed
- Fixed an anvil bug where **all books in the right slot** were consumed when copying enchantments from an enchanted book. Now only the necessary number of books (up to 4) is used.
- Fixed a similar issue when **transferring enchantments from tools or armor to a book**: previously all books were consumed, now only one is used as intended.
- Fixed excessive material consumption during **item repairs**: previously, all materials in the right slot were consumed; now only one unit is used, as expected.


## 📦 Version 1.0.4
### 🛠️ Changed
- Updated meta information: the mod name has been changed.
- The supported Minecraft version range has been capped at 1.21.4 due to incompatibility with NeoForge 21.5.75.
  *(A separate version for MC 1.21.5 has already been published.)*


## 📦 Version 1.0.3
- Initial publishing
