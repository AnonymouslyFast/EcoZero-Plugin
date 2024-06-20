package studio.ecoprojects.ecozero.utils;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.event.Listener;
import org.reflections.Reflections;
import studio.ecoprojects.ecozero.EcoZero;
import studio.ecoprojects.ecozero.discordintergration.BotEssentials;

import java.lang.reflect.InvocationTargetException;

public class EventUtils {


    public static void registerMinecraftListeners(String path) {
        for (Class<?> c : new Reflections(path).getSubTypesOf(Listener.class)) {
            try {
                Listener listener = (Listener) c
                        .getDeclaredConstructor()
                        .newInstance();
                EcoZero.getPlugin().getServer().getPluginManager().registerEvents(listener, EcoZero.getPlugin());
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        EcoZero.logger.info("Successfully registered EcoZero Minecraft listeners.");
    }

    public static void registerDiscordListeners(String path) {
        for (Class<?> c : new Reflections(path).getSubTypesOf(Listener.class)) {
            try {
                ListenerAdapter listener = (ListenerAdapter) c
                        .getDeclaredConstructor()
                        .newInstance();
                BotEssentials.jda.addEventListener(listener);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        EcoZero.logger.info("Successfully registered EcoZero Discord listeners.");
    }
}

