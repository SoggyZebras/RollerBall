package edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats;

import java.io.IOException;
import java.net.Socket;
import java.nio.channels.SelectionKey;

public interface Node extends Protocol {
    //Node includes the onEvent function to handle events
    void onEvent(Event e, Socket socket) throws IOException, ClassNotFoundException;
}
