package frosty.op65n.tech.tagsspigot.struct;

import frosty.op65n.tech.tagsspigot.struct.argument.TagArgument;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class TagHolder {

    private final String icon;
    private final String display;
    private final String description;

    private final String permission;

    public TagHolder(final String icon, final String display, final String description, final String permission) {
        this.icon = icon;
        this.display = display;
        this.description = description;

        this.permission = permission;
    }

    public Material icon() {
        return Material.valueOf(this.icon.toUpperCase());
    }

    public String display() {
        return this.display.replace("_", " ");
    }

    public List<String> description() {
        return Arrays.stream(this.description.split("\n")).map(it -> it.replace("_", " ")).collect(Collectors.toList());
    }

    public String descriptionS() {
        return this.description.replace("_", " ");
    }

    public String permission() {
        return this.permission;
    }

    public boolean isEmpty() {
        return this.display.equalsIgnoreCase("empty");
    }

    /*
            tag.<argument>.<value>
            display.<value> => display.&7Test_Tag
            description.<value> => description.&7Some_random_description
            icon.<value> => icon.NAME_TAG

            ie. tag.display.&fSnowman.description.&7Snowman_tag_for_cool_people.icon.NAME_TAG

         */
    public static TagHolder fromPermission(final String permission) {
        if (permission.equalsIgnoreCase("empty")) {
            return TagHolder.empty();
        }

        final Map<TagArgument, String> arguments = getArgumentPairs(permission);

        return new TagHolder(
                arguments.getOrDefault(TagArgument.ICON, TagArgument.ICON.defaultValue()),
                arguments.getOrDefault(TagArgument.DISPLAY, TagArgument.DISPLAY.defaultValue()),
                arguments.getOrDefault(TagArgument.DESCRIPTION, TagArgument.DESCRIPTION.defaultValue()),

                permission
        );
    }

    public static TagHolder empty() {
        return new TagHolder("DIRT", "empty", "empty", "empty");
    }

    private static Map<TagArgument, String> getArgumentPairs(final String permission) {
        final String[] split = permission.replace("tag.", "").split("\\.");

        final Map<TagArgument, String> arguments = new HashMap<>();
        for (int index = 0; index < split.length; index += 2) {
            final TagArgument argument = TagArgument.from(split[index]);
            final String value = split[index + 1];

            if (argument == null) {
                continue;
            }

            arguments.put(argument, value);
        }

        return arguments;
    }

}
