package eu.pb4.graves.ui;

import com.mojang.authlib.GameProfile;
import eu.pb4.graves.GraveTextures;
import eu.pb4.graves.config.ConfigManager;
import eu.pb4.graves.grave.Grave;
import eu.pb4.graves.grave.GraveManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GraveListGui extends PagedGui {
    private final UUID targetUUID;
    private final boolean canModify;
    private int ticker = 0;
    private List<Grave> graves;
    private boolean canFetch;

    public GraveListGui(ServerPlayerEntity player, GameProfile profile, boolean canModify, boolean canFetch) {
        super(player);
        this.targetUUID = profile.getId();

        this.setTitle(ConfigManager.getConfig().ui.graveTitle.with(Map.of("player", Text.literal(profile.getName()))));
        this.graves = new ArrayList<>(GraveManager.INSTANCE.getByUuid(this.targetUUID));
        this.canModify = canModify;
        this.canFetch = canFetch;
        this.updateDisplay();
    }

    @Override
    protected int getPageAmount() {
        return this.graves.size() / PAGE_SIZE + 1;
    }

    @Override
    protected DisplayElement getElement(int id) {
        if (id < this.graves.size()) {
            var config = ConfigManager.getConfig();

            var grave = this.graves.get(id);

            var placeholders = grave.getPlaceholders(this.player.getServer());

            var element = config.ui.listGraveIcon.get(grave.isProtected()).builder(placeholders)
                    .setCallback((index, type, action) -> {
                        grave.openUi(player, this.canModify, this.canFetch);
                    });

            return DisplayElement.of(element);
        }

        return DisplayElement.empty();
    }

    @Override
    protected DisplayElement getNavElement(int id) {
        return switch (id) {
            case 2 -> DisplayElement.previousPage(this);
            case 6 -> DisplayElement.nextPage(this);
            default -> GraveTextures.hasGuiTexture(this.player) ? DisplayElement.empty() : DisplayElement.filler();
        };
    }

    @Override
    public void onTick() {
        this.ticker++;
        if (this.ticker % 20 == 0) {
            this.graves = new ArrayList<>(GraveManager.INSTANCE.getByUuid(this.targetUUID));
            this.updateDisplay();
        }
        super.onTick();
    }
}
