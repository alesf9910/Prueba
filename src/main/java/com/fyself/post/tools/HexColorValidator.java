package com.fyself.post.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HexColorValidator {
    private static Pattern pattern;
    private static Matcher matcher;

    private static final String HEX_PATTERN = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";

    public static boolean validate(final String hexColorCode) {
        pattern = Pattern.compile(HEX_PATTERN);
        matcher = pattern.matcher(hexColorCode);
        return matcher.matches();
    }
}
