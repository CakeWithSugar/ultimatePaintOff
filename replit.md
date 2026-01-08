# UltimatePaintOff - Minecraft Plugin

## Overview
UltimatePaintOff is a Minecraft Paper/Spigot plugin for a paint-off style minigame. Players compete in teams to paint the arena with their team's color using various weapons and ultimate abilities.

## Project Type
This is a **Minecraft plugin** (Java JAR library), not a runnable web application. The compiled JAR file needs to be deployed to a Minecraft Paper/Spigot server.

## Build System
- **Language**: Java 19
- **Build Tool**: Maven
- **API**: Paper API 1.19.4

## How to Build
Run the build workflow or execute:
```bash
mvn clean package -DskipTests
```

The compiled plugin JAR will be at: `target/ultimatePaintOff-1.0.jar`

## How to Use
1. Build the plugin using Maven
2. Copy `target/ultimatePaintOff-1.0.jar` to your Minecraft server's `plugins/` folder
3. Restart your Minecraft server
4. The plugin will be loaded automatically

## Project Structure
```
src/main/java/org/cws/ultimatePaintOff/
├── UltimatePaintOff.java       # Main plugin class
├── BasicValues.java            # Configuration values
├── arsenal/                    # Weapons and abilities
│   ├── primaryWeapons/        # Main weapons (Nova, Snap, TriAtler, Akonda)
│   └── ultimateWeapons/       # Ultimate abilities
├── executors/                  # Command handlers
├── listeners/                  # Event listeners
├── listsAndInventories/        # Inventory UIs
└── managers/                   # Game logic managers
```

## Plugin Commands
- `/poarsenal` - Open arsenal inventory
- `/poqueue` - Join queue
- `/poleave` - Leave queue/game
- `/postart` - Start game
- `/poinfo` - Show queue info
- `/poarena` - Set arena positions
- `/postop` - Stop game

## Compatibility Notes
This plugin was originally written for Minecraft 1.21+ but has been adapted to work with Paper API 1.19.4 for compatibility with Java 19. Some visual effects (particles) use equivalent alternatives from the older API.
