
in PerInstance {
    mat4 ttc_Transform;
    vec4 ttc_Color;
    ivec2 ttc_Lightmap;
};

const ivec2 ttc_Inject_ConstantOverlay = ivec2(0, 10);

mat3 normScale(mat3 mtr) {
    vec3 scale = vec3(
        length(mtr[0]),
        length(mtr[1]),
        length(mtr[2])
    );

    mtr /= length(scale);

    return mtr;
}

// transforms
transform ModelViewMat = ModelViewMat * ttc_Transform;
transform Normal = normalize(Normal * normScale(mat3(ttc_Transform)));
replace Color = ttc_Color;
replace UV2 = ttc_Lightmap;
replace UV1 = ttc_Inject_ConstantOverlay;
