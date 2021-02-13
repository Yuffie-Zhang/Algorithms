package leetcode.DependencyManagement;

public class Command {
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
