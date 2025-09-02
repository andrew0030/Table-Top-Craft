// PER_INSTANCE
#paco_inject
    paco_per_instance mat4 ttc_Transform; // translation
    paco_per_instance ivec2 ttc_Lightmap; // lightmap
#paco_end

#paco_transform ModelViewMat: multiply ttc_Transform
//#paco_transform Normal: multiply paco_Inject_Orientation
#paco_replace UV2 ttc_Lightmap
