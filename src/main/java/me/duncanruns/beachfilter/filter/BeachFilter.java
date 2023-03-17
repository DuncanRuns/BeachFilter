package me.duncanruns.beachfilter.filter;

import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.biomeutils.source.OverworldBiomeSource;
import kaptainwutax.featureutils.misc.SpawnPoint;
import kaptainwutax.mcutils.util.pos.BPos;
import kaptainwutax.mcutils.version.MCVersion;
import kaptainwutax.terrainutils.terrain.OverworldTerrainGenerator;

public class BeachFilter {
    private static final MCVersion MCVERSION = MCVersion.v1_16_1;
    private static final SpawnPoint SPAWN_POINT = new SpawnPoint();

    private BeachFilter() {
    }

    public static boolean test(long seed, boolean filterForest) {
        OverworldBiomeSource overworldBiomeSource = new OverworldBiomeSource(MCVERSION, seed);
        BPos spawnPoint = SPAWN_POINT.getSpawnPoint(new OverworldTerrainGenerator(overworldBiomeSource));
        if (filterForest && !overworldBiomeSource.getBiome(spawnPoint).equals(Biomes.FOREST)) {
            return false;
        }
        for (int xOff = -1; xOff <= 1; xOff++) {
            for (int zOff = -1; zOff <= 1; zOff++) {
                if (xOff == 0 && zOff == 0) {
                    continue;
                }
                if (overworldBiomeSource.getBiome(spawnPoint.add(xOff * 10, 0, zOff * 10)).equals(Biomes.BEACH)) {
                    return true;
                }
            }
        }
        return false;
    }
}
