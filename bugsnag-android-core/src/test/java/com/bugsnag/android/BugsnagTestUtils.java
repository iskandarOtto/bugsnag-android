package com.bugsnag.android;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

final class BugsnagTestUtils {

    static HashMap<String, Object> runtimeVersions = new HashMap<>();

    static {
        runtimeVersions.put("osBuild", "bulldog");
        runtimeVersions.put("androidApiLevel", 24);
    }

    static Configuration generateConfiguration() {
        Configuration configuration = new Configuration("5d1ec5bd39a74caa1267142706a7fb21");
        configuration.setDelivery(generateDelivery());
        configuration.setLogger(NoopLogger.INSTANCE);
        return configuration;
    }

    static ImmutableConfig generateImmutableConfig() {
        return convert(generateConfiguration());
    }


    static ImmutableConfig convert(Configuration config) {
        return ImmutableConfigKt.convertToImmutableConfig(config, null);
    }

    static DeviceBuildInfo generateDeviceBuildInfo() {
        return new DeviceBuildInfo(
                "samsung", "s7", "7.1", 24, "bulldog",
                "foo-google", "prod,build", "google", new String[]{"armeabi-v7a"}
        );
    }

    static Device generateDevice() {
        DeviceBuildInfo buildInfo = generateDeviceBuildInfo();
        return new Device(buildInfo, new String[]{}, null, null, null, 10923250000L,
                runtimeVersions);
    }

    static DeviceWithState generateDeviceWithState() {
        DeviceBuildInfo buildInfo = generateDeviceBuildInfo();
        return new DeviceWithState(buildInfo,null, null, null,
                109230923452L, runtimeVersions, 22234423124L,
                92340255592L, "portrait", new Date(0));
    }

    public static Delivery generateDelivery() {
        return new Delivery() {
            @NotNull
            @Override
            public DeliveryStatus deliver(@NotNull EventPayload payload,
                                          @NotNull DeliveryParams deliveryParams) {
                return DeliveryStatus.DELIVERED;
            }

            @NotNull
            @Override
            public DeliveryStatus deliver(@NotNull Session payload,
                                          @NotNull DeliveryParams deliveryParams) {
                return DeliveryStatus.DELIVERED;
            }
        };
    }

    public static AppWithState generateAppWithState() {
        return new AppWithState(generateImmutableConfig(), null, null, null,
                null, null, null, null, null);
    }

    public static App generateApp() {
        return new App(generateImmutableConfig(), null, null, null, null, null);
    }
}
