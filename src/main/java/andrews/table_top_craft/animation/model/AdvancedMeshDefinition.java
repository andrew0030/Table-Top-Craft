package andrews.table_top_craft.animation.model;

import net.minecraft.client.model.geom.builders.MeshDefinition;

public class AdvancedMeshDefinition extends MeshDefinition
{
    private final AdvancedPartDefinition advancedRoot = new AdvancedPartDefinition(this.getRoot());

    /**
     * @return The MeshDefinition root PartDefinition, as an AdvancedPartDefinition.
     */
    public AdvancedPartDefinition getAdvancedRoot()
    {
        return this.advancedRoot;
    }

    /**
     * Because we create a new AdvancedPartDefinition children are stored in there,
     * unfortunately Minecraft does not expect that, and thus we need to copy over
     * all the children.
     * @param advancedRoot The AdvancedPartDefinition of which the children will be copied.
     */
    public AdvancedMeshDefinition overwriteRootChildren(AdvancedPartDefinition advancedRoot)
    {
        this.getRoot().children.clear(); // We clear the root children to avoid duplicates, in case something made it in there
        this.getRoot().children.putAll(advancedRoot.children); // We copy over all children
        return this;
    }
}