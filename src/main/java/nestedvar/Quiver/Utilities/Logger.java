package nestedvar.Quiver.Utilities;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;

import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;

public class Logger {
    Colors colors = new Colors();
    Webhooks webhook = new Webhooks(System.getenv("QUIVERWEBHOOK"));

    public Logger(int code, Exception error, Guild guild) {
        StringWriter sw = new StringWriter();
        error.printStackTrace(new PrintWriter(sw));
        String exception = sw.toString();

        EmbedBuilder fatal = new EmbedBuilder();
        fatal.setDescription(exception);
        fatal.setColor(colors.fatalRed);
        fatal.setTimestamp(Instant.now());
        fatal.setFooter("A fatal error has occurred on Quiver", guild.getSelfMember().getUser().getEffectiveAvatarUrl());

        webhook.addEmbed(new Webhooks.EmbedObject()
                .setDescription(exception)
                .setColor(new Color(colors.fatalRed))
                .setFooter("A fatal error has occurred on Quiver", guild.getSelfMember().getUser().getEffectiveAvatarUrl())
        );
        guild.getTextChannelById("491459187620970505").sendMessage(fatal.build()).queue();
        try{
            webhook.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(exception);
    }

    public Logger(int code, Exception error) {
        StringWriter sw = new StringWriter();
        error.printStackTrace(new PrintWriter(sw));
        String exception = sw.toString();
        System.out.println(exception);
    }

    public Logger(int code, String error, Guild guild) {
        System.out.println(error);
    }

    public Logger(int code, String error) {
        System.out.println(error);
    }
}
