/* 
 * Copyright 2017 James Clarkson.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kfusion.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import kfusion.TornadoModel;
import tornado.common.TornadoDevice;
import tornado.drivers.opencl.OCLDriver;
import tornado.drivers.opencl.runtime.OCLDeviceMapping;

import static tornado.runtime.TornadoRuntime.getTornadoRuntime;

public class TornadoConfigPanel extends JPanel implements ActionListener {

    private static final long serialVersionUID = 4887971237978617495L;
    final JComboBox<TornadoDevice> deviceComboBox;
    public final JCheckBox enableTornadoCheckBox;

    private final TornadoModel config;

    public TornadoConfigPanel(final TornadoModel config) {
        this.config = config;
        final List<TornadoDevice> tmpDevices = new ArrayList<>();

        OCLDriver driver = getTornadoRuntime().getDriver(OCLDriver.class);

        final TornadoDevice[] devices;
        if (driver != null) {

            for (int platformIndex = 0; platformIndex < driver.getNumPlatforms(); platformIndex++) {
                for (int deviceIndex = 0; deviceIndex < driver.getNumDevices(platformIndex); deviceIndex++) {
                    final OCLDeviceMapping device = new OCLDeviceMapping(platformIndex, deviceIndex);
                    //if(device.getDevice().getDeviceType() == OCLDeviceType.CL_DEVICE_TYPE_GPU)
                    tmpDevices.add(device);
                }
            }

            devices = new TornadoDevice[tmpDevices.size()];
            tmpDevices.toArray(devices);

        } else {
            devices = new TornadoDevice[0];
        }

        final TornadoDeviceSelection deviceSelectModel = new TornadoDeviceSelection(devices);
        deviceComboBox = new JComboBox<>();
        deviceComboBox.setModel(deviceSelectModel);
        deviceComboBox.setEnabled(false);
        deviceComboBox.addActionListener(this);

        enableTornadoCheckBox = new JCheckBox("Use Tornado");
        enableTornadoCheckBox.setSelected(false);
        enableTornadoCheckBox.addActionListener(this);

        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
                "Tornado Configuration"));

        add(enableTornadoCheckBox);

        add(new JLabel("Tornado Device:"));
        add(deviceComboBox);

    }

    public void updateModel() {
        config.setTornadoDevice((TornadoDevice) deviceComboBox.getSelectedItem());
        config.setUseTornado(enableTornadoCheckBox.isSelected());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        deviceComboBox.setEnabled(enableTornadoCheckBox.isSelected());
        updateModel();

    }

}
