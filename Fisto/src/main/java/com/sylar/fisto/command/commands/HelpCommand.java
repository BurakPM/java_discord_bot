package com.sylar.fisto.command.commands;

import com.sylar.fisto.CommandManager;
import com.sylar.fisto.Config;
import com.sylar.fisto.command.CommandContext;
import com.sylar.fisto.command.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;


public class HelpCommand implements ICommand {
    private final CommandManager manager;

    public HelpCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();

        StringBuilder builder = new StringBuilder();
        //builder.append("Commands:\n");

        manager.getCommands().stream().map(ICommand::getName).forEach(
                (it) ->  builder.append('`')
                        .append(Config.get("PREFIX"))
                        .append(it).append("`\n")
        );

        String title = "Commands:";
        String message = builder.toString();
        final EmbedBuilder embed = EmbedUtils
                .embedMessageWithTitle(title, message)
                .setFooter("\"Fisto is programmed to please.\"");

        channel.sendMessage(embed.build()).queue();


    }

    @Override
    public String getName() {
        return "help";
    }
}
