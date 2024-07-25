package com.jjerome.sync;

import java.util.List;

public interface Synchronized {

    boolean isDataSynchroniced();

    List<SyncParameter<Object>> getUnsynchronizedParameters();
}
