package edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats;

import java.io.IOException;

public interface Event extends Protocol {

    byte[] getBytes() throws IOException;

    byte getType();
}
