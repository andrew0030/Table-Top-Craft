package andrews.table_top_craft.animation.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDefinition;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.PartDefinition;

import java.util.List;
import java.util.Map;

public class AdvancedPartDefinition extends PartDefinition
{
    private final String name;

    public AdvancedPartDefinition(List<CubeDefinition> cubes, PartPose partPose, String name)
    {
        super(cubes, partPose);
        this.name = name;
    }

    public AdvancedPartDefinition(PartDefinition partDefinition)
    {
        this(partDefinition.cubes, partDefinition.partPose, null);
    }

    @Override
    public AdvancedPartDefinition addOrReplaceChild(String name, CubeListBuilder cubes, PartPose partPose)
    {
        AdvancedPartDefinition newPart = new AdvancedPartDefinition(cubes.getCubes(), partPose, name);
        PartDefinition replacedPart = this.children.put(name, newPart);
        if (replacedPart != null)
            newPart.children.putAll(replacedPart.children);
        return newPart;
    }

    @Override
    public AdvancedModelPart bake(int texWidth, int texHeight)
    {
        // Takes all the cubeDefinition's, bakes them and stores them in an ImmutableList as Cube's
        List<ModelPart.Cube> list = this.cubes.stream().map((cubeDefinition) -> cubeDefinition.bake(texWidth, texHeight)).collect(ImmutableList.toImmutableList());
        // A more readable approach of baking the values in children.
        Map<String, ModelPart> map = Maps.newHashMap();
        for(String key : this.children.keySet())
            map.put(key, this.children.get(key).bake(texWidth, texHeight));

        AdvancedModelPart modelPart = new AdvancedModelPart(list, map, this.name);
        modelPart.setInitialPose(this.partPose);
        modelPart.loadPose(this.partPose);
        return modelPart;
    }
}