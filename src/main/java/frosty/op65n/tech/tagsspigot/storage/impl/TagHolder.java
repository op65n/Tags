package frosty.op65n.tech.tagsspigot.storage.impl;

public final class TagHolder {

    private final String identifier;
    private final String description;
    private final String display;
    private final String permission;

    public TagHolder(final String identifier, final String description, final String display, final String permission) {
        this.identifier = identifier;
        this.description = description;
        this.display = display;
        this.permission = permission;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDisplay() {
        return this.display;
    }

    public String getPermission() {
        return this.permission;
    }

}
