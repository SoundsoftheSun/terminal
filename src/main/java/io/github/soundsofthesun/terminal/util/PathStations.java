package io.github.soundsofthesun.terminal.util;

import io.github.soundsofthesun.terminal.Terminal;
import io.github.soundsofthesun.terminal.block.TBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class PathStations {

    private TagKey<Block> railsTag = TagKey.of(RegistryKeys.BLOCK, Identifier.ofVanilla("rails"));

    public ArrayList<BlockPos> validRails = new ArrayList<>();
    public ArrayList<BlockPos> networkRails = new ArrayList<>();
    public ArrayList<BlockPos> stations = new ArrayList<>();
    public ArrayList<BlockPos> networkStations = new ArrayList<>();
    public HashSet<BlockPos> terminals = new HashSet<>();

    private ServerWorld world;

    public PathStations(ServerWorld world) {
        this.world = world;
    }

    public void getRails(BlockPos startPos, BlockPos endPos) {
        List<BlockPos> pathPositions = new ArrayList<>();
        Map<Long, Long> predecessor = new HashMap<>();

        BiConsumer<BlockPos, Consumer<BlockPos>> enqueueNeighbors = (current, enqueue) -> {
            Direction[] lateral = new Direction[] { Direction.EAST, Direction.WEST, Direction.SOUTH, Direction.NORTH };

            Consumer<BlockPos> enqueueWithPrev = (queued) -> {
                long qKey = queued.asLong();
                long curKey = current.asLong();
                if (!predecessor.containsKey(qKey) && qKey != startPos.asLong()) {
                    predecessor.put(qKey, curKey);
                }
                enqueue.accept(queued);
            };

            for (Direction d : lateral) {
                BlockPos nb = current.offset(d);
                enqueueWithPrev.accept(nb);
                enqueueWithPrev.accept(nb.down());
                enqueueWithPrev.accept(nb.up());
            }
        };

        Function<BlockPos, BlockPos.IterationState> callback = (pos) -> {
            BlockState bs = this.world.getBlockState(pos);
            if (pos.asLong() == endPos.asLong()) {
                stations.add(pos);
                long cur = pos.asLong();
                pathPositions.add(BlockPos.fromLong(cur));

                while (predecessor.containsKey(cur)) {
                    long parent = predecessor.get(cur);
                    if (parent == cur) break;
                    pathPositions.add(BlockPos.fromLong(parent));
                    cur = parent;
                    if (cur == startPos.asLong()) break;
                }

                Collections.reverse(pathPositions);
                validRails.clear();
                validRails.addAll(pathPositions);

                return BlockPos.IterationState.STOP;
            } else if (bs.isIn(railsTag)) {
                return BlockPos.IterationState.ACCEPT;
            } else if (bs.getBlock() == TBlocks.STATION_BLOCK) {
                stations.add(pos);
                return BlockPos.IterationState.ACCEPT;
            } else {
                return BlockPos.IterationState.SKIP;
            }
        };

        int maxDepth = Terminal.CONFIG.max_network_size();
        int maxIterations = 100000;

        BlockPos.iterateRecursively(startPos, maxDepth, maxIterations, enqueueNeighbors, callback);
    }

    public void getNetwork(BlockPos startPos) {
        BiConsumer<BlockPos, Consumer<BlockPos>> enqueueNeighbors = (current, enqueue) -> {
            Direction[] lateral = new Direction[] { Direction.EAST, Direction.WEST, Direction.SOUTH, Direction.NORTH };
            for (Direction d : lateral) {
                BlockPos nb = current.offset(d);
                enqueue.accept(nb);
                enqueue.accept(nb.down());
                enqueue.accept(nb.up());
            }
        };

        Function<BlockPos, BlockPos.IterationState> callback = (pos) -> {
            BlockState bs = this.world.getBlockState(pos);
            if (bs.isIn(railsTag)) {
                networkRails.add(pos);
                return BlockPos.IterationState.ACCEPT;
            } if (bs.getBlock() == TBlocks.STATION_BLOCK) {
                networkRails.add(pos);
                networkStations.add(pos);
                return BlockPos.IterationState.ACCEPT;
            } else if (bs.getBlock() == TBlocks.TERMINAL_BLOCK) {
                networkRails.add(pos);
                terminals.add(pos);
                return BlockPos.IterationState.ACCEPT;
            } else {
                    return BlockPos.IterationState.SKIP;
                }
            };

            int maxDepth = Terminal.CONFIG.max_network_size();
            int maxIterations = 100000;

            BlockPos.iterateRecursively(startPos, maxDepth, maxIterations, enqueueNeighbors, callback);
        }
}

















