/*
 *  This file is part of Tornado-KFusion: A Java version of the KFusion computer vision
 *  algorithm running on TornadoVM.
 *  URL: https://github.com/beehive-lab/kfusion-tornadovm
 *
 *  Copyright (c) 2013-2019, 2024, APT Group, Department of Computer Science,
 *  The University of Manchester
 *
 *  This work is partially supported by EPSRC grants Anyscale EP/L000725/1,
 *  PAMELA EP/K008730/1, and EU Horizon 2020 E2Data 780245.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package kfusion.tornado;

import java.awt.EventQueue;

import kfusion.tornado.common.TornadoModel;
import kfusion.tornado.ui.KfusionTornadoCanvas;
import kfusion.tornado.ui.TornadoConfigPanel;
import kfusion.tornado.ui.TornadoFramesPanel;
import kfusion.tornado.ui.TornadoWorkbenchFrame;
import uk.ac.manchester.tornado.api.runtime.TornadoRuntimeProvider;

public class GUI {

    public static void main(String[] args) {
        EventQueue.invokeLater( () -> {
                final TornadoModel config = new TornadoModel();
                if (System.getProperty("tornado.config") != null) {
                    TornadoRuntimeProvider.loadSettings(System.getProperty("tornado.config"));
                }
                final TornadoConfigPanel tornadoConfig = new TornadoConfigPanel(config);
                final KfusionTornadoCanvas canvas = new KfusionTornadoCanvas(config, 660 * 2, 500, tornadoConfig);
                TornadoWorkbenchFrame frame = new TornadoWorkbenchFrame(config, canvas, tornadoConfig);
                frame.setVisible(true);
        });
    }
}
