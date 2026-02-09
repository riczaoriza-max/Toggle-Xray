package com.kotoritos.togglexray;

import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.BlockTags;

final class XrayBlockFilter {
    private static final Set<Block> ALWAYS_VISIBLE = Set.of(
        Blocks.ANCIENT_DEBRIS,
        Blocks.CHEST,
        Blocks.TRAPPED_CHEST,
        Blocks.SPAWNER
    );

    private XrayBlockFilter() {
    }

    static boolean shouldRender(BlockState state) {
        if (state.isIn(BlockTags.ORES)) {
            return true;
        }
        return ALWAYS_VISIBLE.contains(state.getBlock());
    }
}
