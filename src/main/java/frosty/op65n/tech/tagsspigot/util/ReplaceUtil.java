package frosty.op65n.tech.tagsspigot.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ReplaceUtil {

    public static List<String> replaceList(final List<String> input, final Object... values) {
        if (values == null) {
            return input;
        }

        final Map<Object, Object> replacements = getReplacements(values);
        final List<String> replacedLore = new ArrayList<>();
        for (String line : input) {
            for (final Object key : replacements.keySet()) {
                line = line.replace(key.toString(), replacements.get(key).toString());
            }
            replacedLore.add(line);
        }
        return replacedLore;
    }

    public static String replaceString(final String input, final Object... values) {
        if (values == null) {
            return input;
        }

        final Map<Object, Object> replacements = getReplacements(values);
        String replacedString = input;
        for (final Object key : replacements.keySet()) {
            replacedString = replacedString.replace(key.toString(), replacements.get(key).toString());
        }
        return replacedString;
    }

    private static Map<Object, Object> getReplacements(final Object... values) {
        final Map<Object, Object> replacements = new HashMap<>();
        for (int i = 0; i < values.length - 1; i += 2) {
            final Object key = values[i];
            final Object value = values[i + 1];
            replacements.put(key, value);
        }

        return replacements;
    }

}
