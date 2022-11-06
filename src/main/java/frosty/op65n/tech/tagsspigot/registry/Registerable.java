package frosty.op65n.tech.tagsspigot.registry;

import frosty.op65n.tech.tagsspigot.TagsPlugin;

public interface Registerable {

    default void enable(final TagsPlugin plugin) {}
    default void disable(final TagsPlugin plugin) {}

}
