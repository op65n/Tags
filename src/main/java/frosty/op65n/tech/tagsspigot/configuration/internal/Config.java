package frosty.op65n.tech.tagsspigot.configuration.internal;

import java.nio.file.WatchEvent;
import java.util.function.Consumer;

public interface Config {

    default Consumer<WatchEvent<?>> onUpdate() {
        return e -> {};
    }

}
