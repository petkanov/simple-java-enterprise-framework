package com.udemy.course.ormtool.orm;

import com.udemy.course.ormtool.annotations.Bean;

import java.util.HashMap;
import java.util.Map;

@Bean
public class DaoRegistry {

    private final Map<String, DataAccessObject<?>> registry = new HashMap<>();

    public DaoRegistry() {
        /**
        registry.put(User.class.getName(), new UserDAO());
        registry.put(Group.class.getName(), new GroupDAO());
        registry.put(DeviceTroublesReportRecord.class.getName(), new DeviceTroubleReportDAO());
        registry.put(DeviceProperty.class.getName(), new DevicePropertyDAO());
        registry.put(DeviceFunction.class.getName(), new DeviceFunctionDAO());
        registry.put(ZWaveDeviceFunction.class.getName(), new ZWaveDeviceFunctionDAO());
        registry.put(KeyPadDevice.class.getName(), new KeyPadDeviceDAO());
        registry.put(IntrusionDevice.class.getName(), new IntrusionDeviceDAO());
        registry.put(CODevice.class.getName(), new IntrusionDeviceDAO());
        registry.put(ControlPanel.class.getName(), new IntrusionDeviceDAO());
        registry.put(ControlPanelBell.class.getName(), new IntrusionDeviceDAO());
        registry.put(FloodDevice.class.getName(), new IntrusionDeviceDAO());
        registry.put(MagneticDevice.class.getName(), new IntrusionDeviceDAO());
        registry.put(MWDevice.class.getName(), new IntrusionDeviceDAO());
        registry.put(PIRCameraDevice.class.getName(), new IntrusionDeviceDAO());
        registry.put(PIRDevice.class.getName(), new IntrusionDeviceDAO());
        registry.put(SirenDevice.class.getName(), new IntrusionDeviceDAO());
        registry.put(SmokeDevice.class.getName(), new IntrusionDeviceDAO());
        registry.put(ZWaveDevice.class.getName(), new ZWaveDeviceDAO());
        registry.put(ZwaveMultiChannelDevice.class.getName(), new ZwaveMultiChannelDeviceDAO());

        registry.put(Schedule.class.getName(), new ScheduleDAO());
        registry.put(RuleTrigger.class.getName(), new RuleTriggerDAO());
        registry.put(ActionAddress.class.getName(), new ActionAddressDAO());
        registry.put(LocalAction.class.getName(), new LocalActionDAO());
        registry.put(Rule.class.getName(), new RuleDAO());
         **/
    }

    public <T> DataAccessObject<T> getDAO(String className){
        return (DataAccessObject<T>) registry.get(className);
    }
}
