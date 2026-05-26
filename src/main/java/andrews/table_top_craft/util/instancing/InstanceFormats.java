package andrews.table_top_craft.util.instancing;

import com.github.andrew0030.pandora_core.modules.instancer.instancing.InstanceDataElement;
import com.github.andrew0030.pandora_core.modules.instancer.instancing.InstanceFormat;
import com.github.andrew0030.pandora_core.utils.enums.NumericPrimitive;

public class InstanceFormats {
    public static final InstanceDataElement TRANSFORM = new InstanceDataElement("ttc_Transform", NumericPrimitive.FLOAT, 4, 4);
    public static final InstanceDataElement COLOR = new InstanceDataElement("ttc_Color", NumericPrimitive.FLOAT, 4);
    public static final InstanceDataElement LIGHTMAP = new InstanceDataElement("ttc_Lightmap", NumericPrimitive.UNSIGNED_SHORT, 2);

    public static final InstanceFormat TRANSFORM_COLOR_LIGHTMAP = new InstanceFormat(TRANSFORM, COLOR, LIGHTMAP);
}
