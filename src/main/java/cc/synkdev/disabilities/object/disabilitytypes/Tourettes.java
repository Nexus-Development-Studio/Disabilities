package cc.synkdev.disabilities.object.disabilitytypes;

import cc.synkdev.disabilities.object.Disability;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.translation.Translatable;
import org.bukkit.Registry;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.*;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class Tourettes extends Disability {
    private final List<String> words = new ArrayList<>();
    @Override
    public String getId() {
        return "tourettes";
    }

    @Override
    public Class<? extends Event>[] eventClasses() {
        return new Class[0];
    }

    @Override
    public Consumer<Event>[] handleEvent() {
        return new Consumer[0];
    }

    @Override
    public String getDescription() {
        return "You say random things in the chat and your head moves randomly.";
    }

    @Override
    public Runnable reset(Player p) {
        return null;
    }

    @Override
    public Runnable execute(Player p, boolean first) {
        return () -> {
            if (p == null) return;

            if (Math.random() >= 0.85) {
                p.teleport(p.getLocation().addRotation(Math.round(Math.random()*30-15), Math.round(Math.random()*30-15)));
            }

            if (Math.random() >= 0.66) {
                if (words.isEmpty()) mapWords();
                Collections.shuffle(words);
                p.chat(words.getFirst());
            }
        };
    }

    private void mapWords() {
        words.addAll(Registry.MATERIAL.stream().filter(m -> !m.isAir() && !m.isLegacy()).map(this::name).toList());
        words.addAll(Registry.ENTITY_TYPE.stream().map(this::name).toList());
        words.addAll(RegistryAccess.registryAccess().getRegistry(RegistryKey.BIOME).stream().map(this::name).toList());
    }

    private String name(Translatable t) {
        PlainTextComponentSerializer plain = PlainTextComponentSerializer.plainText();
        return plain.serialize(Component.translatable(t.translationKey()));
    }
    @Override
    public long getDelay() {
        return 10 * 20L;
    }

    @Override
    public String getName() {
        return "Tourette's";
    }
}
