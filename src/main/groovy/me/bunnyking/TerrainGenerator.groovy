package me.bunnyking;

import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;

public class TerrainGenerator {

    public TerrainGenerator(InstanceContainer instanceContainer) {


        instanceContainer.setGenerator(algorithm -> {
            // Generate a simple flat world at y = 40
            algorithm.modifier().fillHeight(0, 40, Block.GRASS_BLOCK);
        });

    }



}