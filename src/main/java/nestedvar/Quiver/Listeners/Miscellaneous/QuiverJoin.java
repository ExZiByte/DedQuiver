package nestedvar.Quiver.Listeners.Miscellaneous;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import nestedvar.Quiver.Utilities.*;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class QuiverJoin extends ListenerAdapter {

    public void onGuildJoin(GuildJoinEvent event){
        Colors colors = new Colors();
        Counter counter = new Counter();
        Database db = new Database();
        Utilities utils = new Utilities();
        db.connect();
        MongoCollection guilds = db.getCollection("guilds");
        MongoCollection members = db.getCollection("members");
        if(guilds.find(eq("guildID", event.getGuild().getId())).first() != null){
            db.close();
            Webhooks webhook = new Webhooks(System.getenv("QUIVERWEBHOOK"));
            webhook.addEmbed(new Webhooks.EmbedObject()
                    .setTitle("⚠Discord has triggered a GuildJoinEvent for a Guild already in the database")
                    .setColor(new Color(colors.errorRed))
                    .setDescription("Discord has triggered the Guild Join Event for the following guild\n\n```\nGuild name: " + event.getGuild().getName() + "\nGuild ID: " + event.getGuild().getId() + "\n```")
                    .setFooter("Quiver Guild Join Event Falsely Triggered!", utils.getSelfAvatar(event))
            );
            try{
                webhook.execute();
            } catch (IOException exception){
                exception.printStackTrace();
            }
        } else {
            Webhooks webhook = new Webhooks(System.getenv("QUIVERWEBHOOK"));
            webhook.addEmbed(new Webhooks.EmbedObject()
                    .setColor(new Color(colors.successGreen))
                    .setDescription("New Guild Added!\n\n```\nGuild name: " + event.getGuild().getName() + "\nGuild ID: " + event.getGuild().getId() + "\nGuild Owner: " + event.getGuild().getOwner().getUser().getName() + "#" + event.getGuild().getOwner().getUser().getDiscriminator() + "\nCurrent Member Count: " + counter.getMemberCount(event) + "\n```")
                    .setFooter("Quiver Guild Added!", utils.getSelfAvatar(event))
            );
            List<BasicDBObject> memberInformation = new ArrayList<>();
            for (Member member : event.getGuild().getMembers()) {
                if (!member.getUser().isBot()) {
                    BasicDBObject thing = new BasicDBObject(member.getUser().getId(), new BasicDBObject("name", member.getUser().getName() + "#" + member.getUser().getDiscriminator()).append("level", 0).append("xp", 0).append("isBanned", false));
                    if(members.find(eq("id", member.getUser().getId())).first() != null){
                        System.out.println("User already in DB ignoring");
                    } else {
                        Document memberDoc = new Document("id", member.getUser().getId()).append("name", member.getUser().getName() + "#" + member.getUser().getDiscriminator()).append("facebook", "Not Set").append("instagram", "Not Set").append("youtube", "Not Set").append("twitch", "Not Set").append("mixer", "Not Set").append("imgur", "Not Set").append("tiktok", "Not Set").append("steam", "Not Set").append("blizzard", "Not Set").append("epic", "Not Set").append("twitter", "Not Set").append("origin", "Not Set").append("reddit", "Not Set").append("spotify", "Not Set").append("skype", "Not Set").append("xboxlive", "Not Set").append("psn", "Not Set").append("slack", "Not Set").append("snapchat", "Not Set").append("teamspeak", "Not Set").append("mumble", "Not Set").append("stackoverflow", "Not Set").append("tumblr", "Not Set").append("giphy", "Not Set").append("github", "Not Set").append("gitlab", "Not Set");
                        members.insertOne(memberDoc);
                    }
                    memberInformation.add(thing);
                }
            }
            Document guild = new Document("guildID", event.getGuild().getId())
                    .append("guildName", event.getGuild().getName())
                    .append("prefix", "Q!")
                    .append("members", memberInformation)
                    .append("isBlacklisted", false)
                    .append("isChannelSystemEnabled", true)
                    .append("logChannelID", "Not Set");

            guilds.insertOne(guild);
            db.close();

            try{
                webhook.execute();
            } catch(IOException exception){
                exception.printStackTrace();
            }

            db.close();
        }
    }

}
