package com.thizthizzydizzy.treefeller.decoration;
import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
public abstract class DecorationDetector{
    public static final ArrayList<DecorationDetector> detectors = new ArrayList<>();
    static{
        detectors.add(new AdjacentDecorationDetector("snow", Material.SNOW, BlockFace.UP));
        detectors.add(new AdjacentColumnDecorationDetector("vines", Material.VINE, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST));
        detectors.add(new AdjacentDecorationDetector("cocoa", Material.COCOA, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST));
        detectors.add(new AdjacentColumnDecorationDetector("weeping vines", new Material[]{Material.WEEPING_VINES, Material.WEEPING_VINES_PLANT}, BlockFace.DOWN));
    }
    public final String name;
    private final Material material;
    public DecorationDetector(String name, Material material){
        this.name = name;
        this.material = material;
    }
    public abstract void detect(Block baseBlock, ArrayList<Block> blocks);
    public static DecorationDetector[] getDetectors(){
        return detectors.toArray(new DecorationDetector[detectors.size()]);
    }
    public static Material[] getMaterials(){
        Material[] mats = new Material[detectors.size()];
        for(int i = 0; i<mats.length; i++){
            mats[i] = detectors.get(i).material;
        }
        return mats;
    }
}