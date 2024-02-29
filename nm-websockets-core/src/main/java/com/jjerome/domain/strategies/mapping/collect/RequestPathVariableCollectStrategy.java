package com.jjerome.domain.strategies.mapping.collect;

import com.jjerome.core.Mapping;
import com.jjerome.core.MappingCollectStrategy;
import com.jjerome.core.Request;
import com.jjerome.core.UndefinedBody;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;


@Component
public class RequestPathVariableCollectStrategy implements MappingCollectStrategy {

    @Override
    public void collect(Request<UndefinedBody> request, Mapping mapping) {
        String[] allPathVariableNames = mapping.getPathVariablesNames();

        if (allPathVariableNames.length > 0) {
            Matcher matcher = mapping.getRegexPathPattern().matcher(request.getPath());

            if (matcher.find()) {
                for (int i = 1; i <= matcher.groupCount(); ++i) {
                    String value = matcher.group(i);
                    request.getPathVariables().put(allPathVariableNames[i - 1], value);
                }
            }
        }
    }
}
