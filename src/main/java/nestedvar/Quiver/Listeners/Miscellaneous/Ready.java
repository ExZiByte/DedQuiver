package nestedvar.Quiver.Listeners.Miscellaneous;

import nestedvar.Quiver.Utilities.Colors;
import nestedvar.Quiver.Utilities.Counter;
import nestedvar.Quiver.Utilities.Utilities;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Ready extends ListenerAdapter {

    public void onReady(ReadyEvent event){
        Colors color = new Colors();
        Counter counter = new Counter();
        Utilities utils = new Utilities();
    }

}
