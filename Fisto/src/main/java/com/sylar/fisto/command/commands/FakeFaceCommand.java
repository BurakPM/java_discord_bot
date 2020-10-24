package com.sylar.fisto.command.commands;

import com.sylar.fisto.command.CommandContext;
import com.sylar.fisto.command.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 * CREDITS -> the makers of https://thispersondoesnotexist.com ,
 * pypy-agender and hankhank10
 */


public class FakeFaceCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        WebUtils.ins.getJSONObject("https://fakeface.rest/face/json").async((json) -> {
            if (json.get("image_url").asText("0").length() == 1) {
                channel.sendMessage("Try again later").queue();
                System.out.println(json);
                return;

            }

            final String source = json.get("source").asText();
            final String imageUrl = json.get("image_url").asText();
            final EmbedBuilder embed = EmbedUtils
                    .embedImage(imageUrl)
                    .setFooter("Source: " + source);

            channel.sendMessage(embed.build()).queue();


        });
    }


    @Override
    public String getName() {
        return "fface";
    }
}

