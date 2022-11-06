package frosty.op65n.tech.tagsspigot.placeholder;

import frosty.op65n.tech.tagsspigot.TagsPlugin;
import frosty.op65n.tech.tagsspigot.placeholder.impl.TagPlaceholder;
import frosty.op65n.tech.tagsspigot.registry.Registerable;

public final class PlaceholderRegisterable implements Registerable {

    @Override
    public void enable(final TagsPlugin plugin) {
        new TagPlaceholder();
    }

}
