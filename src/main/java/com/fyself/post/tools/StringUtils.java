package com.fyself.post.tools;

import java.text.Normalizer;
import java.text.Normalizer.Form;

public final class StringUtils {
    public static String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
    }

    public static String normalize(String input) {
        return Normalizer.normalize(input, Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    private StringUtils() {
        throw new AssertionError("No 'StringUtils' instances for you!");
    }
}
