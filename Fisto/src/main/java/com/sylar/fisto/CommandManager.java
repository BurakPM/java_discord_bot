package com.sylar.fisto;

import com.sylar.fisto.command.CommandContext;
import com.sylar.fisto.command.ICommand;
import com.sylar.fisto.command.commands.RedditPost;
import com.sylar.fisto.command.commands.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class CommandManager {
    private final List<ICommand> commands = new ArrayList<>();

    public CommandManager() {
        addCommand(new PingCommand());
        addCommand(new MemeCommand());
        addCommand(new OffensiveMeme());
        addCommand(new AydaliMeme());
        addCommand(new DarkMeme());
        addCommand(new HelpCommand(this));
        addCommand(new RedditPost());
        addCommand(new FakeFaceCommand());
    }

    public List<ICommand> getCommands() {
        return commands;
    }

    private void addCommand(ICommand cmd) {
        boolean nameFound = this.commands.stream().anyMatch(it
                -> it.getName()
                .equalsIgnoreCase(cmd.getName()));

        if (nameFound) {
            throw new IllegalArgumentException("A command with this name is already present");
        }

        commands.add(cmd);
    }

    @Nullable
    private ICommand getCommand(String search) {
        String searchLower = search.toLowerCase();

        for (ICommand cmd : this.commands) {
            if (cmd.getName().equalsIgnoreCase(search) || cmd.getAliases().contains(search)) {
                return cmd;
            }

        }

        return null;

    }

    void handle(GuildMessageReceivedEvent event) {
        String[] split = event.getMessage().getContentRaw()
                .replaceFirst("(?i)" + Pattern.quote(Config.get("PREFIX")), "")
                .split("\\s+");

        String invoke = split[0].toLowerCase();
        ICommand cmd = this.getCommand(invoke);

        if(cmd != null) {
            event.getChannel().sendTyping().queue();
            List<String> args = Arrays.asList(split).subList(1, split.length);

            CommandContext ctx = new CommandContext(event, args);

            cmd.handle(ctx);
        }
    }
}
