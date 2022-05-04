package io.tofpu.bedwarsswapaddon.model.command.presenter;

import io.tofpu.bedwarsswapaddon.model.command.CommandHolder;
import io.tofpu.messagepresenter.MessagePresenterHolder;
import io.tofpu.messagepresenter.MessagePresenterHolderImpl;
import io.tofpu.messagepresenter.extra.MessagePairPresenter;
import io.tofpu.messagepresenter.extra.MessageTreePresenter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.annotation.Flag;
import revxrsal.commands.annotation.Optional;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.annotation.Usage;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class HelpPresenterHolder {
    private static HelpPresenterHolder instance;
    private static final String TITLE = "<white><bold>%s</bold>";
    private static final String KEY_STYLE = "<yellow>%s";
    private static final String VALUE_STYLE = "<white>%s";
    private static final String COMMAND_STYLE = "<yellow>/sb %s %s<dark_gray>- <white>%s";

    public static void generatePresenter(final PluginDescriptionFile description) {
        instance = new HelpPresenterHolder();
        instance.generate(description);
    }

    public static HelpPresenterHolder get() {
        return instance;
    }

    private String result = "";

    private HelpPresenterHolder() {
        // prevent instantiation
    }

    private void generate(final PluginDescriptionFile description) {
        final MessagePresenterHolder holder = new MessagePresenterHolderImpl("<yellow" +
                                                                             "><bold>Swap Addon!");
        holder.with(() -> {
            final MessagePairPresenter.Builder builder = new MessagePairPresenter.Builder();

            builder.title(String.format(TITLE, "Information"));
            builder.pair(String.format(KEY_STYLE, "Author"), String.format(VALUE_STYLE, "Tofpu"))
                    .pair(String.format(KEY_STYLE, "Version"), String.format(VALUE_STYLE, description
                            .getVersion()));

            return builder.build();
        }).with(() -> {
            final MessageTreePresenter.Builder builder = new MessageTreePresenter.Builder();

            builder.title(String.format(TITLE, "Commands"));
            for (final Method method : CommandHolder.class.getDeclaredMethods()) {
                final Subcommand commandMethod = method.getAnnotation(Subcommand.class);
                final Description commandDescription = method.getAnnotation(Description.class);
                if (commandMethod == null) {
                    continue;
                }
                final String usage = generateUsageOfMethod(commandMethod, method);

                builder.message(String.format(COMMAND_STYLE, commandMethod.value()[0],
                        usage, commandDescription != null ? commandDescription.value() : ""));
            }
            return builder.build();
        }).with(() -> {
            final MessagePairPresenter.Builder builder = new MessagePairPresenter.Builder();

            builder.title(String.format(TITLE, "Support"))
                    .pair(String.format(KEY_STYLE, "Discord"), String.format(VALUE_STYLE,
                            "<click:OPEN_URL:https://tofpu" + ".me/discord>tofpu" +
                            ".me/discord"));

            return builder.build();
        });

        this.result = holder.getResult();
    }

    private static String generateUsageOfMethod(final Subcommand subcommand,
            final Method method) {
        final StringBuilder builder = new StringBuilder();

        if (method.isAnnotationPresent(Usage.class)) {
            return method.getAnnotation(Usage.class).value().replace(subcommand.value()[0] + " ", "") + " ";
        }

        for (final Parameter parameter : method.getParameters()) {
            if (CommandSender.class.isAssignableFrom(parameter.getType())) {
                continue;
            }

            if (builder.length() != 0) {
                builder.append(" ");
            }

            final String name;
            switch (parameter.getType().getSimpleName()) {
                // add more types here
                default:
                    name = parameter.getName();
            }

            String startingTag = "<";
            String closingTag = ">";
            if (parameter.isAnnotationPresent(Optional.class)) {
                startingTag = "[";
                closingTag = "]";
            }

            String flag = "";
            if (parameter.isAnnotationPresent(Flag.class)) {
                final Flag flagAnnotation = parameter.getAnnotation(Flag.class);
                flag = "-" + flagAnnotation.value() + " ";
            }

            builder.append(startingTag).append(flag).append(name).append(closingTag).append(" ");
        }

        return builder.toString();
    }

    public String result() {
        return this.result;
    }
}
