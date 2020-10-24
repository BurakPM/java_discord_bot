package com.sylar.fisto;

import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;

public class Bot {


    Collection<GatewayIntent> intents = EnumSet.of(
            GatewayIntent.GUILD_MESSAGES,
            GatewayIntent.GUILD_MEMBERS
    );

    Collection<CacheFlag> flags = EnumSet.of(
            CacheFlag.VOICE_STATE,
            CacheFlag.EMOTE);

    private Bot() {
        EmbedUtils.setEmbedBuilder(() -> new EmbedBuilder()
        .setColor(0x7EC0EE));


        try {
            JDABuilder.createDefault(Config.get("TOKEN"))
                    .setEnabledIntents(intents)
                    .disableCache(flags)
                    .setMemberCachePolicy(MemberCachePolicy.ONLINE)
                    .addEventListeners(new Listener())
                    .setActivity(Activity.watching("Boku No Pico"))
                    .setActivity(Activity.listening("!!help"))
                    .build();
        } catch (LoginException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new Bot();
    }

}
