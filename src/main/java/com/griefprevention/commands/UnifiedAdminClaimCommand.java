package com.griefprevention.commands;

import me.ryanhamshire.GriefPrevention.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Unified command handler for /aclaim with administrative subcommands
 */
public class UnifiedAdminClaimCommand extends UnifiedCommandHandler {

    public UnifiedAdminClaimCommand(@NotNull GriefPrevention plugin) {
        super(plugin, "aclaim");

        // Register subcommands
        registerSubcommand("restore", this::handleRestore, "restorenature", "restorenatureaggressive",
                "restorenaturefill");
        registerSubcommand("ignore", this::handleIgnore);
        registerSubcommand("mode", this::handleMode);
        registerSubcommand("adminlist", this::handleAdminList);
        registerSubcommand("list", this::handleList);
        registerSubcommand("checkexpiry", this::handleCheckExpiry);
        registerSubcommand("blocks", this::handleBlocks);
        registerSubcommand("delete", this::handleDelete, "deleteclaim", "deleteallclaims", "deleteclaimsinworld",
                "deleteuserclaimsinworld", "deletealladminclaims");
        registerSubcommand("transfer", this::handleTransfer);

        // Register standalone commands from Alias enum
        registerStandaloneCommand(Alias.AClaimRestore, this::handleRestore);
        registerStandaloneCommand(Alias.AClaimIgnore, this::handleIgnore);
        registerStandaloneCommand(Alias.AClaimMode, this::handleMode);
        registerStandaloneCommand(Alias.AClaimAdminList, this::handleAdminList);
        registerStandaloneCommand(Alias.AClaimList, this::handleList);
        registerStandaloneCommand(Alias.AClaimCheckExpiry, this::handleCheckExpiry);
        registerStandaloneCommand(Alias.AClaimBlocks, this::handleBlocks);
        registerStandaloneCommand(Alias.AClaimDelete, this::handleDelete);
        registerStandaloneCommand(Alias.AClaimTransfer, this::handleTransfer);
    }

    @Override
    protected void handleDefault(CommandSender sender) {
        // Check if root command is disabled first
        if (!rootCommandEnabled) {
            sender.sendMessage("This command is disabled.");
            return;
        }

        // Check if use-as-help-cmd is enabled
        if (rootCommandConfig != null && rootCommandConfig.shouldUseAsHelpCmd()) {
            sendHelpMessage(sender, new String[0]);
            return;
        }

        // Default behavior: set admin claim mode
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command can only be used by players.");
            return;
        }

        PlayerData playerData = plugin.dataStore.getPlayerData(player.getUniqueId());
        playerData.shovelMode = ShovelMode.Admin;
        GriefPrevention.sendMessage(player, TextMode.Success, Messages.AdminClaimsMode);
        return;
    }

    @Override
    protected boolean handleUnknownSubcommand(CommandSender sender, String subcommand, String[] args) {
        if (sender instanceof Player player) {
            GriefPrevention.sendMessage(player, TextMode.Err, Messages.CommandNotFound, subcommand);
        } else {
            sender.sendMessage("Unknown subcommand: " + subcommand);
        }
        return true;
    }

    private boolean handleRestore(CommandSender sender, String[] args) {
        // TODO: Implement restore command logic
        if (sender instanceof Player player) {
            GriefPrevention.sendMessage(player, TextMode.Err, Messages.CommandNotImplementedYet);
        } else {
            sender.sendMessage("This command is not implemented yet.");
        }
        return true;
    }

    private boolean handleIgnore(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player))
            return false;

        PlayerData playerData = plugin.dataStore.getPlayerData(player.getUniqueId());
        playerData.ignoreClaims = !playerData.ignoreClaims;

        if (!playerData.ignoreClaims) {
            GriefPrevention.sendMessage(player, TextMode.Success, Messages.RespectingClaims);
        } else {
            GriefPrevention.sendMessage(player, TextMode.Success, Messages.IgnoringClaims);
        }
        return true;
    }

    private boolean handleMode(CommandSender sender, String[] args) {
        if (args.length == 0)
            return false;

        if (!(sender instanceof Player player))
            return false;

        PlayerData playerData = plugin.dataStore.getPlayerData(player.getUniqueId());

        if ("admin".equalsIgnoreCase(args[0])) {
            playerData.shovelMode = ShovelMode.Admin;
            GriefPrevention.sendMessage(player, TextMode.Success, Messages.AdminClaimsMode);
            return true;
        }

        GriefPrevention.sendMessage(player, TextMode.Err, Messages.CommandInvalidMode);
        return false;
    }

    private boolean handleAdminList(CommandSender sender, String[] args) {
        // TODO: Implement admin claims list
        if (sender instanceof Player player) {
            GriefPrevention.sendMessage(player, TextMode.Err, Messages.CommandNotImplementedYet);
        } else {
            sender.sendMessage("This command is not implemented yet.");
        }
        return true;
    }

    private boolean handleList(CommandSender sender, String[] args) {
        // Show admin claims list (similar to /adminclaimslist)
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        // Check permission
        if (!player.hasPermission("griefprevention.adminclaims")) {
            GriefPrevention.sendMessage(player, TextMode.Err, Messages.NoAdminClaimsPermission);
            return true;
        }

        // Find admin claims
        java.util.Vector<Claim> claims = new java.util.Vector<>();
        for (Claim claim : plugin.dataStore.getClaims()) {
            if (claim.ownerID == null) // admin claim
            {
                claims.add(claim);
            }
        }
        
        if (claims.size() > 0) {
            GriefPrevention.sendMessage(player, TextMode.Instr, Messages.ClaimsListHeader);
            for (Claim claim : claims) {
                GriefPrevention.sendMessage(player, TextMode.Instr,
                        GriefPrevention.getfriendlyLocationString(claim.getLesserBoundaryCorner()));
            }
        } else {
            GriefPrevention.sendMessage(player, TextMode.Info, "No administrative claims found.");
        }

        return true;
    }

    private boolean handleBlocks(CommandSender sender, String[] args) {
        // TODO: Implement blocks management (adjust/add/set)
        if (sender instanceof Player player) {
            GriefPrevention.sendMessage(player, TextMode.Err, Messages.CommandNotImplementedYet);
        } else {
            sender.sendMessage("This command is not implemented yet.");
        }
        return true;
    }

    private boolean handleDelete(CommandSender sender, String[] args) {
        // TODO: Implement delete commands (claim/world/all/user)
        if (sender instanceof Player player) {
            GriefPrevention.sendMessage(player, TextMode.Err, Messages.CommandNotImplementedYet);
        } else {
            sender.sendMessage("This command is not implemented yet.");
        }
        return true;
    }

    private boolean handleTransfer(CommandSender sender, String[] args) {
        // TODO: Implement transfer claim logic
        if (sender instanceof Player player) {
            GriefPrevention.sendMessage(player, TextMode.Err, Messages.CommandNotImplementedYet);
        } else {
            sender.sendMessage("This command is not implemented yet.");
        }
        return true;
    }

    private boolean handleCheckExpiry(CommandSender sender, String[] args) {
        // Check permission
        if (sender instanceof Player player) {
            if (!player.hasPermission("griefprevention.checkclaimexpiry")) {
                GriefPrevention.sendMessage(player, TextMode.Err, Messages.NoPermissionForCommand);
                return true;
            }
        } else {
            // Console requires a player target
            if (args.length == 0) {
                sender.sendMessage("Usage: /aclaim checkexpiry <player>");
                return false;
            }
        }

        // Handle no arguments - check current claim if player is standing in one
        if (args.length == 0) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("Console must specify a player target.");
                return false;
            }

            // Find claim at player's location
            Claim claim = plugin.dataStore.getClaimAt(player.getLocation(), true, null);
            if (claim == null) {
                GriefPrevention.sendMessage(player, TextMode.Err, Messages.ClaimExpiryNoClaim);
                return true;
            }

            // Show expiry info for this specific claim
            showSingleClaimExpiry(sender, claim);
            return true;
        }

        // Handle player target argument
        String targetPlayerName = args[0];

        // Look up the player (online or offline)
        OfflinePlayer targetPlayer = plugin.resolvePlayerByName(targetPlayerName);
        if (targetPlayer == null || !targetPlayer.hasPlayedBefore()) {
            if (sender instanceof Player player) {
                GriefPrevention.sendMessage(player, TextMode.Err, Messages.ClaimExpiryPlayerNotFound,
                        targetPlayerName);
            } else {
                sender.sendMessage("Could not find player: " + targetPlayerName);
            }
            return true;
        }

        // Get player data
        PlayerData playerData = plugin.dataStore.getPlayerData(targetPlayer.getUniqueId());
        if (playerData == null) {
            if (sender instanceof Player player) {
                GriefPrevention.sendMessage(player, TextMode.Err, Messages.ClaimExpiryPlayerNotFound,
                        targetPlayerName);
            } else {
                sender.sendMessage("Could not find player data for: " + targetPlayerName);
            }
            return true;
        }

        // Get all claims owned by the player
        java.util.List<Claim> claims = playerData.getClaims();
        if (claims.isEmpty()) {
            if (sender instanceof Player player) {
                GriefPrevention.sendMessage(player, TextMode.Info, Messages.ClaimExpiryPlayerNoClaims,
                        targetPlayer.getName());
            } else {
                sender.sendMessage(targetPlayer.getName() + " has no claims.");
            }
            return true;
        }

        // Show all claims for the player
        showPlayerClaimsExpiry(sender, targetPlayer, claims);
        return true;
    }

    private void showSingleClaimExpiry(CommandSender sender, Claim claim) {
        // Show header for single claim
        if (sender instanceof Player player) {
            GriefPrevention.sendMessage(player, TextMode.Info, Messages.ClaimExpiryHeader,
                    claim.getOwnerName());
        } else {
            sender.sendMessage("Claim expiration for " + claim.getOwnerName() + ":");
        }

        // Show location
        String location = claim.getLesserBoundaryCorner().getWorld().getName() + " (" +
                claim.getLesserBoundaryCorner().getBlockX() + ", " +
                claim.getLesserBoundaryCorner().getBlockY() + ", " +
                claim.getLesserBoundaryCorner().getBlockZ() + ")";

        if (sender instanceof Player player) {
            GriefPrevention.sendMessage(player, TextMode.Info, Messages.ClaimExpiryLocation, location);
        } else {
            sender.sendMessage("Location: " + location);
        }

        // Show expiry info
        String expiryInfo = getSingleClaimExpiryInfo(claim);
        if (sender instanceof Player player) {
            player.sendMessage(expiryInfo);
        } else {
            sender.sendMessage(expiryInfo);
        }
    }

    private void showPlayerClaimsExpiry(CommandSender sender, OfflinePlayer targetPlayer, java.util.List<Claim> claims) {
        // Check if player is online
        boolean isOnline = targetPlayer.isOnline();

        // Send header
        if (sender instanceof Player player) {
            GriefPrevention.sendMessage(player, TextMode.Info, Messages.ClaimExpiryPlayerHeading,
                    targetPlayer.getName());
            if (isOnline) {
                int maxDays = plugin.config_claims_expirationDays;
                if (maxDays > 0) {
                    player.sendMessage(
                            "§7Player is online. Claims will expire after §e" + maxDays + " days§7 of inactivity.");
                } else {
                    player.sendMessage("§7Player is online. Claims are configured to never expire.");
                }
            }
        } else {
            sender.sendMessage("Claim expiration for " + targetPlayer.getName() + ":");
            if (isOnline) {
                int maxDays = plugin.config_claims_expirationDays;
                if (maxDays > 0) {
                    sender.sendMessage(
                            "Player is online. Claims will expire after " + maxDays + " days of inactivity.");
                } else {
                    sender.sendMessage("Player is online. Claims are configured to never expire.");
                }
            }
        }

        // Display each claim with expiry info
        for (Claim claim : claims) {
            String expiryInfo = getClaimExpiryInfo(claim, targetPlayer, isOnline);
            String location = claim.getLesserBoundaryCorner().getWorld().getName() + " (" +
                    claim.getLesserBoundaryCorner().getBlockX() + ", " +
                    claim.getLesserBoundaryCorner().getBlockY() + ", " +
                    claim.getLesserBoundaryCorner().getBlockZ() + ")";

            if (sender instanceof Player player) {
                GriefPrevention.sendMessage(player, TextMode.Info, Messages.ClaimExpiryListEntry,
                        location, expiryInfo);
            } else {
                sender.sendMessage("- " + location + ": " + expiryInfo);
            }
        }
    }

    private String getSingleClaimExpiryInfo(Claim claim) {
        // Admin claims never expire
        if (claim.isAdminClaim()) {
            return "§aAdministrative claim (never expires)";
        }

        // Get the claim owner
        OfflinePlayer owner = null;
        if (claim.getOwnerID() != null) {
            owner = Bukkit.getOfflinePlayer(claim.getOwnerID());
        }

        if (owner == null || !owner.hasPlayedBefore()) {
            return "§aAdministrative claim (never expires)";
        }

        // Check if owner is online
        boolean isOnline = owner.isOnline();

        // If player is online, show max expiry time
        if (isOnline) {
            int maxDays = plugin.config_claims_expirationDays;
            if (maxDays > 0) {
                return "§7Owner is online. Claim will expire after §e" + maxDays + " days§7 of inactivity.";
            } else {
                return "§aOwner is online. Claim is configured to never expire.";
            }
        }

        // Get expiry configuration
        int expirationDays = plugin.config_claims_expirationDays;

        // Check if this is a chest claim
        int areaOfDefaultClaim = 0;
        if (plugin.config_claims_automaticClaimsForNewPlayersRadius >= 0) {
            areaOfDefaultClaim = (int) Math.pow(plugin.config_claims_automaticClaimsForNewPlayersRadius * 2 + 1, 2);
        }

        PlayerData ownerData = plugin.dataStore.getPlayerData(owner.getUniqueId());
        boolean isChestClaim = ownerData.getClaims().size() == 1 &&
                claim.getArea() <= areaOfDefaultClaim &&
                plugin.config_claims_chestClaimExpirationDays > 0;

        if (isChestClaim) {
            expirationDays = plugin.config_claims_chestClaimExpirationDays;
        }

        // If expiration is disabled
        if (expirationDays <= 0) {
            return "§aThis claim will never expire.";
        }

        // Calculate days since last login
        long lastPlayed = owner.getLastPlayed();
        long now = System.currentTimeMillis();
        long daysSinceLogin = (now - lastPlayed) / (1000 * 60 * 60 * 24);
        long daysUntilExpiry = expirationDays - daysSinceLogin;

        if (daysUntilExpiry > 0) {
            return "§eExpires in " + daysUntilExpiry + " day" + (daysUntilExpiry == 1 ? "" : "s");
        } else if (daysUntilExpiry == 0) {
            return "§cExpires today";
        } else {
            long daysExpired = Math.abs(daysUntilExpiry);
            return "§cExpired " + daysExpired + " day" + (daysExpired == 1 ? "" : "s") + " ago";
        }
    }

    private String getClaimExpiryInfo(Claim claim, OfflinePlayer player, boolean isOnline) {
        // Admin claims never expire
        if (claim.isAdminClaim()) {
            return "§aAdministrative claim (never expires)";
        }

        // If player is online, show max expiry time
        if (isOnline) {
            return "§7(see above)";
        }

        // Get expiry configuration
        int expirationDays = plugin.config_claims_expirationDays;

        // Check if this is a chest claim
        int areaOfDefaultClaim = 0;
        if (plugin.config_claims_automaticClaimsForNewPlayersRadius >= 0) {
            areaOfDefaultClaim = (int) Math.pow(plugin.config_claims_automaticClaimsForNewPlayersRadius * 2 + 1, 2);
        }

        PlayerData ownerData = plugin.dataStore.getPlayerData(player.getUniqueId());
        boolean isChestClaim = ownerData.getClaims().size() == 1 &&
                claim.getArea() <= areaOfDefaultClaim &&
                plugin.config_claims_chestClaimExpirationDays > 0;

        if (isChestClaim) {
            expirationDays = plugin.config_claims_chestClaimExpirationDays;
        }

        // If expiration is disabled
        if (expirationDays <= 0) {
            return "§aNever expires";
        }

        // Calculate days since last login
        long lastPlayed = player.getLastPlayed();
        long now = System.currentTimeMillis();
        long daysSinceLogin = (now - lastPlayed) / (1000 * 60 * 60 * 24);
        long daysUntilExpiry = expirationDays - daysSinceLogin;

        if (daysUntilExpiry > 0) {
            return "§eExpires in " + daysUntilExpiry + " day" + (daysUntilExpiry == 1 ? "" : "s");
        } else if (daysUntilExpiry == 0) {
            return "§cExpires today";
        } else {
            long daysExpired = Math.abs(daysUntilExpiry);
            return "§cExpired " + daysExpired + " day" + (daysExpired == 1 ? "" : "s") + " ago";
        }
    }
}
