# 🔧 EnchantOnce — Enchanting & Repair Overhaul

A minimalistic mod that reworks enchanting and repairing: fixed XP costs, enchant extraction, book copying, and full gear duplication — all using vanilla mechanics.

---

## 📌 Features

### 🛠️ Flat Repair Cost
- Repairing any damaged item always costs **2 experience levels** — no matter how many times it's been repaired.
- The "Too Expensive" limitation is removed entirely.

### 🔄 Repair with Materials
Repair tools and gear using **base materials**, instead of combining duplicate items.

| Item                   | Repair Material        | Amount Repaired         |
|------------------------|------------------------|--------------------------|
| Elytra                 | Phantom Membrane       | ¼ durability             |
| Shield                 | Any Planks             | ½ durability             |
| Shears                 | Iron Ingot             | Full durability          |
| Bow, Fishing Rod       | String                 | ¼ durability             |
| Crossbow               | String                 | ¼ durability             |
| Flint & Steel          | Flint                  | Full durability          |
| Trident                | Prismarine Shard       | ¼ durability             |
| Brush                  | Feather                | ¼ durability             |

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

---

### 📚 Extract Enchantments
Move all enchantments from any enchanted item to a single enchanted book.

- Place the **item** in the left anvil slot, and a **book** in the right.
- Output is one enchanted book with all enchantments.
- The item is **destroyed** in the process.
- **XP cost:** 1 level.

---

### 🧬 Clone Enchanted Items
Create a perfect duplicate of any enchanted item.

- Place the **enchanted item** in the left slot and an **unenchanted item of the same type** in the right.
- Output: a copy with the **same enchantments and durability**.
- **XP cost:** 25 levels.

---

## ✅ Compatibility

- Compatible with **vanilla items** and most **modded gear** that follows NeoForge standards.
- Fully supports custom items from my **The Block of Angel** mod.
- No custom blocks, items, or GUIs — purely vanilla mechanics.

---

## 💡 Philosophy

You're not meant to re-grind enchantments forever.  
You earned them — keep them. This mod respects your time and your progress.

---

# 🛠️ Installation and Technical Information

## Installation
- Make sure you have **Minecraft 1.21.4** or **1.21.5** with **NeoForge** installed.
- Download the mod `.jar` file.
- Place it into your `mods` folder.
- Launch the game and enjoy your adventure!

## Technical Details
- **Developer:** Sergey Pekarchik
- **Supported NeoForge versions:** 1.21.4 and 1.21.5

---

## 🧩 Related Mods

EnchantOnce is fully compatible with the following mods by the same author:

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
