package com.jjerome.domain.strategies.mapping.collect;

import com.jjerome.core.Mapping;
import com.jjerome.core.MappingCollectStrategy;
import com.jjerome.core.Request;
import com.jjerome.core.UndefinedBody;
import org.springframework.stereotype.Component;


@Component
public class RequestQueryParamsCollectStrategy implements MappingCollectStrategy {
    @Override
    public void collect(Request<UndefinedBody> request, Mapping mapping) {

    }
}
