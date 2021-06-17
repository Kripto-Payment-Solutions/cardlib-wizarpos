package com.kriptops.wizarpos.cardlib;

import com.kriptops.wizarpos.cardlib.crypto.PaddingMode;
import com.kriptops.wizarpos.cardlib.db.IVController;
import com.kriptops.wizarpos.cardlib.crypto.FitMode;

public class PosOptions {

    private IVController ivController;
    private FitMode track2FitMode;
    private PaddingMode track2PaddingModeMode;

    public IVController getIvController() {
        return ivController;
    }

    public void setIvController(IVController ivController) {
        this.ivController = ivController;
    }

    public FitMode getTrack2FitMode() {
        return track2FitMode;
    }

    public void setTrack2FitMode(FitMode track2FitMode) {
        this.track2FitMode = track2FitMode;
    }

    public PaddingMode getTrack2PaddingMode() {
        return track2PaddingModeMode;
    }

    public void setTrack2PaddingMode(PaddingMode track2PaddingModeMode) {
        this.track2PaddingModeMode = track2PaddingModeMode;
    }
}
