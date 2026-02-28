## 📦 Version 2.3.1

### 🐞 Fixed
- XP cost for combining enchantments now correctly accounts for existing enchantments and prior work, matching Vanilla behavior.


## 📦 Version 2.3.0

### 📝 Improvements
- The XP cost calculation algorithm for combining items and books has been adjusted.
  - It now accounts for cases where vanilla increases an enchantment level but the mod intentionally does not.
  - In all other cases, the XP cost matches vanilla or differs only negligibly.
  - **Wind Burst 🔒** enchantment no longer increases the XP cost.
- The *Wind Burst* enchantment level is now increased:
  - When combining a **Mace** with an **Enchanted Book**.
  - When combining two books and the right book does not contain the **Wind Burst 🔒** marker, regardless of whether the left one does.  
    In this case, the marker is moved to the result item.


## 📦 Version 2.2.1

### 📝 Improvements
- Book downgrading now removes the **Wind Burst 🔒 lock marker** if no *Wind Burst* enchantment remains on the book.


## 📦 Version 2.2.0

### 📝 Improvements
- Added a **controlled scaling exception** for the Wind Burst enchantment.
- Introduced an internal **Wind Burst 🔒 lock marker** to distinguish vanilla-origin books from duplicated ones.
- Wind Burst enchantment level can now increase **only when combining unmodified vanilla books**.
- Prevented Wind Burst level scaling on **duplicated, extracted, or modified enchanted books**.
- Ensured Wind Burst progression remains possible without reintroducing infinite scaling exploits.

### 🔒 Enchantment Safety & Exploit Prevention
- Duplicating an enchanted book with Wind Burst now applies the 🔒 lock to the original and all copies.
- Extracting Wind Burst from an item onto a book applies the 🔒 lock to the resulting book.
- The 🔒 lock is preserved during enchantment downgrading with Flint.
- The 🔒 lock exists **only on books** and is never transferred to items.

### ⚙️ Technical Changes
- Vanilla enchantment combination logic selectively restored **only for Wind Burst** under strict conditions.
- Enchantment scaling rules updated to support Wind Burst’s unique vanilla generation mechanics.


## 📦 Version 2.1.0

### 🐞 Fixed
- Fixed a bug where an existing enchantment with a higher level was ignored and didn't influence the resulting item.

### 📝 Improvements
- Now multiple **flints** can be consumed at once.


## 📦 Version 2.0.2

### 🐞 Fixed
- Fixed a bug in *Survival Mode* where incompatible enchantments could be combined on a single item under certain conditions (e.g. *Silk Touch* and *Fortune*).


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


## 📦 Version 1.1.1

### 📝 Improvements
- Allow consuming the required number of repair materials in a single operation.
- Added `damageMainHandGear` console command.

### 🐞 Fixed
- Undamaged staff can no longer be "repaired" or consume resources.
- Number of consumed materials is calculated correctly now. **Trident** now consumes up to **4** items to be completely repaired.
- Mod was split into two separate versions due to incompatibilities between Minecraft *1.21.5–1.21.8* and *1.21.9–1.21.11*.


## 📦 Version 1.0.5

### 🐞 Fixed
- Fixed an anvil bug where **all books in the right slot** were consumed when copying enchantments from an enchanted book. Now only the necessary number of books (up to 4) is used.
- Fixed a similar issue when **transferring enchantments from a tool or armor to a book**: previously all books were consumed, now only one is used as intended.


## 📦 Version 1.0.4
- Promoted from beta to stable release

## 📦 Version 1.0.0-beta
- Initial publishing
