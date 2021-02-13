package leetcode.DependencyManagement;

import java.util.*;

class DependencyManagement {

    private static ComponenetLibrary lib = new ComponenetLibrary();

    public static void main(String[] args) {
        String input = "DEPEND TELNET TCPIP NETCARD\n" + "DEPEND TCPIP NETCARD\n" + "DEPEND DNS TCPIP NETCARD\n"
                + "DEPEND BROWSER TCPIP HTML\n" + "INSTALL NETCARD\n" + "INSTALL TELNET\n" + "INSTALL foo\n"
                + "REMOVE NETCARD\n" + "INSTALL BROWSER\n" + "INSTALL DNS\n" + "LIST\n" + "REMOVE TELNET\n"
                + "REMOVE NETCARD\n" + "REMOVE DNS\n" + "REMOVE NETCARD\n" + "INSTALL NETCARD\n" + "REMOVE TCPIP\n"
                + "REMOVE BROWSER\n" + "REMOVE TCPIP\n" + "LIST\n" + "END";

        String[] lines = input.split("\n");
        for (String line : lines) {
            if (line.equals("END")) {
                System.out.println(line);
                break;
            }
            Command.executor(line, lib);
        }

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            Command.executor(line, lib);
        }
        scanner.close();
    }
}

class Command {
    enum CommandType {
        DEPEND, INSTALL, REMOVE, LIST
    };

    public static void executor(String line, ComponenetLibrary lib) {
        System.out.println(line);
        String[] arguments = line.split(" ");
        CommandType cmd;
        try {
            cmd = CommandType.valueOf(arguments[0]);
        } catch (IllegalArgumentException e) {
            // need to log command type that not recognize
            return;
        }
        switch (cmd) {
            case DEPEND:
                runDepend(arguments, lib);
                break;
            case INSTALL:
                runInstall(arguments, lib);
                break;
            case REMOVE:
                runRemove(arguments, lib);
                break;
            case LIST:
                runList(lib);
                break;
        }

    }

    private static void runDepend(String[] arguments, ComponenetLibrary lib) {
        String dependentName = arguments[1];
        Component dependent = lib.getComponent(dependentName);
        for (int i = 2; i < arguments.length; i++) {
            String prereqName = arguments[i];
            Component prereq = lib.getComponent(prereqName);
            dependent.registerPrereq(prereq);
            prereq.registerDependent(dependent);
        }
    }

    private static void runInstall(String[] arguments, ComponenetLibrary lib) {
        Component toInstall = lib.getComponent(arguments[1]);
        if (toInstall.getInstalled()) {
            System.out.println("\t" + toInstall.getName() + " is already installed");
            return;
        } else {
            installComponent(toInstall);
            for (Component prereq : toInstall.getPrereqs()) {
                if (!prereq.getInstalled())
                    installComponent(prereq);
            }
        }
    }

    private static void installComponent(Component toInstall) {
        toInstall.setInstalled(true);
        System.out.println("\t" + "Installing " + toInstall.getName());
    }

    private static void runRemove(String[] arguments, ComponenetLibrary lib) {
        Component toRemove = lib.getComponent(arguments[1]);
        if (!toRemove.getInstalled()) {
            System.out.println("\t" + toRemove.getName() + " is not installed");
            return;
        }
        if (!toRemove.removable()) {
            System.out.println("\t" + toRemove.getName() + " is still needed");
            return;
        } else {
            removeComponent(toRemove);
            for (Component prereq : toRemove.getPrereqs()) {
                if (prereq.removable())
                    removeComponent(prereq);
            }
        }
    }

    private static void removeComponent(Component toRemove) {
        toRemove.setInstalled(false);
        System.out.println("\t" + "Removing " + toRemove.getName());
    }

    private static void runList(ComponenetLibrary lib) {
        Collection<Component> componenets = lib.getComponents();

        componenets.forEach(component -> {
            if (component.getInstalled()) {
                System.out.println("\t" + component.getName());
            }
        });
    }
}

class ComponenetLibrary {
    private Map<String, Component> componenetLib;

    public ComponenetLibrary() {
        this.componenetLib = new HashMap<>();
    }

    public Collection<Component> getComponents() {
        return this.componenetLib.values();
    }

    public boolean containsComponent(String name) {
        return this.componenetLib.containsKey(name);
    }

    public void addComponenet(Component component) {
        this.componenetLib.put(component.getName(), component);
    }

    public Component getComponent(String name) {
        Component component;
        if (!containsComponent(name)) {
            component = new Component(name);
            addComponenet(component);
        } else {
            component = componenetLib.get(name);
        }
        return component;
    }
}

class Component {
    private String name;
    private boolean installed;
    private Set<Component> prereqs;
    private Set<Component> denpendents;

    public Component(String name) {
        this.name = name;
        installed = false;
        prereqs = new HashSet<>();
        denpendents = new HashSet<>();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Component> getPrereqs() {
        return this.prereqs;
    }

    public Set<Component> getDependents() {
        return this.denpendents;
    }

    public boolean getInstalled() {
        return this.installed;
    }

    public void setInstalled(boolean installed) {
        this.installed = installed;
    }

    public void registerDependent(Component dependent) {
        this.denpendents.add(dependent);
    }

    // private boolean checkDependent(Component dependent) {

    // }

    public void registerPrereq(Component prereq) {
        this.prereqs.add(prereq);
    }

    public boolean removable() {
        if (!installed)
            return false;
        for (Component dependent : denpendents) {
            if (dependent.installed) {
                return false;
            }
        }
        return true;
    }

    public Object findfirst(Object object) {
        return null;
    }
}
