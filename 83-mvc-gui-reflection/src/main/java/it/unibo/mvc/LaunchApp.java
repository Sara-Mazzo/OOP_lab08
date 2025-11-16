package it.unibo.mvc;

import java.lang.reflect.InvocationTargetException;
import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.controller.DrawNumberControllerImpl;
import it.unibo.mvc.model.DrawNumberImpl;

/**
 * Application entry-point.
 */
public final class LaunchApp {

    private LaunchApp() { }

    /**
     * Runs the application.
     *
     * @param args ignored
     * @throws ClassNotFoundException if the fetches class does not exist
     * @throws NoSuchMethodException if the 0-ary constructor do not exist
     * @throws InvocationTargetException if the constructor throws exceptions
     * @throws InstantiationException if the constructor throws exceptions
     * @throws IllegalAccessException in case of reflection issues
     * @throws IllegalArgumentException in case of reflection issues
     */
    public static void main(final String... args) 
        throws 
        ClassNotFoundException,
        NoSuchMethodException,
        InvocationTargetException,
        InstantiationException,
        IllegalAccessException {
        final var model = new DrawNumberImpl();
        final DrawNumberController app = new DrawNumberControllerImpl(model);
        final String[] viewClassNames = {
            "it.unibo.mvc.view.DrawNumberStandardOutputView", 
            "it.unibo.mvc.view.DrawNumberSwingView",
        };
        for (final String s : viewClassNames) {
            final Class<?> c = Class.forName(s);
            for (int i = 0; i < 3; i++) {
                final var newView = c.getConstructor().newInstance();
                if (DrawNumberView.class.isAssignableFrom(newView.getClass())) {
                    app.addView((DrawNumberView) newView);
                } else {
                    throw new IllegalStateException(
                        newView.getClass() + "cannot be cast as" + DrawNumberView.class
                    );
                }
            }
        }
    }
}
