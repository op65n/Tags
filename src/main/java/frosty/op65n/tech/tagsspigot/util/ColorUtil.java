package frosty.op65n.tech.tagsspigot.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

public final class ColorUtil {

    public static Component parse(String text, @Nullable Object... placeholders) {
        if (placeholders != null && placeholders.length > 0) {
            if (placeholders.length % 2 != 0) {
                throw new IllegalStateException("Placeholders Must be in a key: replacement order, found missing value!");
            }

            final TagResolver.Builder tagBuilder = TagResolver.builder();

            for (int i = 0; i < placeholders.length; i += 2) {
                String key = String.valueOf(placeholders[i]);
                Component value = deserialize(String.valueOf(placeholders[i + 1]));

                tagBuilder.tag(key, Tag.selfClosingInserting(value));
            }

            return MiniMessage.miniMessage().deserialize(text, tagBuilder.build()).decoration(TextDecoration.ITALIC, false);
        }

        return MiniMessage.miniMessage().deserialize(text).decoration(TextDecoration.ITALIC, false);
    }

    public static List<Component> parse(List<String> text, @Nullable Object... placeholders) {
        return text.stream().map(it -> parse(it, placeholders)).collect(Collectors.toList());
    }

    public static Component deserialize(String message) {
        return MiniMessage.miniMessage().deserialize(message).decoration(TextDecoration.ITALIC, false);
    }

    public static String parseS(final String input) {
        return HexUtil.colorify(input);
    }

}
