/*
 * Copyright 2013 ragnar0k@fabytes.com.
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
package ANNFileDetect;

/**
 *
 * @author ragnar0k@fabytes.com
 */

import java.io.File;
import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.EncogDirectoryPersistence;

public class EncogParse {

    BasicNetwork bn;

    EncogParse(String egFileName) {
        bn = (BasicNetwork) EncogDirectoryPersistence.loadObject(new File(egFileName));
    }

    public String LayoutInfo() {
        String summary = "";
        for (int i = 0; i < bn.getLayerCount(); i++) {
            summary = summary + "Layer: " + i + " count:" + bn.getLayerNeuronCount(i) + " Bias: " + bn.getLayerBiasActivation(i) + "\n";
        }
        return summary;
    }
}
