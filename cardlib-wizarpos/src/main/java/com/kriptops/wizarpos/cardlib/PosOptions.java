package com.kriptops.wizarpos.cardlib;

import com.kriptops.wizarpos.cardlib.crypto.PaddingMode;
import com.kriptops.wizarpos.cardlib.db.IVController;
import com.kriptops.wizarpos.cardlib.crypto.FitMode;

/**
 * Permite establecer parametros de configuracion para la librería.
 */
public class PosOptions {

    private IVController ivController;
    private FitMode track2FitMode;
    private PaddingMode track2PaddingMode;
    private int[] iccTaglist;
    private int[] nfcTagList;

    public IVController getIvController() {
        return ivController;
    }

    /**
     * Configura el controlador de persistencia de los vectores de inicializacion asociados a las llaves de trabajo.
     *
     * @return
     */
    public void setIvController(IVController ivController) {
        this.ivController = ivController;
    }

    public FitMode getTrack2FitMode() {
        return track2FitMode;
    }

    /**
     * Establece el metodo de completado del track 2 a una cadena hex valida.
     * <p>
     * FitMode.F completa con F, FitMode.Zero completa con 0.
     *
     * @param track2FitMode
     */
    public void setTrack2FitMode(FitMode track2FitMode) {
        this.track2FitMode = track2FitMode;
    }

    public PaddingMode getTrack2PaddingMode() {
        return track2PaddingMode;
    }

    /**
     * Establece el metodo de padding para completar el track2.
     * <p>
     * PaddingMode.Zero completa con 0x00, PaddingMode.F completa con 0xFF, PaddingMode.PKCS5
     * completa en modo PKCS5.
     *
     * @param track2PaddingMode
     */
    public void setTrack2PaddingMode(PaddingMode track2PaddingMode) {
        this.track2PaddingMode = track2PaddingMode;
    }

    public int[] getIccTaglist() {
        return iccTaglist;
    }

    public void setIccTaglist(int[] iccTaglist) {
        this.iccTaglist = iccTaglist;
    }

    public int[] getNfcTagList() {
        return nfcTagList;
    }

    public void setNfcTagList(int[] nfcTagList) {
        this.nfcTagList = nfcTagList;
    }

}
