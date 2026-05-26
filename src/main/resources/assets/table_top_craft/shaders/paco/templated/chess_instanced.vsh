
in PerInstance {
    mat4 ttc_Transform;
    vec4 ttc_Color;
    ivec2 ttc_Lightmap;
};

const ivec2 ttc_Inject_ConstantOverlay = ivec2(0, 10);

// transforms
transform ModelViewMat = ModelViewMat * ttc_Transform;
//transform Normal = Normal * paco_Inject_Orientation;
replace Color = ttc_Color;
replace UV2 = ttc_Lightmap;
replace UV1 = ttc_Inject_ConstantOverlay;


//// PER_INSTANCE
//#paco_inject
//paco_per_instance mat4 ttc_Transform; // translation
//paco_per_instance vec4 ttc_Color; // lightmap
//paco_per_instance ivec2 ttc_Lightmap; // lightmap
//#paco_end
//
//#paco_transform ModelViewMat: multiply ttc_Transform
////#paco_transform Normal: multiply paco_Inject_Orientation
//#paco_replace Color ttc_Color
//#paco_replace UV2 ttc_Lightmap
