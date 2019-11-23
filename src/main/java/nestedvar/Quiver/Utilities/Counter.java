package nestedvar.Quiver.Utilities;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Counter{

    public int getGuildsServiced(ReadyEvent event){
        int guildCount = 0;
        guildCount = event.getGuildAvailableCount();
        return guildCount;
    }

    public int getTotalMemberCount(ReadyEvent event){
        int memberCount = 0;

        for(Guild guild: event.getJDA().getGuilds()){
            memberCount = guild.getMembers().size();
        }

        return memberCount;
    }

    public int getTotalMemberCount(GuildMessageReceivedEvent event){
        int memberCount = 0;

        for(Guild guild: event.getJDA().getGuilds()){
            memberCount = guild.getMembers().size();
        }

        return memberCount;
    }

    public int getMemberCount(GuildJoinEvent event){
        return event.getGuild().getMembers().size();
    }
}
