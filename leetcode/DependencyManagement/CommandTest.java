package leetcode.DependencyManagement;

import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;

public class CommandTest {
    private static ComponenetLibrary lib = new ComponenetLibrary();

    @Test
    public void testDependCommand() {
        Command.executor("DEPEND TELNET TCP/TP", lib);
        Collection<Component> componenets = lib.getComponents();
        assertEquals(2, componenets.size());
        long installed = componenets.stream().filter(Component::getInstalled).count();
        assertEquals(0, installed);
        Component telnet = componenets.stream().filter(compo -> (compo.getName().equals("TELNET"))).findAny()
                .orElse(null);
        Component tcpip = componenets.stream().filter(compo -> (compo.getName().equals("TCP/TP"))).findAny()
                .orElse(null);

        assertNotNull(telnet);
        assertNotNull(tcpip);

        assertEquals(1, telnet.getPrereqs().size());
        telnet.getPrereqs().stream().findAny().orElse(null);
        assertEquals(tcpip, telnet.getPrereqs().stream().findAny().orElse(null));

        assertEquals(1, tcpip.getDependents().size());
        tcpip.getDependents().stream().findAny().orElse(null);
        assertEquals(telnet, tcpip.getDependents().stream().findAny().orElse(null));
    }

    @Test
    public void testInstallCommand() {
        Command.executor("DEPEND TELNET TCP/TP", lib);
        Command.executor("INSTALL TELNET", lib);
        Collection<Component> componenets = lib.getComponents();
        assertEquals(2, componenets.size());
        long installed = componenets.stream().filter(Component::getInstalled).count();
        assertEquals(2, installed);
    }

    @Test
    public void testRemoveCommandWhenRemovable() {
        Command.executor("DEPEND TELNET TCP/TP", lib);
        Command.executor("INSTALL TELNET", lib);
        Command.executor("REMOVE TELNET", lib);
        Collection<Component> componenets = lib.getComponents();
        assertEquals(2, componenets.size());
        long installed = componenets.stream().filter(Component::getInstalled).count();
        assertEquals(0, installed);
    }

    @Test
    public void testRemoveCommandWhenNotRemovable() {
        Command.executor("DEPEND TELNET TCP/TP", lib);
        Command.executor("INSTALL TELNET", lib);
        Command.executor("REMOVE TCP/TP", lib);
        Collection<Component> componenets = lib.getComponents();
        assertEquals(2, componenets.size());
        long installed = componenets.stream().filter(Component::getInstalled).count();
        assertEquals(2, installed);
    }

}