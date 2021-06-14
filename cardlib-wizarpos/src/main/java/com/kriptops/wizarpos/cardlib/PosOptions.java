package com.kriptops.wizarpos.cardlib;

import com.kriptops.wizarpos.cardlib.db.IVController;

public class PosOptions {

    private IVController ivController;

    public IVController getIvController() {
        return ivController;
    }

    public void setIvController(IVController ivController) {
        this.ivController = ivController;
    }
}
