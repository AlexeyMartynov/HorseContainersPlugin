package io.github.alexeymartynov.horsecontainers.main;

import org.bukkit.ChatColor;

public enum Message {

    CONTAINER_ADDED(ChatColor.GREEN + "Вы успешно надели контейнер на лошадь!"),
    HORSE_ALREADY_HAS_CONTAINER(ChatColor.RED + "На данной лошади уже есть контейнер!");

    private String message;

    private Message(String message) { this.message = message; }

    @Override
    public String toString() { return message; }
}
