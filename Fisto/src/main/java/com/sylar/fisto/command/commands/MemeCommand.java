package com.sylar.fisto.command.commands;

import com.fasterxml.jackson.databind.JsonNode;
import com.sylar.fisto.command.CommandContext;
import com.sylar.fisto.command.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class MemeCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        WebUtils.ins.getJSONObject("https://meme-api.herokuapp.com/gimme").async((json) -> {
            if(json.get("postLink").asText().length() == 0) {
                channel.sendMessage("Try again later").queue();
                System.out.println(json);
                return;
            }


            final String title = json.get("title").asText();
            final String url = json.get("postLink").asText();
            final String imageUrl = json.get("url").asText();
            final EmbedBuilder embed = EmbedUtils.embedImageWithTitle(title, url, imageUrl);

            channel.sendMessage(embed.build()).queue();
        });
    }

    @Override
    public String getName() {
        return "random";
    }
}
