package me.currycookie.world;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class VoidGenerator extends WorldCreator {

    public VoidGenerator(String worldName) {
        super(worldName);
    }

    @Override
    public ChunkGenerator generator() {
        return new VoidChunkGen();
    }

    public class VoidChunkGen extends ChunkGenerator {
        public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
            ChunkData data = createChunkData(world);
            for (int x1 = 0; x1 < 16; x1++) {
                for (int y1 = 0; y1 < 16; y1++) {
                    data.setBlock(x1, 0, y1, Material.AIR);
                }
            }
            return data;
        }

    }
}
