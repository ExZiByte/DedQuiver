import nestedvar.Quiver.Listeners.Miscellaneous.Ready;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.exceptions.RateLimitedException;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;
import java.net.UnknownHostException;

public class Quiver {

    private static ShardManager manager;
    private static final DefaultShardManagerBuilder quiver = new DefaultShardManagerBuilder();


    public static void main(String[] args) throws RateLimitedException, InterruptedException, UnknownHostException, LoginException {
        quiver.setToken(System.getenv("QUIVERTOKEN"));
        quiver.setStatus(OnlineStatus.DO_NOT_DISTURB);
        quiver.setActivity(Activity.watching("the loading bar fill"));
        quiver.setShardsTotal(-1);

        quiver.addEventListeners(
                new Ready()
        );

        manager = quiver.build();
    }

}
