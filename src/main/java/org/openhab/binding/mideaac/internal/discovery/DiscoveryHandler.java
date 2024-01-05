package org.openhab.binding.mideaac.internal.discovery;

import org.eclipse.jdt.annotation.NonNull;
import org.openhab.core.config.discovery.DiscoveryResult;

public interface DiscoveryHandler {
    public void discovered(@NonNull DiscoveryResult discoveryResult);
}
