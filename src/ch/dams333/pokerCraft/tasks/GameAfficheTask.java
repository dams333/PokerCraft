package ch.dams333.pokerCraft.tasks;

import ch.dams333.pokerCraft.Poker;
import net.minecraft.server.v1_15_R1.ChatMessageType;
import net.minecraft.server.v1_15_R1.IChatBaseComponent;
import net.minecraft.server.v1_15_R1.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameAfficheTask extends BukkitRunnable {

    public GameAfficheTask(Poker main) {
        this.main = main;
        timer = 0;
    }

    private Poker main;

    private int timer;

    @Override
    public void run() {
        if(main.gameManager.armorStands.size() > 0) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                int bided = main.gameManager.bided.get(p);
                int tapis = main.gameManager.tapis.get(p);
                int currentMaxBid = main.gameManager.currentBid;
                int totalBided = 0;
                for (Player pl : main.gameManager.bided.keySet()) {
                    totalBided += main.gameManager.bided.get(pl);
                }
                sendActionBar(p, ChatColor.GOLD + "Tapis: " + tapis + ChatColor.GRAY + " | " + ChatColor.YELLOW + "Misé: " + bided + ChatColor.GRAY + " | " + ChatColor.GREEN + "Plus grosse mise: " + currentMaxBid + ChatColor.GRAY + " | " + ChatColor.AQUA + "Montant total: " + totalBided);

                if (!main.gameManager.dead.contains(p)) {
                    if (main.gameManager.debout.contains(p)) {
                        main.gameManager.armorStands.get(p).setCustomName(ChatColor.GOLD + "Tapis: " + tapis + ChatColor.GRAY + " | " + ChatColor.GREEN + "Mise: " + bided);
                    } else {
                        main.gameManager.armorStands.get(p).setCustomName(ChatColor.RED + "Couché");
                    }
                } else {
                    main.gameManager.armorStands.get(p).setCustomName(ChatColor.RED + " ");
                }
            }
        }
        if(main.gameManager.central != null){
            main.gameManager.central.setCustomName(ChatColor.GRAY + main.gameManager.message);
        }
        timer++;
        if(timer >= 10){
            if(main.gameManager.places.keySet().size() > 3) {
                timer = 0;
                main.mapManager.rotateRiver();
            }
        }
    }


    private void sendActionBar(Player player, String message) {
        IChatBaseComponent chat = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");

        PacketPlayOutChat packetPlayOutChat = new PacketPlayOutChat(chat, ChatMessageType.GAME_INFO);

        CraftPlayer craft = (CraftPlayer) player;

        craft.getHandle().playerConnection.sendPacket(packetPlayOutChat);
    }

}
