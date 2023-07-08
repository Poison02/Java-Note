package cdu.zch.builder.builders;

import cdu.zch.builder.cars.CarType;
import cdu.zch.builder.components.Engine;
import cdu.zch.builder.components.GPSNavigator;
import cdu.zch.builder.components.Transmission;
import cdu.zch.builder.components.TripComputer;

/**
 * @author Zch
 * @date 2023/7/8
 **/
public interface Builder {

    void setCarType(CarType type);
    void setSeats(int seats);
    void setEngine(Engine engine);
    void setTransmission(Transmission transmission);
    void setTripComputer(TripComputer tripComputer);
    void setGPSNavigator(GPSNavigator gpsNavigator);

}
