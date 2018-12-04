package edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats;

import java.io.IOException;

public interface Event extends Protocol {

    String getFile() throws IOException;

    int getType();
}
