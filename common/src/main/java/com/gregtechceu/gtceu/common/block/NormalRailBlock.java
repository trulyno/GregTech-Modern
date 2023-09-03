package com.gregtechceu.gtceu.common.block;

import com.gregtechceu.gtceu.api.block.AMHSRailBlock;
import com.gregtechceu.gtceu.api.pipenet.amhs.AMHSRailType;
import net.minecraft.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * @author KilaBash
 * @date 2023/8/9
 * @implNote NormalRailBlock
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class NormalRailBlock extends AMHSRailBlock {

    public NormalRailBlock(Properties properties) {
        super(properties, AMHSRailType.NORMAL);
    }

}
