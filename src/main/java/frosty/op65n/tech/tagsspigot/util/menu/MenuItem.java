package frosty.op65n.tech.tagsspigot.util.menu;

import org.bukkit.Material;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import java.util.List;

@ConfigSerializable
public final class MenuItem {

    @Setting
    private Material material = Material.BARRIER;

    @Setting
    private String display = "";

    @Setting
    private List<String> lore = List.of("lore");

    @Setting
    private List<Integer> slots = List.of(-1);

    @Setting
    private String action = "dummy";

    public Material material() {
        return this.material;
    }

    public String display() {
        return this.display;
    }

    public List<String> lore() {
        return this.lore;
    }

    public List<Integer> slots() {
        return this.slots;
    }

    public String action() {
        return this.action;
    }

}
