package com.StreamPi.OBSSuite.Mother.MotherConnection;

import net.twasi.obsremotejava.OBSRemoteController;

public class MotherConnection {

    private static OBSRemoteController obsRemoteController = null;

    public static OBSRemoteController getRemoteController() 
    {
		return obsRemoteController;
	}

    public static void setRemoteController(OBSRemoteController obsRemoteController)
    {
        MotherConnection.obsRemoteController = obsRemoteController;
    }
}
