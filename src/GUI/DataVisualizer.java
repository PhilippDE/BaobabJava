package GUI;

/**
 * Created by Marcel on 25.03.2017.
 */
public interface DataVisualizer {

    /**
     * This method will be called when the calculation of a node has started.
     *
     * An class implementing this class should imlpement this and display some kind of
     * indication that calculation has started. In addition to that, also the the visualization
     * should be cleared so no confusion is being created.
     *
     */
    void displayClaculatingMesssage();


    void disable();

    void enable();

}
