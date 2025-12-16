package ru.metal_partner_rnd.ecommerce_core;

import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.LoadTimeWeavingConfigurer;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;

// @Configuration
// @EnableLoadTimeWeaving(aspectjWeaving = EnableLoadTimeWeaving.AspectJWeaving.ENABLED)
// class LtwConfig implements LoadTimeWeavingConfigurer {

//     @Override 
//     public LoadTimeWeaver getLoadTimeWeaver() {
//         return new InstrumentationLoadTimeWeaver();
//     }
// }