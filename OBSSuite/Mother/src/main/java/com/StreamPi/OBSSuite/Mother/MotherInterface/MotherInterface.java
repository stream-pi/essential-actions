package com.StreamPi.OBSSuite.Mother.MotherInterface;

import com.StreamPi.OBSSuite.Mother.API;

import net.twasi.obsremotejava.OBSRemoteController;

public class MotherInterface implements API {

    public static MotherInterface instance = null;

    public MotherInterface startNewInstance(OBSRemoteController obsRemoteController) {
        instance = new MotherInterface(obsRemoteController);

        return instance;
    }

    public static MotherInterface getInstance() {
        return instance;
    }

    private OBSRemoteController obsRemoteController;

    public MotherInterface(OBSRemoteController obsRemoteController) {
        // should be already connected

        this.obsRemoteController = obsRemoteController;
    }

    @Override
    public OBSRemoteController getRemoteController() {
        return obsRemoteController;
    }
}
