package com.baeldung.controller;

import java.util.logging.Logger;

import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	private final static Logger LOGGER = Logger.getLogger(IndexController.class.getName());

	@GetMapping("/")
    public String greeting(Device device) {
		
		String deviceType = "browser";
		String platform = "browser";
		
        if (device.isNormal()) {
            deviceType = "browser";
        } else if (device.isMobile()) {
            deviceType = "mobile";
        } else if (device.isTablet()) {
            deviceType = "tablet";
        }
        
        platform = device.getDevicePlatform().name();
        
        if (platform.equalsIgnoreCase("UNKNOWN")) {
        	platform = "browser";
		}
        
        LOGGER.info("Client Device Type: " + deviceType +", Platform: " + platform);
        
		
        return "index";
    }
	
}
