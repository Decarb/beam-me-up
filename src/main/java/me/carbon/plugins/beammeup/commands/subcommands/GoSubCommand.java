package me.carbon.plugins.beammeup.commands.subcommands;

import me.carbon.plugins.beammeup.BeamMeUp;
import me.carbon.plugins.beammeup.locations.LocationFileManager;
import me.carbon.plugins.beammeup.locations.LocationManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GoSubCommand extends SubCommand {
    private final BeamMeUp pluginInstance;

    public GoSubCommand(BeamMeUp pluginInstance) {
        this.pluginInstance = pluginInstance;
        this.permission = "beam.go";
    }

    @Override
    public void onCommand(CommandSender commandSender, Command parentCommand, String alias, String[] args) {
        if (commandSender.hasPermission(this.permission)) {
            if (args.length == 1) {
                LocationManager lm = this.pluginInstance.getLocationManager();
                String name = args[0].toLowerCase();

                if (lm.hasLocation(name)) {
                    ((Player) commandSender).teleport(lm.getLocation(name));
                    commandSender.sendMessage("You were teleported to " + name);
                } else commandSender.sendMessage("Location not found - Make sure that you typed the name correctly");
            } else commandSender.sendMessage("Expected one argument");
        } else commandSender.sendMessage("You do not have permission to use this command");
    }

    // TODO: Abstract this out maybe? Too stupid to think about it now.
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String alias, String[] args) {
        if (args.length == 1 && commandSender.hasPermission(this.permission)) {
            LocationManager lm = this.pluginInstance.getLocationManager();
            List<String> locations = lm.getLocationNames();

            return locations.stream()
                    .filter(s -> s.startsWith(args[0]))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
