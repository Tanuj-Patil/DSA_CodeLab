package com.CodeLab.DB_Service.requestdto_converter;

import com.CodeLab.DB_Service.model.Location;
import com.CodeLab.DB_Service.requestDTO.LocationRequestDTO;

public class LocationConverter {
    public static Location locationConverter(LocationRequestDTO locationRequestDTO){
        Location location = new Location();
        location.setCity(locationRequestDTO.getCity());
        location.setState(locationRequestDTO.getState());
        location.setCountry(locationRequestDTO.getCountry());

        return location;
    }
}
