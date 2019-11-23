package nestedvar.Quiver.Listeners.Miscellaneous;

import com.mongodb.client.MongoCollection;
import nestedvar.Quiver.Utilities.*;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;

import java.awt.*;
import java.io.IOException;

import static com.mongodb.client.model.Filters.eq;

public class QuiverJoin extends ListenerAdapter {

    public void onGuildJoin(GuildJoinEvent event){
        Colors colors = new Colors();
        Counter counter = new Counter();
        Database db = new Database();
        Utilities utils = new Utilities();
        db.connect();
        MongoCollection<Document> guilds = db.getCollection("guilds");
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

            try{
                webhook.execute();
            } catch(IOException exception){
                exception.printStackTrace();
            }

            db.close();
        }
    }

}
