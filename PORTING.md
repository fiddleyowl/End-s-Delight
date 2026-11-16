# Porting and development notes

Project overview and authorship: [README.md](README.md).

## Source and dependencies

This port starts from End's Delight 2.6.1 for Minecraft 1.21.1, upstream branch `1.21.1`, commit [`5778ed0`](https://github.com/FoggyHillside/End-s-Delight/commit/5778ed081dfb18656f04cb1ba91220bf87ea2bba). Development takes place on branch `26.1.2-neoforge` in [fiddleyowl/End-s-Delight](https://github.com/fiddleyowl/End-s-Delight).

The build pins Minecraft 26.1.2, NeoForge 26.1.2.126, and Java 25. Runtime metadata accepts newer NeoForge builds within the target Minecraft version, but those builds have not all been verified.

The Farmer's Delight dependency is [Farmer's Delight 26 Neo Ver 1.3.3-fix8 by BuaichiZY](https://www.curseforge.com/minecraft/mc-mods/farmers-delight-26-neo-ver/files/9236195). CurseForge project ID `1699768` and file ID `9236195` select the build dependency. The dependency is not bundled in the mod jar and must be installed separately when playing. The original 1.21.1 Farmer's Delight jar is incompatible with this branch.

## Build and run

The Gradle 9.9.0 wrapper is included. Set `JAVA_HOME` to a Java 25 JDK if your default Java is older.

```sh
./gradlew build
./gradlew runClient
./gradlew runServer
./gradlew runGameTestServer
```

Build output is `build/libs/ends_delight-2.6.1+neoforge.26.1.2.jar`; the sources jar is in the same directory.

## Testing

`runGameTestServer` uses an isolated `run-gametest/` directory. Tests in `src/gametest/` are available in development runs and excluded from the distributed jars.

Automated GameTests cover:

- All 63 item-name translation keys, all 75 recipes loading, all 12 advancements loading, and loot modifier loading.
- Food effects and bowl return, drinking animation, fast consumption, and popsicle consumption without a stick remainder.
- All four knives' one-point attack durability cost and knife repair ingredients.
- Single-trigger placed-pie teleport without slice cooldowns or item-use statistics.
- Six-slot stove synchronization and cooking across save/load.
- Wine effect protection, two-block dragon-leg servings, and gristle teleport health cost.

Recipe loading checks do not execute every recipe, and advancement loading checks do not trigger every advancement. Passing these tests does not establish complete equivalence to the original mod.

Client visuals, survival play, naturally generated chorus succulents, entity drops, multiplayer synchronization, and a standalone production server still need manual verification. Use a new test world for initial playtesting; this port has not been validated against existing saves.

## Implementation notes

The port updates registry initialization, tool materials and repair tags, consumable effects and durations, container remainders, block interaction and teleport APIs, loot modifier codecs, recipe ingredients, the advancement background, and client item definitions.

The end stove uses Farmer's Delight's current renderer with its original six slots and item offsets and explicitly synchronizes its inventory. Its saved fields remain `Inventory`, `CookingTimes`, and `CookingTotalTimes`. Knives retain their original one-point durability cost per attack, and chorus popsicles leave no stick. Placed chorus pie retains its separate block-feeding path without held-item cooldowns or consumption statistics.

### Chorus fruit wine

Wine removes one randomly selected eligible harmful effect while preserving beneficial effects and honoring NeoForge's cancellable effect-removal event. The old per-instance `EffectCures` API no longer exists in NeoForge 26.1.

Datapacks can replace former milk-cure exclusions using `data/ends_delight/tags/mob_effect/immune_to_chorus_fruit_wine.json`, for example:

```json
{
  "values": ["othermod:protected_effect"]
}
```

The immunity tag is empty by default. Old mod-specific, per-instance cure metadata cannot be migrated automatically through this tag.

## Names and compatibility

The in-game display name remains **End's Delight**, the mod ID and resource namespace remain `ends_delight`, and the Java package remains `cn.foggyhillside.ends_delight`. Upstream item, block, block-entity, feature, damage-type, recipe, advancement, tag, translation, and configuration names are retained. Block items explicitly use the original `block.*` translation keys.

Port-specific additions are client item definitions, four repair-material tags, and a wine-immunity tag. They introduce no new item/block IDs or custom saved data-component types. Fork attribution is kept in README and NOTICE.

These identifiers are preserved to support replacing this jar with a future official release for the same Minecraft version. Actual save compatibility must still be checked against that release's registry IDs, block states, and saved-data schema when it exists. This does not claim compatibility when upgrading a world from older Minecraft versions.

## License packaging

The original End's Delight copyright and complete [MIT license text](LICENSE) are preserved unchanged; port modifications are also distributed under MIT. Both the runtime and sources jars include `META-INF/LICENSE` and `META-INF/NOTICE`.

The placed-pie behavior adapted from Farmer's Delight is also covered by its original [MIT License](licenses/FarmersDelight-MIT.txt), copyright (c) 2020 vectorwing. This license is included in both jars under `META-INF/licenses/FarmersDelight-MIT.txt`.

The Gradle wrapper retains its Apache License 2.0 notices; a copy is available at [licenses/Gradle-Apache-2.0.txt](licenses/Gradle-Apache-2.0.txt). Farmer's Delight and its 26.1.2 port retain their respective authorship and licensing and are installed separately. See [NOTICE](NOTICE) for full attribution.
