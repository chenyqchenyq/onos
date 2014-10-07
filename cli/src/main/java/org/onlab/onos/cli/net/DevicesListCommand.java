package org.onlab.onos.cli.net;

import org.apache.karaf.shell.commands.Command;
import org.onlab.onos.cli.AbstractShellCommand;
import org.onlab.onos.cli.Comparators;
import org.onlab.onos.net.Device;
import org.onlab.onos.net.device.DeviceService;

import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Lists all infrastructure devices.
 */
@Command(scope = "onos", name = "devices",
         description = "Lists all infrastructure devices")
public class DevicesListCommand extends AbstractShellCommand {

    private static final String FMT =
            "id=%s, available=%s, role=%s, type=%s, mfr=%s, hw=%s, sw=%s, serial=%s";

    @Override
    protected void execute() {
        DeviceService service = get(DeviceService.class);
        for (Device device : getSortedDevices(service)) {
            printDevice(service, device);
        }
    }

    /**
     * Returns the list of devices sorted using the device ID URIs.
     *
     * @param service device service
     * @return sorted device list
     */
    protected static List<Device> getSortedDevices(DeviceService service) {
        List<Device> devices = newArrayList(service.getDevices());
        Collections.sort(devices, Comparators.ELEMENT_COMPARATOR);
        return devices;
    }

    /**
     * Prints information about the specified device.
     *
     * @param service device service
     * @param device  infrastructure device
     */
    protected void printDevice(DeviceService service, Device device) {
        if (device != null) {
            print(FMT, device.id(), service.isAvailable(device.id()),
                  service.getRole(device.id()), device.type(),
                  device.manufacturer(), device.hwVersion(), device.swVersion(),
                  device.serialNumber());
        }
    }

}
