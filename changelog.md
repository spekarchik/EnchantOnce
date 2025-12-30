## 📦 Version 2.0.1

### 🐞 Fixed
- Fixed a bug where combining two items with the same enchantment level increased the resulting enchantment level.


## 📦 Version 2.0.0

### ✨ New
- **Flint** can now downgrade *enchanted books* by decreasing the level of their enchantments by 1.
    - Enchantments at their minimum level are removed.
    - *Curse* enchantments are preserved.
- A vanilla behavior has been overridden: when combining two items with the same enchantment level, the resulting enchantment level no longer increases. This change is intentional to preserve balance and prevent enchantment scaling (see the description for details).


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
