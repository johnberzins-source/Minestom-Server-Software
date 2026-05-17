package me.bunnyking;

import net.minestom.server.instance.InstanceContainer
import net.minestom.server.instance.block.Block
import java.util.random.RandomGenerator

public class TerrainGenerator {

    // Seed for noise generation
    RandomGenerator randomCentral = RandomGenerator.getDefault()
    long seed = randomCentral.nextLong()

    public TerrainGenerator(InstanceContainer instanceContainer) {

        //generator for the world, using a simple flat world as an example
        instanceContainer.setGenerator(algorithm -> {
            // Generate a simple flat world at y = 40
            algorithm.modifier().fillHeight(0, 40, Block.GRASS_BLOCK)
        })

    }



}