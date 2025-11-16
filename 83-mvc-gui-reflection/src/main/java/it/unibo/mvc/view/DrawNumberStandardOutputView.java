package it.unibo.mvc.view;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.api.DrawResult;

/**
 * implementations of the DrawNumberView interface.
 */
public final class DrawNumberStandardOutputView implements DrawNumberView {

    /**
     * this view only prints on the terminal.
     * it does not accept input.
     */
    public DrawNumberStandardOutputView() {
        //empty constructor
    }

    /**
     * this is an output only UI, we do not need a controller.
     */
    @Override
    public void setController(final DrawNumberController observer) {
    }

    /**
     * there is no frame to set visible.
     */
    @Override
    public void start() {
    }

    @Override
    public void result(final DrawResult res) {
        System.out.println(res.getDescription()); //NOPMD
    }

}
