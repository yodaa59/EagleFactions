package io.github.aquerr.eaglefactions.commands;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import io.github.aquerr.eaglefactions.EagleFactions;
import io.github.aquerr.eaglefactions.PluginInfo;
import io.github.aquerr.eaglefactions.logic.FactionLogic;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.Extent;

public class HomeCommand implements CommandExecutor
{
    @Override
    public CommandResult execute(CommandSource source, CommandContext context) throws CommandException
    {
        if(source instanceof Player)
        {
            Player player = (Player)source;

            String playerFactionName = FactionLogic.getFactionName(player.getUniqueId());

            if(playerFactionName != null)
            {
                if(FactionLogic.getHome(playerFactionName) != null)
                {
                    //TODO: Wait 5-10 seconds before teleporting.

                    World world = player.getWorld();

                    if(FactionLogic.isHomeInWorld(world.getUniqueId(), playerFactionName))
                    {
                        EagleFactions.getEagleFactions().getLogger().info("Found home!!!");

                        Vector3i home = FactionLogic.getHome(playerFactionName);

                        EagleFactions.getEagleFactions().getLogger().info("Teleporting the player...");
                        player.setLocation(player.getLocation().setBlockPosition(home));

                        source.sendMessage(Text.of(PluginInfo.PluginPrefix, "You were teleported to faction's home"));
                    }
                    else
                    {
                        source.sendMessage(Text.of(PluginInfo.ErrorPrefix, "Faction's home is not in this world."));
                    }
                }
                else
                {
                    source.sendMessage(Text.of(PluginInfo.ErrorPrefix, TextColors.RED, "Faction's home is not set!"));
                }

            }
            else
            {
                source.sendMessage(Text.of(PluginInfo.ErrorPrefix, TextColors.RED, "You must be in a faction in order to use this command!"));
            }

        }
        else
        {
            source.sendMessage(Text.of(PluginInfo.ErrorPrefix, TextColors.RED, "Only in-game players can use this command!"));
        }

        return CommandResult.success();
    }
}
