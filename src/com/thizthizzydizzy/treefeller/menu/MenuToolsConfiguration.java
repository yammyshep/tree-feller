package com.thizthizzydizzy.treefeller.menu;
import com.thizthizzydizzy.simplegui.Button;
import com.thizthizzydizzy.simplegui.ItemBuilder;
import com.thizthizzydizzy.simplegui.Menu;
import com.thizthizzydizzy.treefeller.Option;
import com.thizthizzydizzy.treefeller.Tool;
import com.thizthizzydizzy.treefeller.TreeFeller;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.plugin.Plugin;
public class MenuToolsConfiguration extends Menu{
    private final int page;
    public MenuToolsConfiguration(Menu parent, Plugin plugin, Player player){
        this(parent, plugin, player, 0);
    }
    private final int TOOLS_PER_PAGE = 45;
    public MenuToolsConfiguration(Menu parent, Plugin plugin, Player player, int page){
        super(parent, plugin, player, "Tools Configuration", 54);
        this.page = page;
        refresh();
    }
    @Override
    public void onOpen(){
        refresh();
    }
    private void refresh(){
        int pageMin = page*TOOLS_PER_PAGE;
        int pageMax = (page+1)*TOOLS_PER_PAGE;//actually the first index of the next page, but it's used with <
        components.clear();
        add(new Button(size-1, makeItem(Material.BARRIER).setDisplayName("Back"), (click) -> {
            if(click==ClickType.LEFT)open(parent);
        }));
        if(page>0){
            add(new Button(size-9, makeItem(Material.PAPER).setDisplayName("Previous Page"), (click) -> {
                if(click==ClickType.LEFT)open(new MenuToolsConfiguration(parent, plugin, player, page-1));
            }));
        }
        if(pageMax<TreeFeller.tools.size()){
            add(new Button(size-2, makeItem(Material.PAPER).setDisplayName("Next Page"), (click) -> {
                if(click==ClickType.LEFT)open(new MenuToolsConfiguration(parent, plugin, player, page+1));
            }));
        }
        int index = 0;
        int offset = 0;
        for(int i = 0; i<Math.min(pageMax,TreeFeller.tools.size()-offset); i++){
            Tool t = TreeFeller.tools.get(i+offset);
            if(i<pageMin)continue;
            ItemBuilder builder = makeItem(t.material);
            if(Option.REQUIRED_NAME.toolValues.containsKey(t))builder.setDisplayName(Option.REQUIRED_NAME.getValue(t));
            if(Option.REQUIRED_LORE.toolValues.containsKey(t)){
                for(String lore : Option.REQUIRED_LORE.getValue(t)){
                    builder.addLore(lore);
                }
            }
            add(new Button(index, builder, (click) -> {
                if(click==ClickType.LEFT)open(new MenuToolConfiguration(this, plugin, player, t));
            }));
            index++;
        }
        updateInventory();
        add(new Button(size-1, makeItem(Material.BARRIER).setDisplayName("Back"), (click) -> {
            if(click==ClickType.LEFT)open(parent);
        }));
    }
}