# End's Delight — NeoForge 26.1.2

An unofficial port of [End's Delight by FoggyHillside](https://github.com/FoggyHillside/End-s-Delight), a culinary addon for Farmer's Delight. This branch starts from upstream's Minecraft 1.21.1 version 2.6.1. The original author, credits, mod ID, and content IDs are retained.

Maintained by **fiddleyowl** in [fiddleyowl/End-s-Delight](https://github.com/fiddleyowl/End-s-Delight), on branch `26.1.2-neoforge`. The upstream baseline is commit [`5778ed0`](https://github.com/FoggyHillside/End-s-Delight/commit/5778ed081dfb18656f04cb1ba91220bf87ea2bba).

The in-game display name is the original **End's Delight**, with mod ID and resource namespace `ends_delight` and Java package `cn.foggyhillside.ends_delight`. Upstream item, block, block-entity, feature, damage-type, recipe, advancement, tag, translation, and configuration names are retained. Port-specific additions are client item definitions, repair-material tags, and a wine-immunity tag; they introduce no new item/block IDs or custom saved data-component types. Fork attribution is kept in this README and NOTICE.

These identifiers are preserved to support replacing this jar with a future official release for the same Minecraft version. Actual save compatibility must still be checked against that release's registry IDs, block states, and saved-data schema when it exists. This does not claim compatibility when upgrading a world from older Minecraft versions.

## Requirements

- Minecraft **26.1.2** and NeoForge **26.1.2.126** or newer within the 26.1.2 series.
- Java **25**.
- [Farmer's Delight 26 Neo Ver **1.3.3-fix8** by BuaichiZY](https://www.curseforge.com/minecraft/mc-mods/farmers-delight-26-neo-ver/files/9236195). The build pins CurseForge project `1699768`, file `9236195`; this dependency must also be installed separately when playing.

The original 1.21.1 Farmer's Delight jar is incompatible with this branch.

## Build and development

```sh
./gradlew build
./gradlew runClient
./gradlew runServer
./gradlew runGameTestServer
```

The Gradle 9.9.0 wrapper is included. Set `JAVA_HOME` to a Java 25 JDK if your default Java is older. Build output is `build/libs/ends_delight-2.6.1+neoforge.26.1.2.jar`; the sources jar is in the same directory.

`runGameTestServer` uses an isolated `run-gametest/` directory. Tests in `src/gametest/` are available in development runs and excluded from the distributed jars.

## Port status

The port updates the build toolchain, registry IDs, tool materials and repair tags, consumable effects and durations, container remainders, block interaction and teleport APIs, loot modifier codecs, recipe ingredients, advancement background, and client item definitions. The end stove uses Farmer's Delight's current renderer with its original six slots and item offsets and explicitly synchronizes its inventory. Knives retain their original one-point durability cost per attack, and chorus popsicles leave no stick. Placed chorus pie retains its separate block-feeding path without held-item cooldowns or consumption statistics.

Automated GameTests cover all 75 recipes, all 12 advancements, loot modifier loading, food effects and bowl return, drinking animation and fast consumption, all four knives' attack durability, repair ingredients, popsicle consumption, single-trigger pie teleport without slice cooldowns or item-use statistics, six-slot stove synchronization and cooking across save/load, wine effect protection, two-block dragon-leg servings, and gristle teleport health cost.

Chorus fruit wine removes one randomly selected eligible harmful effect while preserving beneficial effects and honoring NeoForge's cancellable effect-removal event. The old per-instance `EffectCures` API no longer exists in NeoForge 26.1. Datapacks can replace former milk-cure exclusions using `data/ends_delight/tags/mob_effect/immune_to_chorus_fruit_wine.json`, for example:

```json
{
  "values": ["othermod:protected_effect"]
}
```

The immunity tag is empty by default. Old mod-specific, per-instance cure metadata cannot be migrated automatically through this tag.

Client visuals, survival play, naturally generated chorus succulents, entity drops, multiplayer synchronization, and a standalone production server still need manual verification. A successful build and development GameTest run do not establish full gameplay compatibility. Use a new test world for initial playtesting; this port has not been validated against existing saves.

## License and attribution

End's Delight is licensed under the [MIT License](LICENSE), copyright (c) 2022 FoggyHillside. The original copyright and complete license text are preserved unchanged; port modifications are also distributed under MIT. Both the runtime and sources jars include `META-INF/LICENSE` and `META-INF/NOTICE`. See [NOTICE](NOTICE) for upstream provenance, port changes, and dependency attribution.

The placed-pie behavior adapted from Farmer's Delight is also covered by its original [MIT License](licenses/FarmersDelight-MIT.txt), copyright (c) 2020 vectorwing. This license is included in both jars under `META-INF/licenses/FarmersDelight-MIT.txt`.

The Gradle wrapper retains its Apache License 2.0 notices; a copy is available at [licenses/Gradle-Apache-2.0.txt](licenses/Gradle-Apache-2.0.txt). This community port is not an official release by the original author. Farmer's Delight and its 26.1.2 port retain their respective authorship and licensing and are installed separately.
