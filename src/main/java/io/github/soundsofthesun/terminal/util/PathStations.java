package io.github.soundsofthesun.terminal.util;

import io.github.soundsofthesun.terminal.Terminal;
import io.github.soundsofthesun.terminal.block.TBlocks;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class PathStations {

    private TagKey<Block> railsTag = TagKey.create(Registries.BLOCK, ResourceLocation.withDefaultNamespace("rails"));

    public ArrayList<BlockPos> validRails = new ArrayList<>();
    public ArrayList<BlockPos> networkRails = new ArrayList<>();
    public ArrayList<BlockPos> stations = new ArrayList<>();
    public ArrayList<BlockPos> networkStations = new ArrayList<>();
    public HashSet<BlockPos> terminals = new HashSet<>();

    private ServerLevel world;

    public PathStations(ServerLevel world) {
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
                BlockPos nb = current.relative(d);
                enqueueWithPrev.accept(nb);
                enqueueWithPrev.accept(nb.below());
                enqueueWithPrev.accept(nb.above());
            }
        };

        Function<BlockPos, BlockPos.TraversalNodeStatus> callback = (pos) -> {
            BlockState bs = this.world.getBlockState(pos);
            if (pos.asLong() == endPos.asLong()) {
                stations.add(pos);
                long cur = pos.asLong();
                pathPositions.add(BlockPos.of(cur));

                while (predecessor.containsKey(cur)) {
                    long parent = predecessor.get(cur);
                    if (parent == cur) break;
                    pathPositions.add(BlockPos.of(parent));
                    cur = parent;
                    if (cur == startPos.asLong()) break;
                }

                Collections.reverse(pathPositions);
                validRails.clear();
                validRails.addAll(pathPositions);

                return BlockPos.TraversalNodeStatus.STOP;
            } else if (bs.is(railsTag)) {
                return BlockPos.TraversalNodeStatus.ACCEPT;
            } else if (bs.getBlock() == TBlocks.STATION_BLOCK) {
                stations.add(pos);
                return BlockPos.TraversalNodeStatus.ACCEPT;
            } else {
                return BlockPos.TraversalNodeStatus.SKIP;
            }
        };

        int maxDepth = Terminal.CONFIG.max_network_size();
        int maxIterations = 100000;

        BlockPos.breadthFirstTraversal(startPos, maxDepth, maxIterations, enqueueNeighbors, callback);
    }

    public void getNetwork(BlockPos startPos) {
        BiConsumer<BlockPos, Consumer<BlockPos>> enqueueNeighbors = (current, enqueue) -> {
            Direction[] lateral = new Direction[] { Direction.EAST, Direction.WEST, Direction.SOUTH, Direction.NORTH };
            for (Direction d : lateral) {
                BlockPos nb = current.relative(d);
                enqueue.accept(nb);
                enqueue.accept(nb.below());
                enqueue.accept(nb.above());
            }
        };

        Function<BlockPos, BlockPos.TraversalNodeStatus> callback = (pos) -> {
            BlockState bs = this.world.getBlockState(pos);
            if (bs.is(railsTag)) {
                networkRails.add(pos);
                return BlockPos.TraversalNodeStatus.ACCEPT;
            } if (bs.getBlock() == TBlocks.STATION_BLOCK) {
                networkRails.add(pos);
                networkStations.add(pos);
                return BlockPos.TraversalNodeStatus.ACCEPT;
            } else if (bs.getBlock() == TBlocks.TERMINAL_BLOCK) {
                networkRails.add(pos);
                terminals.add(pos);
                return BlockPos.TraversalNodeStatus.ACCEPT;
            } else {
                    return BlockPos.TraversalNodeStatus.SKIP;
                }
            };

            int maxDepth = Terminal.CONFIG.max_network_size();
            int maxIterations = 100000;

            BlockPos.breadthFirstTraversal(startPos, maxDepth, maxIterations, enqueueNeighbors, callback);
        }
}

















