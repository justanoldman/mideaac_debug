package org.openhab.binding.mideaac.internal.handler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSmoother {

    @SuppressWarnings("unused")
    private final Logger logger = LoggerFactory.getLogger(MideaACHandler.class);
    private final HashMap<String, Float> history = new HashMap<String, Float>();
    private final ArrayList<String> managed = new ArrayList<String>();

    private static final DecimalFormat df = new DecimalFormat("0.0");

    public void setManaged(String channelName) {
        if (!managed.contains(channelName)) {
            managed.add(channelName);
        }
    }

    private boolean isManaged(String channelName) {
        if (managed.contains(channelName)) {
            return true;
        }
        return false;
    }

    public Float get(String channelName, Float value) {
        if (!isManaged(channelName)) {
            return value;
        }
        if (!history.containsKey(channelName)) {
            this.history.put(channelName, value); // JO added this.
            return value;
        } else {
            Float previousvalue = this.history.get(channelName); // JO added this.
            if (previousvalue != null && value != null) {
                // @SuppressWarnings("null")

                float avg = average(previousvalue, value);
                this.history.put(channelName, avg); // JO added this.
                logger.debug("channelname  when avg: {} and avg value: {} and previousvalue; {}", channelName, value,
                        previousvalue);
                return avg;
                // return value; //JO tried this and then rimmed this out
            } else {
                // float avg = average(0, 0);
                this.history.put(channelName, previousvalue); // JO added this.
                // history.put(channelName, avg);
                logger.debug("channelname when previous: {} and value: {} and previousvalue: {}", channelName, value,
                        previousvalue);
                return previousvalue;
            }
        }
    }

    public float average(float previousvalue, float value) {
        return Float.parseFloat(df.format((previousvalue + value) / 2));
    }

    public DataSmoother() {
    }
}
