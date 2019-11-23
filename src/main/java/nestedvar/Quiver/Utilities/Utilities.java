package nestedvar.Quiver.Utilities;

import com.mongodb.client.MongoCollection;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;

public class Utilities {

    Database db = new Database();

    public String getPrefix(GuildMessageReceivedEvent event){
        String prefix;
        db.connect();
        MongoCollection<Document> guilds = db.getCollection("guilds");
        prefix = guilds.find(eq("guildID", event.getGuild().getId())).first().getString("prefix");
        return prefix;
    }

    public void setPrefix(GuildMessageReceivedEvent event, String prefix){
        db.connect();
        MongoCollection<Document> guilds = db.getCollection("guilds");
        Document oldPrefix = guilds.find(eq("guildID", event.getGuild().getId())).first();
        Bson newPrefix = new Document("prefix", prefix);
        Bson updateDocument = new Document("$set", newPrefix);
        guilds.findOneAndUpdate(oldPrefix, updateDocument);
        db.close();
    }

    public String getSelfAvatar(GuildMessageReceivedEvent event){
        return event.getJDA().getSelfUser().getEffectiveAvatarUrl();
    }
    public String getSelfAvatar(GuildMemberJoinEvent event){
        return event.getJDA().getSelfUser().getEffectiveAvatarUrl();
    }
    public String getSelfAvatar(GuildMemberLeaveEvent event){
        return event.getJDA().getSelfUser().getEffectiveAvatarUrl();
    }

    public String getSelfAvatar(GuildJoinEvent event){
        return event.getJDA().getSelfUser().getEffectiveAvatarUrl();
    }

    public String getSelfAvatar(GuildLeaveEvent event){
        return event.getJDA().getSelfUser().getEffectiveAvatarUrl();
    }

    public TextChannel getLogChannel(GuildMessageReceivedEvent event){
        db.connect();
        MongoCollection<Document> guilds = db.getCollection("guilds");
        String channelID = guilds.find(eq("guildID", event.getGuild().getId())).first().getString("logChannelID");
        db.close();
        if(channelID.equalsIgnoreCase("Not Set")) return null;
        else return event.getGuild().getTextChannelById(channelID);
    }

    public TextChannel getLogChannel(GuildMemberJoinEvent event){
        db.connect();
        MongoCollection<Document> guilds = db.getCollection("guilds");
        String channelID = guilds.find(eq("guildID", event.getGuild().getId())).first().getString("logChannelID");
        db.close();
        if(channelID.equalsIgnoreCase("Not Set")) return null;
        else return event.getGuild().getTextChannelById(channelID);
    }

    public TextChannel getLogChannel(GuildMemberLeaveEvent event){
        db.connect();
        MongoCollection<Document> guilds = db.getCollection("guilds");
        String channelID = guilds.find(eq("guildID", event.getGuild().getId())).first().getString("logChannelID");
        db.close();
        if(channelID.equalsIgnoreCase("Not Set")) return null;
        else return event.getGuild().getTextChannelById(channelID);
    }

}
