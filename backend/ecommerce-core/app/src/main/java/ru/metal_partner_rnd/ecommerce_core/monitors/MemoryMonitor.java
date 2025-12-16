package ru.metal_partner_rnd.ecommerce_core.monitors;

import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;
import java.util.HashMap;

@Component
public class MemoryMonitor {

    @Scheduled(fixedRate = 60000)
    public Map<String, Long> monitor() {
        HashMap<String, Long> info = new HashMap();
        Runtime runtime = Runtime.getRuntime();
        Long used = runtime.totalMemory() - runtime.freeMemory();
        Long max = runtime.maxMemory();

        info.put("used (MB)", used / 1024 / 1024);
        info.put("max (MB)", max / 1024 / 1024);

        return info;
    }
}