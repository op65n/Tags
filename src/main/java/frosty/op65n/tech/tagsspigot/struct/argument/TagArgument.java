package frosty.op65n.tech.tagsspigot.struct.argument;

public enum TagArgument {

    DISPLAY("&cInvalid"),
    DESCRIPTION("&7A generic description"),
    ICON("NAME_TAG");

    private final String defaultValue;

    TagArgument(final String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String defaultValue() {
        return this.defaultValue;
    }

    public static TagArgument from(final String value) {
        TagArgument result = null;

        for (final TagArgument argument : values()) {
            if (!argument.name().equalsIgnoreCase(value)) {
                continue;
            }

            result = argument;
            break;
        }

        return result;
    }
}
