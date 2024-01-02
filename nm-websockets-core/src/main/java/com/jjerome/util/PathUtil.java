package com.jjerome.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PathUtil {

    private static final String PATH_VARIABLE_PATTERN = "\\{[^\\/]+\\}";
    private static final String PATH_VARIABLE_PATTERN_FOR_REGEX = "([^\\/]+)";
    private static final String REMOVE_BRACKETS_PATTERN = "[{}]";

    private static final String SLASH = "/";

    public String[] extractPathVariables(String path) {
        Pattern pattern = Pattern.compile(PATH_VARIABLE_PATTERN);
        Matcher matcher = pattern.matcher(path);
        String[] pathVariableArray = new String[]{};
        while (matcher.find()) {
            String unprocessedPathVariable = matcher.group();
            String processedPathVariable = unprocessedPathVariable.replaceAll(REMOVE_BRACKETS_PATTERN, "");
            pathVariableArray = ArrayUtils.add(pathVariableArray, processedPathVariable);
        }
        return pathVariableArray;
    }

    public String buildRegex(String path) {
        StringBuilder builder = new StringBuilder();
        String[] components = StringUtils.split(path, SLASH);
        for (String component : components) {
            if (StringUtils.isBlank(StringUtils.trim(component))) {
                continue;
            }

            builder.append("\\" + SLASH);
            if (StringUtils.containsNone(component, '{', '}')) {
                builder.append(component);
                continue;
            }

            builder.append(PATH_VARIABLE_PATTERN_FOR_REGEX);
        }
        return builder.toString();
    }

    /*
    /test/foo/     /smth
    /test/foo/(\d+)/smth

    /test/foo/{id}/smth
    /test/foo/1/smth

    /test/foo/1/smth
    /test/foo/(.*)/smth

    / <---> /?
    StringBuilder:
    group():
        result = group()
        if result contains '[{}]' -> builder.append('{???}')
        else builder.append(result)

     */
}
