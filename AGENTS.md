# AGENTS.md - AsteroidsFX Developer Guide

## Project Overview

**AsteroidsFX** is a modular Java game engine implementing a classic Asteroids arcade game using JavaFX and Spring Framework. The architecture uses Java Module System (JPMS) with a plugin-based service provider pattern for loose coupling between game systems.

**Key Stack:**
- **Build:** Maven (multi-module parent-child structure)
- **UI Framework:** JavaFX 21.0.2
- **DI/Wiring:** Spring Framework 6.1.3 (AnnotationConfigApplicationContext)
- **Module System:** Java 9+ JPMS with ServiceLoader
- **Language:** Java 1.9+

## Architecture Pattern: Plugin-Based Service Architecture

The codebase uses **Java Module System + Service Provider Interface (SPI) + Spring DI** for extensibility:

### Core Concepts:

1. **Common Interface Module** (`Common/`): Defines all service contracts
   - `IGamePluginService`: Initializes game systems (called once at startup)
   - `IEntityProcessingService`: Updates entities each frame (core game loop)
   - `IPostEntityProcessingService`: Post-update processing (e.g., collision detection)
   - **Data structures:** `GameData`, `World`, `Entity`, `GameKeys`

2. **Plugin Modules** (Player, Bullet, Asteroids, Collision, CommonBullet, CommonAsteroids):
   - Each declares `provides` statements in `module-info.java`
   - Implements interfaces and registers via ServiceLoader
   - Built as separate JARs, dynamically discovered at runtime

3. **Core Module** (`Core/`): 
   - Entry point with `Main.java` (extends JavaFX Application)
   - `Game.java` handles rendering loop and keyboard input
   - `ModuleConfig.java`: Spring config using ServiceLoader to wire all plugins

### Service Resolution Flow:

```
Main.start() → Spring AnnotationConfigApplicationContext
  ↓
ModuleConfig beans → ServiceLoader.load(IGamePluginService.class)
  ↓
Game receives lists of services (injected via constructor)
  ↓
Game.start() calls IGamePluginService.start() for all plugins
  ↓
Game.render() loop: 
  - Call IEntityProcessingService.process() for each service
  - Call IPostEntityProcessingService.process() (collision)
  - Update JavaFX Polygons from Entity coordinates
```

## Critical Build & Deployment Workflow

### Build System:
- **Multi-module Maven:** Parent `pom.xml` defines dependencyManagement; child modules define dependencies
- **Maven Profiles:** Auto-activated based on presence of `src/` folder
  - `parent-project`: Cleans `mods-mvn/` and `libs/`
  - `child-project`: Copies built JARs to `mods-mvn/` directory

### Build & Run Commands:
```bash
# Build all modules
mvn clean install

# Run the application
mvn exec:exec  # From root - launches Main via exec-maven-plugin
```

**Important:** The exec plugin configures:
- `--module-path=mods-mvn` (all JARs including plugins)
- `--add-modules javafx.graphics`
- Entry point: `Core/dk.sdu.mmmi.cbse.main.Main`

### Directory Structure for Runtime:
```
mods-mvn/           # Built JAR files (populated by Maven)
  ├── Core-*.jar
  ├── Common-*.jar
  ├── Player-*.jar
  ├── Bullet-*.jar
  ├── Asteroids-*.jar
  ├── Collision-*.jar
  └── javafx-*.jar
```

## Module Dependencies & Naming

**Dependency Graph** (requires-provides):
- `Core` requires: `Common`, `CommonBullet`, javafx.graphics, Spring
- `Player`, `Bullet`, `Asteroids` require: `Common`, implement `IGamePluginService` + `IEntityProcessingService`
- `Collision` requires: `Common`, implements `IPostEntityProcessingService`
- `CommonAsteroids`, `CommonBullet`: Domain-specific interfaces (e.g., `IAsteroidSplitter`, `BulletSPI`)

**Module Naming Convention:**
- Package prefix: `dk.sdu.mmmi.cbse`
- Module declarations in `module-info.java` use simplified names: `module Player { }`, `module Collision { }`

## Adding New Features or Plugins

### To Add a New Game System (e.g., PowerUps):

1. **Create Module Structure:**
   ```
   PowerUps/
   ├── pom.xml (parent reference, depends on Common)
   ├── src/main/java/
   │   ├── module-info.java
   │   └── dk/sdu/mmmi/cbse/powersystem/
   │       ├── PowerPlugin.java (implements IGamePluginService)
   │       └── PowerProcessor.java (implements IEntityProcessingService)
   ```

2. **Declare in `module-info.java`:**
   ```java
   module PowerUps {
       requires Common;
       provides IGamePluginService with dk.sdu.mmmi.cbse.powersystem.PowerPlugin;
       provides IEntityProcessingService with dk.sdu.mmmi.cbse.powersystem.PowerProcessor;
   }
   ```

3. **Add module to root `pom.xml` modules list**

4. **Implement Services:**
   - `IGamePluginService.start()`: Initialize power entities in World
   - `IEntityProcessingService.process()`: Update power positions/effects

5. **Build:** `mvn clean install` automatically discovers and loads via ServiceLoader

### Data Sharing Between Systems:

- **Shared mutable state:** `GameData` and `World` (passed to all processors)
- **Entity Coordinates:** Store in `Entity` objects (Polygon coordinates)
- **SPI Calls:** Use domain-specific SPIs (e.g., `BulletSPI` in Bullet module for cross-system communication)

## Key Code Patterns

### Entity Processing Service Pattern:
```java
// All processors follow this contract:
public class MyProcessor implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            // Update entity based on gameData.getKeys(), gameData.getDisplayWidth(), etc.
            entity.setX(entity.getX() + velocity);
        }
    }
}
```

### Game Loop Execution Order (see Game.java):
1. Call all `IGamePluginService.start()` (initialization)
2. Each frame:
   - Read keyboard input via `GameData.getKeys()`
   - Execute all `IEntityProcessingService.process()` in ServiceLoader order
   - Execute `IPostEntityProcessingService.process()` (collision detection)
   - Update JavaFX Scene by syncing `Entity` polygonCoordinates to visual `Polygon` shapes

### Spring Bean Discovery:
```java
// ModuleConfig.java uses ServiceLoader inside Spring @Bean methods
@Bean
public List<IEntityProcessingService> entityProcessingServiceList() {
    return ServiceLoader.load(IEntityProcessingService.class)
        .stream()
        .map(ServiceLoader.Provider::get)
        .collect(toList());
}
```

## Common Gotchas & Debugging

1. **ServiceLoader Not Finding Plugins:** Ensure JAR is in `mods-mvn/` and `module-info.java` has correct `provides` statement
2. **Missing Module Declarations:** All Java files must be in packages declared in `module-info.java` `exports` or `opens` to
3. **Spring Dependency Injection:** Spring scans only Spring modules; plugins auto-loaded via ServiceLoader (not Spring @ComponentScan)
4. **KeyCode Input:** Only processed in `Game.start()` scene event handlers; check `GameKeys` state in processors

## Testing & Development

- **No dedicated test framework found** in codebase (no JUnit dependencies)
- All processor logic can be unit-tested independently by creating mock `GameData` and `World`
- Plugin testing: Create test `module-info.java` variations with mock service implementations

## File Locations Summary

| Component | Path | Purpose |
|-----------|------|---------|
| Main Entry | `Core/src/main/java/dk/sdu/mmmi/cbse/main/Main.java` | JavaFX Application launcher |
| Spring Config | `Core/src/main/java/dk/sdu/mmmi/cbse/main/ModuleConfig.java` | ServiceLoader bean wiring |
| Game Loop | `Core/src/main/java/dk/sdu/mmmi/cbse/main/Game.java` | Render loop & input handling |
| Core Contracts | `Common/src/main/java/dk/sdu/mmmi/cbse/common/` | IGamePluginService, IEntityProcessingService, etc. |
| Player System | `Player/src/main/java/dk/sdu/mmmi/cbse/playersystem/` | User-controlled entity |
| Bullet System | `Bullet/src/main/java/dk/sdu/mmmi/cbse/bulletsystem/` | Projectile management |
| Asteroid System | `Asteroids/src/main/java/dk/sdu/mmmi/cbse/asteroid/` | Asteroid spawning & splitting |
| Collision | `Collision/src/main/java/dk/sdu/mmmi/cbse/collisionsystem/` | Post-processing collision detection |




