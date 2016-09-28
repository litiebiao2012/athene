package com.athene.api.gateway.remote;

import com.athene.api.gateway.request.AtheneRequest;

/**
 * Created by fe on 16/9/9.
 */
public interface RemoteClient {

    public Object execute(AtheneRequest atheneRequest);


}
