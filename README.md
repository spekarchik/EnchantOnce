# 🔧 EnchantOnce — Enchanting & Repair Overhaul

A lightweight **configurable** mod that removes enchanting frustration: control XP costs, extract and copy enchantments, and duplicate fully enchanted gear.

Find a rare enchanted book or item once — and never worry about losing it again.

---

## 🎯 Who is this mod for?

**EnchantOnce** is for players who enjoy enchantments but are tired of grinding XP and repeating the same rituals.

If you prefer a smoother, more controlled experience — where your best finds actually matter — this mod is for you.

---

## 📑 Table of Contents

- [Features](#-features)
    - [Flat Repair Cost](#%EF%B8%8F-flat-repair-cost)
    - [Repair with Materials](#-repair-with-materials)
    - [Duplicate Enchanted Books](#-duplicate-enchanted-books)
    - [Extract Enchantments](#-extract-enchantments)
    - [Clone Enchanted Items](#-clone-enchanted-items)
    - [Vanilla Enchantment Combination Change](#%EF%B8%8F-vanilla-enchantment-combination-change)
    - [Downgrade Enchanted Books (Flint)](#-downgrade-enchanted-books-flint)
    - [Wind Burst — Controlled Vanilla Scaling](#%EF%B8%8F-wind-burst--controlled-vanilla-scaling)
    - [Configuration](#%EF%B8%8F-configuration)
- [Compatibility](#-mod-compatibility)
- [Philosophy](#-philosophy)
- [Testing Commands](#-testing--utility-commands-creative--admin)
- [Installation and Technical Information](#%EF%B8%8F-installation-and-technical-information)

---

## 📌 Features

### 🛠️ Flat Repair Cost
- Repairing a damaged item with its base material always costs **2 experience levels** — no matter how many times it's been repaired.
- The "Too Expensive" limitation is removed entirely.
- **Item history is preserved** during repairs (it is not reset or increased).

### 🔄 Repair with Materials
Repair tools and gear using **base materials**, instead of combining duplicate items.

| Item                   | Repair Material        | Amount Repaired         |
|------------------------|------------------------|--------------------------|
| Shield                 | Any Planks             | ½ durability             |
| Shears                 | Iron Ingot             | Full durability          |
| Bow, Fishing Rod       | String                 | ¼ durability             |
| Crossbow               | String                 | ¼ durability             |
| Flint & Steel          | Flint                  | Full durability          |
| Trident                | Prismarine Shard       | ¼ durability             |
| Brush                  | Feather                | ¼ durability             |
| Elytra                 | Phantom Membrane       | Full durability          |

➡ Only works if the item is damaged. The material is consumed.

---

### 📖 Duplicate Enchanted Books
Copy enchanted books using blank books.

- Place one **enchanted book** in the left slot and up to 4 **normal books** in the right.
- Output: up to 5 copies of the enchanted book (original + up to 4 more).
- **XP cost** depends on the enchantments:
  - Calculated from the minimum enchantment cost values.
  - Curses are ignored in the cost.
  - Total cost = `(number of new copies) × (combined cost of enchantments)`.
  - Minimum cost per copy is always **1 level**.

> This system prevents XP farming via book copying + grindstone trickery.
> **Book history is preserved and copied to all resulting books.**

---

### 📚 Extract Enchantments
Move all enchantments from any enchanted item to a single enchanted book.

- Place the **item** in the left anvil slot, and a **book** in the right.
- The item must be **completely intact** (no damage).
- Output is one enchanted book with all enchantments.
- The item is **destroyed** in the process.
- **XP cost:** 1 level.
- **This is the only operation that resets history.**

---

### 🧬 Clone Enchanted Items
Create a perfect duplicate of any enchanted item.

- Place the **enchanted item** in the left slot and an **unenchanted item of the same type** in the right.
- Both items must be **completely intact** (no damage).
- Output: a copy with the **same enchantments**.
- **XP cost:** 25 levels.
- **Item history is preserved and copied to the result.**

---

### ⚖️ Vanilla Enchantment Combination Change
*(Available since 2.0.0 mod version)*

The vanilla enchantment level increase mechanic has been intentionally disabled.

In vanilla Minecraft, combining two enchanted books or items with the same enchantment level increases the resulting enchantment level. This behavior makes sense in vanilla, because enchanted books and items are effectively **single-use** and cannot be duplicated.

**EnchantOnce** introduces the ability to **duplicate enchanted books and clone enchanted items**. Without adjusting vanilla behavior, this would allow players to reach maximum enchantment levels simply by repeatedly cloning and combining items.

To preserve balance and prevent enchantment scaling:
- Combining two books or items with the same enchantment level **no longer increases** the resulting enchantment level.
- The highest existing level is preserved instead.

This change is intentional and required for compatibility with the mod’s duplication mechanics.

---

### 🔻 Downgrade Enchanted Books (Flint)
*(Available since 2.0.0 mod version)*

Lower enchantment levels on enchanted books using **Flint**.

- Place an **enchanted book** in the left anvil slot and **Flint** in the right.
- All **non-curse** enchantments have their level decreased by **1**.
- Enchantments that reach their **minimum level** are removed.
- *Curse* enchantments are **never modified**.
- If no enchantment can be downgraded, the operation is cancelled.

**Cost:**
- **XP:** 1 level  
- **Material:** 1 Flint

This provides a controlled way to adjust enchantment strength without relying on random rerolling or grindstone abuse.

---

### 🌪️ Wind Burst — Controlled Vanilla Scaling

*(Available since 2.2.0 mod version)*

In vanilla Minecraft, **Wind Burst** is a special-case enchantment:

- It **only generates at level I** in Trial Chambers.
- Its intended progression relies on **combining multiple level I books** to increase its level.
- Without vanilla scaling, Wind Burst would become permanently locked at level I.

Since EnchantOnce disables vanilla enchantment level scaling by default, Wind Burst requires a **carefully controlled exception** to preserve its intended gameplay — without opening duplication exploits.

#### 🔒 Wind Burst Lock Mechanic

EnchantOnce introduces an internal, book-only marker enchantment:  
**`Wind Burst 🔒`**

This marker is **not obtainable by players** and exists solely to control Wind Burst scaling behavior.

#### ⚖️ Scaling Rules

Wind Burst enchantment level scaling follows a single consistent rule:

**Allowed:**
- Wind Burst **can increase in level** if the item in the **right slot** is an **Enchanted Book without the 🔒 lock**

**Blocked:**
- In all other cases, Wind Burst **will not scale**, including:
  - The right slot item is **not a book**
  - The right slot book **has the 🔒 lock**
- When scaling is blocked, the **highest existing level is preserved**

#### 📚 Lock Propagation

The 🔒 lock is automatically applied in the following cases:

- **Enchanted Book Duplication**
  - When duplicating a vanilla Wind Burst book:
    - The original and all copies receive the 🔒 lock
    - Resulting books can no longer be used for scaling

- **Book Combination**
  - When combining two Enchanted Books containing Wind Burst:
    - If **either book** has the 🔒 lock, the **resulting book also receives it**
    - This applies **even if the enchantment level increases**

- **Enchantment Extraction**
  - If Wind Burst is extracted from an item onto a book:
    - The resulting book receives the 🔒 lock
    - Prevents item → book → combine scaling loops

- **Downgrading with Flint**
  - The 🔒 lock behaves like a Curse:
    - It is preserved while Wind Burst remains on the book
    - It is removed automatically if Wind Burst is completely removed

#### 🛠️ Item Behavior

- When applying Wind Burst to an item (e.g. Mace):
  - The 🔒 lock is **not transferred**
  - The lock exists **only on enchanted books**

Items can still be duplicated, repaired, and extracted normally without enabling unintended scaling paths.

#### ✅ Result

- Vanilla Wind Burst progression is preserved
- Duplication and cloning remain fully supported
- Infinite enchantment scaling is prevented
- Wind Burst is the **only enchantment** allowed to scale, under strict conditions

---

## ⚙️ Configuration

*(Available since 2.4.0 mod version)*

**EnchantOnce** now includes a fully configurable system that allows you to enable, disable, or tweak nearly every feature of the mod.

You can even **disable all mechanics** and effectively restore full vanilla behavior — making the mod completely non-intrusive if desired.

### 🔧 Available Options

- `allowBookCopying` – allow creating copies of enchanted books  
- `allowGearCopying` – allow copying enchantments directly between gear items  
- `allowMoveEnchantmentsToBook` – allow moving enchantments from gear to books  
- `allowDecreaseEnchantmentLevel` – allow decreasing enchantment level on books using flint  
- `allowFixedRepairCost` – enable fixed XP cost for repairing items with materials  
- `fixedRepairCost` – set XP cost (in levels) for material repairs  
- `preventIncreaseEnchantmentLevel` – prevent enchantment level scaling when combining items  
- `allowNonstandardRepairs` – allow repairing items like Trident, Shears, etc. with materials  
- `moveEnchantmentsToBookCost` – XP cost for moving enchantments to books  
- `gearCopyingCost` – XP cost for copying enchantments between gear  
- `maxBookCopies` – maximum number of copies per book duplication operation  

### 📁 Config Location

The configuration file is generated automatically after launching the game and can be found in the standard mod config directory.

By default, it is located at:
`config/enchantonce-common.toml`

---

## ✅ Mod Compatibility

- Compatible with **vanilla items** and most **modded gear** that follows NeoForge standards.
- Fully supports custom items from my **The Block of Angel** mod.
- No custom blocks, items, or GUIs — purely vanilla mechanics.

---

## 💡 Philosophy

You're not meant to re-grind enchantments forever.  
You earned them — keep them. This mod respects your time and your progress.

---

## 🧪 Testing & Utility Commands (Creative / Admin)

*(Updated in 2.4.0 and later)*

**EnchantOnce** includes a set of utility commands for **testing, debugging, and experimentation**.

### ⌨️ Available Commands

- `damageMainHand [half | <damageValue>]` – set damage of main-hand item  
- `damageArmor [half | <damageValue>]` – set damage of worn armor  
- `repairMainHand [half | <durabilityValue>]` – set durability of main-hand item  
- `repairArmor [half | <durabilityValue>]` – set durability of worn armor  
- `hp [<hpValue>]` – set player health  
- `food [<foodLevel>]` – set hunger level (resets saturation)  
- `enchantMax [all | basic | clear]` – apply max compatible enchantments to main-hand item  
- `enchantArmorMax [all | basic | clear]` – apply max compatible enchantments to worn armor  

### 🧪 Additional Testing Commands *(2.4.1)*

- `dayLock [night|cancel]` – set clear weather, set time (day or night), and freeze the cycle  
- `xp500 [level]` – set player experience (default: 500 levels)

### Notes

- Commands require operator permissions  
- Intended for testing and sandbox use — not survival progression

---

# 🛠️ Installation and Technical Information

## Installation
- Make sure you have **Minecraft 1.20.5 - 26.1** with **NeoForge** or **Forge** installed.
- Download the mod `.jar` file.
- Place it into your `mods` folder.
- Launch the game and enjoy your adventure!

## Technical Details
- **Developer:** Sergey Pekarchik
- **Supported Minecraft versions with NeoForge:** 1.20.5 - 26.1
- **Supported Minecraft versions with Forge:** 1.20.1 - 1.21.5 (no plans to support later versions)

---

## 🧩 Related Mods

**EnchantOnce** is fully compatible with the following mods by the same author:

- **[🧭 Compass Cleaner](https://modrinth.com/mod/compass-cleaner)** — adds a minimalist recipe to reset a _Lodestone Compass_ to a regular _Compass_.  
    Ideal for tidying up obsolete compasses after you lose or remove their lodestone targets.

- **[🌟 Pouch & Paper](https://modrinth.com/mod/pouch-and-paper)** — introduces compact forms of tradeable resources for easier storage and trading:  
  • paper (stackable and block form),  
  • ink and glow ink (bottled),  
  • leather (bundled),  
  • seeds (in pouches),  
  • feathers (as compact packs).  
  Includes the *Burnt Paper Block* — crafted by setting a Paper Block on fire. It attracts creepers and repels bees, perfect for traps and moody builds.

- **[🌟 The Block of Angel](https://modrinth.com/mod/angel-block-mod)**
  An exploration-focused mod that rewards curiosity and reduces repetitive grind — while keeping the core survival balance intact. Perfect for players who want each discovery to matter.
  Start with almost nothing — earn every shortcut.  
  This mod transforms Minecraft into a world of meaningful adventure and artifact-driven progression. No grind, no farms — just clever tools, dangerous dungeons, and rewards you must conquer.

  • Progression-focused gameplay: leather armor, stone weapons, and real survival.  
  • Unique tools: magnetic rods, precision builders, instant crop planters.  
  • Legendary gear: 6 armor sets, powerful weapons, and upgrade paths.  
  • Angel Block & Rod: sacred artifacts that protect and purify your world.  
  • No custom UIs — pure, immersive gameplay.

  Ideal for adventurers who want to *explore*, *survive*, and *earn* their power — not dig tunnels forever.

---

## ☕ Support Development

If you enjoy the mod and would like to support future updates and new features, you can support me here:  
[Buy me a coffee on Ko-fi!](https://ko-fi.com/sergeypekarchik)

Your support means a lot and helps me dedicate more time to creating new content and improving the mod! ❤️

---
