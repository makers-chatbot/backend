package icesi.cmr.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private DataInitializer dataInitializer;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        dataInitializer.init();

    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
