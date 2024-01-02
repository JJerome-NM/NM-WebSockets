package com.jjerome;

import com.jjerome.core.Mapping;

public interface ApplicationSecurity {

    Mapping wrapMappingSecurity(Mapping mapping);
}
