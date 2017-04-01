package com.gaj2l.eventtus.ioc;

import android.app.Application;

import com.gaj2l.eventtus.ioc.components.DaggerServiceComponent;
import com.gaj2l.eventtus.ioc.components.ServiceComponent;
import com.gaj2l.eventtus.ioc.modules.AppModule;

/**
 * Created by Jackson Majolo on 30/03/2017.
 */

public class ComponentProvider {
    private static ServiceComponent serviceComponent;

    public static ServiceComponent getServiceComponent() {
        return serviceComponent;
    }

    public static void initialize(Application application) {
        serviceComponent = DaggerServiceComponent.builder().appModule(new AppModule(application)).build();
    }
}
