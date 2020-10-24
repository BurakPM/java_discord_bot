package com.sylar.fisto.command.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.sylar.fisto.command.CommandContext;
import com.sylar.fisto.command.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;


public class RedditPost implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        WebUtils.ins.getJSONArray("https:/www.reddit.com/r/tifu/random.json").async((jsonArrayNode) -> {
            if (getValueByField(jsonArrayNode, "title").length() == 0) {
                channel.sendMessage("Try again later").queue();
                System.out.println(jsonArrayNode);
                return;
            }

            final String title = getValueByField(jsonArrayNode, "title");
            final String author = getValueByField(jsonArrayNode, "author");
            final String post = getValueByField(jsonArrayNode, "selftext");
            final String url = getValueByField(jsonArrayNode, "url");
            final String limitedPost = limit(post, 1000);

            final EmbedBuilder embed = EmbedUtils.embedMessage(limitedPost)
                    .setTitle(title, url)
                    .setFooter("User: " + author);

            channel.sendMessage(embed.build()).queue();
        });

    }

    @Override
    public String getName() {
        return "tifu";
    }

    public static String limit(String value, int length) {
        StringBuilder buf = new StringBuilder(value);
        if (buf.length() > length) {
            buf.setLength(length);
            buf.append("...");
        }

        return buf.toString();
    }

    public String getValueByField(ArrayNode jsonArrayNode, String field) {
        String value = jsonArrayNode
                .get(0).get("data").get("children").get(0)
                .get("data").get(field).asText();

        return value;
    }


}


